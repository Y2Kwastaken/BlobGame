package sh.miles.algidle.computer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public record Equation(String name, Function<BigDecimal, BigDecimal> time) {

    private static BigDecimal baseEquation(BigDecimal upgradeValue, int constant, BigO timeFunction) {
        return BigDecimal.valueOf(1.0/Math.pow(Math.E, timeFunction.operate(upgradeValue).divide(BigDecimal.valueOf(constant), 4, RoundingMode.HALF_UP).doubleValue())*(constant-1.0));
    }
    // Constant Algorithms
    public static final Equation MEDIAN_SEARCH = new Equation("Median Search", (BigDecimal value) -> baseEquation(value, BigO.CONSTANT.ordinal()+2, BigO.CONSTANT));
    // LogN Algorithms
    public static final Equation BINARY_SEARCH = new Equation("Binary Search", (BigDecimal value) -> baseEquation(value, BigO.LOG_N.ordinal()+2, BigO.LOG_N));
    // Linear Algorithms

    // NLogN Algorithms
    public static final Equation FFT = new Equation("Fast Fourier Transform", (BigDecimal value) -> baseEquation(value, BigO.N_LOG_N.ordinal()+2, BigO.N_LOG_N));
    // N^2 Algorithms
    public static final Equation DFT = new Equation("Discrete Fourier Transform", (BigDecimal value) -> baseEquation(value, BigO.N_SQUARED.ordinal()+2, BigO.N_SQUARED));
    // N^3 Algorithms

    // 2^N Algorithms

    // N! Algorithms
    public static final Equation TRAVELING_SALESMAN_PROBLEM = new Equation("Traveling Salesman Problem", (BigDecimal value) -> baseEquation(value, BigO.FACTORIAL.ordinal()+2, BigO.FACTORIAL));


    public static void main(String[] args) {
        System.out.println(MEDIAN_SEARCH.time().apply(BigDecimal.valueOf(1L)));
        System.out.println(BINARY_SEARCH.time().apply(BigDecimal.valueOf(1L)));
        System.out.println(FFT.time().apply(BigDecimal.valueOf(1L)));
        System.out.println(DFT.time().apply(BigDecimal.valueOf(1L)));
        System.out.println(TRAVELING_SALESMAN_PROBLEM.time().apply(BigDecimal.valueOf(1L)));
    }
}
