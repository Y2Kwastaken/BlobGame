package sh.miles.blobs.entity.components;

public record EntityTargetComponent(int target, boolean doAttack) {

    public EntityTargetComponent withTarget(int target) {
        return new EntityTargetComponent(target, this.doAttack);
    }

    public EntityTargetComponent withDoAttack(boolean doAttack) {
        return new EntityTargetComponent(this.target, doAttack);
    }

}
