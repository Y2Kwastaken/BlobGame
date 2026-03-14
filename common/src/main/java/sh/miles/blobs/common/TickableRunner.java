package sh.miles.blobs.common;

import sh.miles.blobs.common.attribute.Lifecycle;

public class TickableRunner {

    public static final double TICKS_PER_SECOND = 50.0;
    public static final double NANO_CONSTANT = 1000000000.0;

    private final Lifecycle tickable;
    private final Thread gameThread;
    public volatile boolean running = true;
    public volatile long lastTps = -1;

    public TickableRunner(Lifecycle tickable) {
        this.tickable = tickable;
        this.gameThread = new Thread(this::run);
    }

    public void start() {
        this.gameThread.start();
        this.tickable.start();
    }

    public void stop() {
        try {
            this.running = false;
            this.gameThread.join();
            this.tickable.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void run() {
        long ticks = 0;

        long lastTime = System.nanoTime();
        double ns = NANO_CONSTANT / TICKS_PER_SECOND;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            final long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tickable.tick();
                ticks++;
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                lastTps = ticks;
                timer += 1000;
                ticks = 0;
            }
        }
    }
}
