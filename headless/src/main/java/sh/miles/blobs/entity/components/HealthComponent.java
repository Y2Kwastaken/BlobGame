package sh.miles.blobs.entity.components;

public record HealthComponent(float maxHealth, float health) {

    public HealthComponent withMaxHealth(float maxHealth) {
        return new HealthComponent(maxHealth, this.health);
    }

    public HealthComponent withHealth(float health) {
        return new HealthComponent(this.maxHealth, health);
    }

}
