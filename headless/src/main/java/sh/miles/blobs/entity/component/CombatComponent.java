package sh.miles.blobs.entity.component;

import sh.miles.blobs.common.attribute.Copyable;

public class CombatComponent implements Copyable<CombatComponent> {
    public float attackDamage;
    public float attackRange;
    public float attackDuration;
    public float attackTimeLeft;
    public int attackCooldown;
    public int attackCooldownTicksLeft;
    public boolean willStartAttack;

    public CombatComponent(final float attackDamage, final float attackRange, final float attackDuration, final float attackTimeLeft, final int attackCooldown, final int attackCooldownTicksLeft, final boolean willStartAttack) {
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.attackDuration = attackDuration;
        this.attackTimeLeft = attackTimeLeft;
        this.attackCooldown = attackCooldown;
        this.attackCooldownTicksLeft = attackCooldownTicksLeft;
        this.willStartAttack = willStartAttack;
    }

    @Override
    public CombatComponent copy() {
        return new CombatComponent(this.attackDamage, this.attackRange, this.attackDuration, this.attackTimeLeft, this.attackCooldown, this.attackCooldownTicksLeft, this.willStartAttack);
    }
}
