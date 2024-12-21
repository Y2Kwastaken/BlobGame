package sh.miles.algidle.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class TimeUtils {

    public static int TICKS_PER_MILLISECOND = 50;
    public static int MILLISECONDS_PER_TICK = 2;

    public static int milliSecondsToTicks(BigDecimal milliseconds) {
        return milliseconds.divide(BigDecimal.TWO, RoundingMode.HALF_UP).intValue();
    }

    public static BigDecimal ticksToMilliseconds(int ticks) {
        return BigDecimal.valueOf((long) ticks * TICKS_PER_MILLISECOND);
    }
}
