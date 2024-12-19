package sh.miles.algidle.math;

import java.math.BigInteger;

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

}
