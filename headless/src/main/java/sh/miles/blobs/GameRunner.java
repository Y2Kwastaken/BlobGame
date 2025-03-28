package sh.miles.blobs;

public class GameRunner {

    public static final double TICKS_PER_SECOND = 50.0;
    public static final double NANO_CONSTANT = 1000000000.0;
    public static final boolean SHOW_TPS = false;

    public static final GameRunner GAME = new GameRunner();

    private final Thread gameThread;
    public volatile boolean running = true;
    public volatile long lastTps = -1;
    public BlobsGame game;

    GameRunner() {
        this.gameThread = new Thread(this::run);
    }

    public void start() {
        this.gameThread.start();
    }

    public void stop() {
        try {
            this.running = false;
            this.gameThread.join();
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
                game.tick();
                ticks++;
                delta--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                if (SHOW_TPS) {
                    System.out.println("TPS: " + ticks);
                }
                lastTps = ticks;
                timer += 1000;
                ticks = 0;
            }
        }
    }
}
