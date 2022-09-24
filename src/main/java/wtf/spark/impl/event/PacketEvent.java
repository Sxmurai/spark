package wtf.spark.impl.event;

import me.bush.eventbus.event.Event;
import net.minecraft.network.Packet;

public class PacketEvent extends Event {

    private final Packet<?> packet;
    private final Direction direction;

    public PacketEvent(Direction direction, Packet<?> packet) {
        this.direction = direction;
        this.packet = packet;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) packet;
    }

    public boolean isClientBound() {
        return direction.equals(Direction.CLIENT);
    }

    public boolean isServerBound() {
        return direction.equals(Direction.SERVER);
    }

    @Override
    protected boolean isCancellable() {
        return true;
    }

    public enum Direction {
        CLIENT, SERVER
    }
}
