package sh.miles.algidle.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class BigMath {

    private BigMath() {
        throw new UnsupportedOperationException("Can not initialize utility class");
    }

    public static BigInteger factorial(BigInteger num) {
        BigInteger factorial = num;

        for (BigInteger i = BigInteger.ONE; i.compareTo(num) < 0; i = i.add(BigInteger.ONE)) {
            factorial = factorial.multiply(num.subtract(i));
        }

        return factorial;
    }

    public static int getDigitCount(BigInteger num) {
        double factor = Math.log(2) / Math.log(10);
        int digitCount = (int) (factor * num.bitLength() + 1);
        if (BigInteger.TEN.pow(digitCount - 1).compareTo(num) > 0) {
            return digitCount - 1;
        }
        return digitCount;
    }
    // .pow(getDigitCount(num))
    public static BigInteger log2(BigInteger num) {
        // Compute log in base 10
        double fracPart = ((new BigDecimal(num)).divide((BigDecimal.valueOf(10)).pow(getDigitCount(num)))).doubleValue();
        double log10 = getDigitCount(num) + Math.log10(fracPart);
        // Rebase to log base 2
        double log2 = log10/(Math.log10(2));
        return BigInteger.valueOf((long) log2);
    }

}
