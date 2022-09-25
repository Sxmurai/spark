package wtf.spark.asm.mixins.duck;

import net.minecraft.util.Session;

public interface IMinecraft {
    /**
     * Sets the local session for session authentication for connecting to servers
     * @param session the new session
     */
    void setSession(Session session);

    /**
     * Sets the games tick speed calculated as 50 / speed
     * @param speed the speed to go
     */
    void setTimerSpeed(float speed);
}
