package sh.miles.blobs.entity.component;

import sh.miles.blobs.entity.Entity;

public record CombatComponent(float damage, boolean attacking) {

    public CombatComponent withAttacking(boolean attacking) {
        return new CombatComponent(this.damage, attacking);
    }

    public CombatComponent withDamage(float damage) {
        return new CombatComponent(damage, this.attacking);
    }

    public static void update(Entity entity) {
        final var combat = entity.get(EntityComponents.COMBAT);
        if (combat == null) return;
        final var position = entity.get(EntityComponents.POSITION);
        if (position == null) return;
        final var level = entity.get(EntityComponents.LEVEL);
        if (level == null) return;

    }
}
