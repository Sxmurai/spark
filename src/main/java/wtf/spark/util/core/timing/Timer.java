package wtf.spark.util.core.timing;

public class Timer {

    public static final long NS_MS = 1000000L;
    private long time = -1L;

    public boolean hasPassed(long t) {
        return hasPassed(t, false);
    }

    public boolean hasPassed(long t, boolean reset) {

        boolean hasPassed = (getTimePassed() / NS_MS) >= t;
        if (hasPassed && reset) {
            resetTime();
        }

        return hasPassed;
    }

    public Timer resetTime() {
        time = System.nanoTime();
        return this;
    }

    public long getTimePassed() {
        return System.nanoTime() - time;
    }
}
