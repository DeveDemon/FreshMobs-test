package net.devedemon.freshmobs.entity.skeleton_walker.main;

import net.devedemon.freshmobs.entity.skeleton_walker.ai.SkeletonWalkerMeleeAttackGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SkeletonWalkerEntity extends Monster implements GeoEntity {

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    private static final RawAnimation SLASH = RawAnimation.begin().thenPlay("slash");
    private static final RawAnimation STAB = RawAnimation.begin().thenPlay("stab");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("death");
    private static final RawAnimation PUMP_FAKE = RawAnimation.begin().thenPlay("pump_fake");
    private static final RawAnimation BANGING = RawAnimation.begin().thenPlay("banging");


    private final AnimatableInstanceCache geoCache =
            GeckoLibUtil.createInstanceCache(this);

    private int deathLengthInTicks = 100;

    private AnimationController<SkeletonWalkerEntity>
            attackController = new AnimationController<>(this, "attack", 1, this::attackingAnimation)
            .triggerableAnim("slash", SLASH)
            .triggerableAnim("stab", STAB)
            .triggerableAnim("pump_fake", PUMP_FAKE)
            .triggerableAnim("banging", BANGING);
    private AnimationController<SkeletonWalkerEntity>
            deathController = new AnimationController<>(this, "death", 0, this::deathAnimation)
            .triggerableAnim("death", DEATH);


    public SkeletonWalkerEntity(EntityType<? extends SkeletonWalkerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ARMOR, 0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SkeletonWalkerMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement", 5, this::movementAnimation));

        controllers.add(this.deathController);

        controllers.add(this.attackController);
    }

    @Override
    public void tick() {
        super.tick();
         if(isDeadOrDying() && this.level().shouldTickDeath(this)) {
             this.tickDeath();
         }
    }

    @Override
    public void die(DamageSource pDamageSource) {
        this.attackController.forceAnimationReset();
        this.deathController.forceAnimationReset();
        this.triggerAnim("death", "death");

        super.die(pDamageSource);
    }

    @Override
    protected void tickDeath() {
        this.deathLengthInTicks--;
        if(this.deathLengthInTicks <= 0 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte)60);
            this.remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean isDeadOrDying() {
        return this.getHealth() <= 0;
    }


    private PlayState movementAnimation(
            AnimationState<SkeletonWalkerEntity> state) {

        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);
    }

    private PlayState attackingAnimation(AnimationState<SkeletonWalkerEntity> state) {
        return PlayState.CONTINUE;
    }

    private PlayState deathAnimation(AnimationState<SkeletonWalkerEntity> state) {
        return PlayState.CONTINUE;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public int getExperienceReward() {
        return 20;
    }
}
