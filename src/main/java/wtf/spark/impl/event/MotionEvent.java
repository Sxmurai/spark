package wtf.spark.impl.event;

import me.bush.eventbus.event.Event;

public class MotionEvent extends Event {
    public double x, y, z;

    public MotionEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    protected boolean isCancellable() {
        return true;
    }
}
