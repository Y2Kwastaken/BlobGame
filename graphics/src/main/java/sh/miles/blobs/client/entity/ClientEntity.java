package sh.miles.blobs.client.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sh.miles.blobs.api.dto.game.GameEntityDTO;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.asset.entity.AnimationVariation;
import sh.miles.blobs.client.asset.entity.EntityAnimationType;
import sh.miles.blobs.client.asset.entity.EntityAnimations;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public final class ClientEntity {

    private final ConcurrentLinkedQueue<Consumer<SpriteBatch>> renderQueue = new ConcurrentLinkedQueue<>();

    public final int entityId;
    public final EntityType entityType;
    private float stateTime = 0;

    private EntityAnimations animations;
    private EntityAnimationType animationType;
    private AnimationVariation variation;
    private Animation<TextureRegion> currentAnimation;

    public ClientEntity(int entityId, EntityType entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.animationType = EntityAnimationType.IDLE;
        this.variation = AnimationVariation.SOUTH;

        // Cache the animations exactly once on creation
        this.animations = Assets.ENTITY_ANIMATIONS.get(entityType);
    }

    public void render(SpriteBatch batch, GameEntityDTO.EntitySnapshotDTO dto) {
        if (dto == null) return;

        // Action Order: Replaced raw component checks with pure DTO math
        if (dto.isAttacking()) {
            setAnimation(EntityAnimationType.ATTACK, this.variation);
        } else if (Math.abs(dto.velocityX()) < 0.001f && Math.abs(dto.velocityY()) < 0.001f) {
            setAnimation(EntityAnimationType.IDLE, this.variation);
        } else if (dto.hasOrientaiton() ? dto.facingY() > 0 : dto.velocityY() > 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.NORTH);
        } else if (dto.hasOrientaiton() ? dto.facingY() < 0 : dto.velocityY() < 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.SOUTH);
        } else if (dto.hasOrientaiton() ? dto.facingX() > 0 : dto.velocityX() > 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.EAST);
        } else if (dto.hasOrientaiton() ? dto.facingX() < 0 : dto.velocityX() < 0) {
            setAnimation(EntityAnimationType.WALK, AnimationVariation.WEST);
        }

        if (this.currentAnimation != null) {
            // true/false for looping might depend on the animation type later
            final TextureRegion keyFrame = currentAnimation.getKeyFrame(this.stateTime, true);
            float drawX = (dto.x() * 16f) - (keyFrame.getRegionWidth() / 2f);
            float drawY = (dto.y() * 16f) - (keyFrame.getRegionHeight() / 2f);

            batch.draw(keyFrame, drawX, drawY, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
            this.stateTime += Gdx.graphics.getDeltaTime();
        }

        while (!renderQueue.isEmpty()) {
            renderQueue.poll().accept(batch);
        }
    }

    public void enqueueRenderOperation(Consumer<SpriteBatch> operation) {
        this.renderQueue.add(operation);
    }

    private void setAnimation(EntityAnimationType animation, AnimationVariation variation) {
        if (this.animationType == animation && this.variation == variation && this.currentAnimation != null) return;
        this.animationType = animation;
        this.variation = variation;

        if (this.animations != null && this.animations.getAnimation(animationType) != null) {
            this.currentAnimation = this.animations.getAnimation(animationType).get(variation);
        }
        this.stateTime = 0f;
    }
}
