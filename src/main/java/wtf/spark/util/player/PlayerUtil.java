package wtf.spark.util.player;

import wtf.spark.util.core.ClientImpl;

public class PlayerUtil implements ClientImpl {

    public static boolean isInsideBlock(double offset) {
        return !mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(-offset, -offset, -offset)).isEmpty();
    }


}
