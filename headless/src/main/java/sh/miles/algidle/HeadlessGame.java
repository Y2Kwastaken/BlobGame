package sh.miles.algidle;

public class HeadlessGame implements Runnable {

    public static final double TICKS_PER_SECOND = 50.0;
    public static final double NANO_CONSTANT = 1000000000.0;
    public static final boolean SHOW_TPS = false;

    public static final HeadlessGame GAME = new HeadlessGame();

    private final Thread gameThread;
    public final TickLoop tickLoop;
    public volatile boolean running = true;
    public volatile long lastTps = -1;

    private HeadlessGame() {
        this.gameThread = new Thread(this);
        this.tickLoop = new TickLoop();
    }

    public void start() {
        this.gameThread.start();
    }

    @Override
    public void run() {
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
                this.tickLoop.tick();
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

    public static void main(String[] args) {
        HeadlessGame.GAME.start();
    }
}
