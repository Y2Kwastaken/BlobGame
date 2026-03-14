package sh.miles.blobs.client.util;

public class RenderDebugSettings {
    public static boolean DEFAULT = true;

    public AtomicFlag showDiagnostics = new AtomicFlag(DEFAULT);
    public AtomicFlag showHitboxes = new AtomicFlag(DEFAULT);
    public AtomicFlag showLocation = new AtomicFlag(DEFAULT);
    public AtomicFlag showAttackSwing = new AtomicFlag(DEFAULT);
    public AtomicFlag showMiscDraws = new AtomicFlag(DEFAULT);
}
