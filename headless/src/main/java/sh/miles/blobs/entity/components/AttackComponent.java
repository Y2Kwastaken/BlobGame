package sh.miles.blobs.entity.components;

/**
 * Component for attack related values
 */
public record AttackComponent(float damage, float attackRadius, int attackTicks, int ticksLeft, boolean isAttacking) {

    public AttackComponent withIsAttacking(boolean isAttacking) {
        return new AttackComponent(this.damage, this.attackRadius, this.attackTicks, this.ticksLeft, isAttacking);
    }

    public AttackComponent withDamage(float damage) {
        return new AttackComponent(damage, this.attackRadius, this.attackTicks, this.ticksLeft, this.isAttacking);
    }

    public AttackComponent withTicksLess(int ticksLeft) {
        return new AttackComponent(damage, this.attackRadius, this.attackTicks, this.ticksLeft, this.isAttacking);
    }

    public Builder builder() {
        return new Builder(this);
    }

    public static class Builder {
        public float damage;
        public float attackRadius;
        public int attackTicks;
        public int ticksLeft;
        public boolean isAttacking;

        public Builder() {
        }

        public Builder(AttackComponent component) {
            this.damage = component.damage;
            this.attackRadius = component.attackRadius;
            this.attackTicks = component.attackTicks;
            this.ticksLeft = component.ticksLeft;
            this.isAttacking = component.isAttacking;
        }

        public AttackComponent build() {
            return new AttackComponent(this.damage, this.attackRadius, this.attackTicks, this.ticksLeft, this.isAttacking);
        }
    }
}
