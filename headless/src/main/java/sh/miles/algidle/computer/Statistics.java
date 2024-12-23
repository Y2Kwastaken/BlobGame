package sh.miles.algidle.computer;

import sh.miles.algidle.utils.collection.registry.RegistryKey;

import java.math.BigDecimal;
import java.util.Map;

public class Statistics {

    private final Map<RegistryKey, BigDecimal> stats;


    public Statistics(Map<RegistryKey, BigDecimal> stats) {
        this.stats = stats;
    }

    public void setStatistic(RegistryKey statistic, BigDecimal value) {
        this.stats.put(statistic, value);
    }

    public BigDecimal getStatistic(RegistryKey statistic) {
        return this.stats.getOrDefault(statistic, BigDecimal.ZERO);
    }

    public void incrementStatistic(RegistryKey statistic, BigDecimal value) {
        this.stats.put(statistic, this.getStatistic(statistic).add(value));
    }
}
