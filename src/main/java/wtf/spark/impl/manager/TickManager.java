package wtf.spark.impl.manager;

import wtf.spark.asm.mixins.duck.IMinecraft;
import wtf.spark.core.Spark;
import wtf.spark.util.core.ClientImpl;

public class TickManager implements ClientImpl {

    public TickManager() {
        Spark.BUS.subscribe(this);
    }

    public void setSpeed(float speed) {
        ((IMinecraft) mc).setTimerSpeed(speed);
    }

    public void resetTimerSpeed() {
        setSpeed(1.0f);
    }
}
