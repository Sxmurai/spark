package wtf.spark.impl.module.movement;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.Vec3d;
import wtf.spark.asm.mixins.vanilla.net.packet.s2c.ISPacketPlayerPosLook;
import wtf.spark.impl.event.MotionEvent;
import wtf.spark.impl.event.PacketEvent;
import wtf.spark.impl.module.ModuleCategory;
import wtf.spark.impl.module.ToggableModule;
import wtf.spark.impl.property.Property;
import wtf.spark.util.net.NetworkUtil;
import wtf.spark.util.player.PlayerUtil;
import wtf.spark.util.player.motion.MotionUtil;

import java.util.HashMap;
import java.util.Map;

public class PacketFlight extends ToggableModule {

    private static final double BASE_SPEED = 0.2873;
    private static final double MOVE_FACTOR = 1.0 / Math.sqrt(2.0);
    private static final double CONCEAL = 0.0624;

    private final Property<Mode> mode = new Property<>(Mode.FACTOR, "Mode", "m", "type");
    private final Property<Float> factor = new Property<>(2.1f, 0.1f, 5.0f, "Factor", "loops")
            .setVisibility(() -> mode.getValue().equals(Mode.FACTOR));
    private final Property<Bounds> bounds = new Property<>(Bounds.DOWN, "Bounds", "outofbounds");
    private final Property<Boolean> antiKick = new Property<>(true, "Anti Kick", "antikick", "No Kick");
    private final Property<Boolean> conceal = new Property<>(true, "Conceal", "slow");
    private final Property<Boolean> friction = new Property<>(true, "Friction", "slowdown");

    private final Map<Integer, Vec3d> predictions = new HashMap<>();
    private int teleportId = -1;
    private int lagTicks = 0;

    public PacketFlight() {
        super("Packet Flight", new String[]{"packetflight", "packetfly"}, ModuleCategory.MOVEMENT);
        register(mode, factor, bounds, antiKick, conceal, friction);
    }

    @Override
    protected void onDisable() {
        super.onDisable();

        predictions.clear();
        teleportId = -1;
        lagTicks = 0;
    }

    @EventListener
    public void onMotion(MotionEvent event) {
        double moveSpeed = (conceal.getValue() || --lagTicks > 0 || PlayerUtil.isInsideBlock(CONCEAL)) ? CONCEAL : BASE_SPEED;

        int factorInt = (int) Math.floor(factor.getValue());
        if (mode.getValue().equals(Mode.FACTOR)) {

            if (mc.player.ticksExisted % 10 < 10 * (factor.getValue() - Math.floor(factor.getValue()))) {
                factorInt++;
            }
        } else {
            factorInt = 1;
        }

        double motionY = 0.0;
        boolean shouldAntiKick = false;

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            motionY = CONCEAL;

            // apply friction
            if ((MotionUtil.isMoving() || PlayerUtil.isInsideBlock(CONCEAL)) && friction.getValue()) {
                motionY *= MOVE_FACTOR;
                moveSpeed *= MOVE_FACTOR;
            }
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            motionY = -CONCEAL;

            // apply friction
            if ((MotionUtil.isMoving() || PlayerUtil.isInsideBlock(CONCEAL)) && friction.getValue()) {
                motionY *= MOVE_FACTOR;
                moveSpeed *= MOVE_FACTOR;
            }
        } else {
            shouldAntiKick = antiKick.getValue()
                    && mc.player.ticksExisted % 40 == 0
                    && !PlayerUtil.isInsideBlock(CONCEAL)
                    && !mc.world.collidesWithAnyBlock(mc.player.getEntityBoundingBox())
                    && !MotionUtil.isMoving();

            if (shouldAntiKick) {
                motionY = -0.04;
                factorInt = 1;
            }
        }

        sendPackets(factorInt, moveSpeed, motionY, shouldAntiKick);

        event.x = mc.player.motionX;
        event.y = mc.player.motionY;
        event.z = mc.player.motionZ;

        mc.player.noClip = true;
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (event.isClientBound() && event.getPacket() instanceof CPacketPlayer) {
            event.setCancelled(true);
        }

        if (event.isServerBound() && event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = event.getPacket();

            int id = packet.getTeleportId();

            if (predictions.containsKey(id)) {
                Vec3d p = predictions.get(id);

                if (p.x == packet.getX() && p.y == packet.getY() && p.z == packet.getZ()) {
                    mc.player.connection.sendPacket(new CPacketConfirmTeleport(id));

                    if (!mode.getValue().equals(Mode.SETBACK)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            ((ISPacketPlayerPosLook) packet).setYaw(mc.player.rotationYaw);
            ((ISPacketPlayerPosLook) packet).setPitch(mc.player.rotationPitch);

            mc.player.connection.sendPacket(new CPacketConfirmTeleport(id));

            teleportId = id;
            lagTicks = 20;
        }
    }

    private void sendPackets(int f, double s, double y, boolean a) {
        if (f <= 0) {
            mc.player.setVelocity(0.0, 0.0, 0.0);
            return;
        }

        double motionX = 0.0;
        double motionZ = 0.0;

        if (MotionUtil.isMoving()) {
            double[] strafe = MotionUtil.strafe(s);

            motionX = strafe[0];
            motionZ = strafe[1];
        }

        for (int i = 1; i < f + 1; ++i) {
            double x = motionX * i;
            double z = motionZ * i;

            double motionV;
            if (a) {
                motionV = -0.04;
            } else {
                motionV = y * i;
            }

            // set client-sided velocity
            mc.player.motionX = x;
            mc.player.motionY = motionV;
            mc.player.motionZ = z;

            // our player position vector
            Vec3d vec = mc.player.getPositionVector();

            // our movement vector
            Vec3d moveVec = vec.add(x, motionV, z);

            // send move packet
            sendPacket(moveVec);

            // send our "bounds" packet because we go out of bounds (100+ blocks) so we can teleport wherever (thanks mojang)
            sendPacket(createOutOfBoundsVec(vec));

            // add our prediction to our absolute move vector
            predictions.put(++teleportId, moveVec);
            mc.player.connection.sendPacket(new CPacketConfirmTeleport(teleportId));
        }
    }

    private void sendPacket(Vec3d v) {
        NetworkUtil.sendNoEvent(new CPacketPlayer.Position(v.x, v.y, v.z, true));
    }

    private Vec3d createOutOfBoundsVec(Vec3d from) {
        double offset = 0.0;

        switch (bounds.getValue()) {
            case UP:
                offset = 1337.0;
                break;

            case DOWN:
                offset = -1337.0;
                break;

            case LIMIT:
                offset = mc.player.isElytraFlying() ? 300 : 100;
                break;
        }

        return from.add(0.0, offset, 0.0);
    }

    public enum Mode {
        FACTOR, FAST, SETBACK
    }

    public enum Bounds {
        UP, DOWN, LIMIT
    }
}
