package sh.miles.blobs.client.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.component.EntityComponents;

public final class ClientEntity {

    public final Entity entity;
    private float stateTime = 0;
    private EntityAnimations animations;
    private EntityAnimationType animationType;
    private AnimationVariation variation;
    private Animation<TextureRegion> currentAnimation;

    public ClientEntity(Entity entity) {
        this.entity = entity;
        this.animationType = EntityAnimationType.IDLE;
        this.variation = AnimationVariation.SOUTH;
    }

    public void render(SpriteBatch batch) {
        final var position = entity.get(EntityComponents.POSITION);
        if (position == null) return;
        final var velocity = entity.get(EntityComponents.VELOCITY);
        if (velocity == null) return;
        final var vector = velocity.velocity().vector();

        if (vector.isZero(0.001f)) {
            setAnimation(EntityAnimationType.IDLE, this.variation);
            vector.x = 0;
            vector.y = 0;
        } else if (vector.y > 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.NORTH);
        } else if (vector.y < 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.SOUTH);
        } else if (vector.x > 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.EAST);
        } else if (vector.x < 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.WEST);
        }

        if (this.currentAnimation != null) {
            final TextureRegion keyFrame = currentAnimation.getKeyFrame(this.stateTime);
            batch.draw(keyFrame, position.x(), position.y(), keyFrame.getRegionWidth() / 4f, keyFrame.getRegionHeight() / 4f);
            this.stateTime += Gdx.graphics.getDeltaTime();
            this.stateTime %= this.currentAnimation.getAnimationDuration();
        }

    }

    private void setAnimation(EntityAnimationType animation, AnimationVariation variation) {
        if (this.animationType == animation && this.variation == variation && this.currentAnimation != null) return;
        this.animationType = animation;
        this.variation = variation;
        this.currentAnimation = get(this.entity).getAnimation(animationType).get(variation);
    }

    private EntityAnimations get(Entity entity) {
        if (animations != null) return this.animations;
        this.animations = Assets.ENTITY_ANIMATIONS.get(entity.get(EntityComponents.ENTITY_TYPE));
        return this.animations;
    }
}
