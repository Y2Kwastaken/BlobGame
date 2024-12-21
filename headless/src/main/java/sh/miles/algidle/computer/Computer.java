package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;

/**
 * Algorithm Algorithm constant Algorithm level Algorithm Time Takes // refresh this every time an upgrade is applied to
 * the computer Computer level Random (a, b) -> variance on time
 */
@NullMarked
public class Computer {
    private final Algorithm algorithm;
    private int algorithmLevel;
    private int constant;
    private int computerLevel;
    private int processTime; // ms

    public Computer(final Algorithm algorithm) {
        this.algorithm = algorithm;
        this.algorithmLevel = 1;
        this.computerLevel = 1;
        this.processTime = -1;
    }


}
