package sh.miles.algidle;

import sh.miles.algidle.computer.Algorithms;
import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.registry.Registries;
import sh.miles.algidle.utils.Ticking;

public class TickLoop implements Ticking {

    private final Computer computer;

    TickLoop() {
        computer = new Computer(Registries.ALGORITHMS.get(Algorithms.DATA_SEARCH).unwrap());
    }

    @Override
    public void tick() {
        computer.tick();
    }
}
