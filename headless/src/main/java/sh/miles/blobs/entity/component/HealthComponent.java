package sh.miles.blobs.entity.component;

import sh.miles.blobs.common.attribute.Copyable;

public class HealthComponent implements Copyable<HealthComponent> {
    public float maxHealth;
    public float health;

    public HealthComponent(float maxHealth, float health) {
        this.maxHealth = maxHealth;
        this.health = health;
    }

    public HealthComponent(float maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public HealthComponent copy() {
        return new HealthComponent(this.maxHealth, this.health);
    }
}
