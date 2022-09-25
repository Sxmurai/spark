package wtf.spark.util.player.motion;

import wtf.spark.util.core.ClientImpl;

public class MotionUtil implements ClientImpl {

    public static boolean isMoving() {
        return mc.player.movementInput.moveForward != 0.0f || mc.player.movementInput.moveStrafe != 0.0f;
    }

    public static double[] strafe(double speed) {
        float f = getMovementYaw();
        double sin = -Math.sin(f);
        double cos = Math.cos(f);
        return new double[] { sin * speed, cos * speed };
    }

    public static float getMovementYaw() {
        float rotationYaw = mc.player.rotationYaw;
        float n = 1.0f;

        if (mc.player.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        } else if (mc.player.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }

        if (mc.player.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }

        if (mc.player.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }

        return rotationYaw * 0.017453292f;
    }
}
