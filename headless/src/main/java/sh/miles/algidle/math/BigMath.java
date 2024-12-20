package sh.miles.algidle.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class BigMath {

    private BigMath() {
        throw new UnsupportedOperationException("Can not initialize utility class");
    }

    public static BigDecimal factorial(BigDecimal num) {
        BigDecimal factorial = num;

        for (BigDecimal i = BigDecimal.ONE; i.compareTo(num) < 0; i = i.add(BigDecimal.ONE)) {
            factorial = factorial.multiply(num.subtract(i));
        }

        return factorial;
    }

    public static int getDigitCount(BigDecimal num) {
        double factor = Math.log(2) / Math.log(10);
        int digitCount = (int) (factor * BigInteger.valueOf(num.longValue()).bitLength() + 1);
        if (BigInteger.TEN.pow(digitCount - 1).compareTo(BigInteger.valueOf(num.longValue())) > 0) {
            return digitCount - 1;
        }
        return digitCount;
    }
    // .pow(getDigitCount(num))
    public static BigDecimal log2(BigDecimal num) {
        // Compute log in base 10
        double fracPart = (num.divide((BigDecimal.valueOf(10)).pow(getDigitCount(num)), 4, RoundingMode.HALF_UP)).doubleValue();
        double log10 = getDigitCount(num) + Math.log10(fracPart);
        // Rebase to log base 2
        double log2 = log10/(Math.log10(2));
        return BigDecimal.valueOf(log2);
    }

}
