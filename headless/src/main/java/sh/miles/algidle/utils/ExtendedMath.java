package sh.miles.algidle.utils;

public class ExtendedMath {

    public static int factorial(int n) {
        int factorial = n;

        for (int i = 1; i < n; i++) {
            factorial *= (n-i);
        }
        return factorial;
    }

}
