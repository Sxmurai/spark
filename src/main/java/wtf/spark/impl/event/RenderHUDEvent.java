package wtf.spark.impl.event;

import me.bush.eventbus.event.Event;

public class RenderHUDEvent extends Event {

    private final float partialTicks;

    public RenderHUDEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    @Override
    protected boolean isCancellable() {
        return false;
    }
}
