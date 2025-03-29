package sh.miles.blobs.util;

public final class GameMath {

    public static boolean isInCircle(float centerX, float centerY, float radius, float x, float y) {
        final var squaredDist = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        return squaredDist <= Math.pow(radius, 2) && y <= centerY;
    }

}
