package net.devedemon.freshmobs.entity.skeleton_walker.main;


import net.devedemon.freshmobs.entity.general.ai.AggroAnimationGoal;
import net.devedemon.freshmobs.entity.general.ai.SkeletonAttackGoal;
import net.devedemon.freshmobs.sound.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
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

    protected static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(SkeletonWalkerEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final EntityDataAccessor<Byte> ATTACK_ANIM =
            SynchedEntityData.defineId(SkeletonWalkerEntity.class, EntityDataSerializers.BYTE);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation SLASH = RawAnimation.begin().thenPlay("slash");
    protected static final RawAnimation STAB = RawAnimation.begin().thenPlay("stab");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("death");
    protected static final RawAnimation PUMP_FAKE = RawAnimation.begin().thenPlay("pump_fake");
    private static final RawAnimation BANGING = RawAnimation.begin().thenPlay("banging");


    private final AnimatableInstanceCache geoCache =
            GeckoLibUtil.createInstanceCache(this);

    private static final int DEATH_LENGTH = 100;

    private int deathLengthInTicks = DEATH_LENGTH;

    private boolean attackLocked;

    public boolean isAttackLocked() {
        return attackLocked;
    }

    public void setAttackLocked(boolean value) {
        attackLocked = value;
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    protected AnimationController<SkeletonWalkerEntity> attackController =
            createAttackController();

    protected AnimationController<SkeletonWalkerEntity> createAttackController() {
        return new AnimationController<>(this, "attack", 1, this::attackingAnimation)
                .triggerableAnim("slash", SLASH)
                .triggerableAnim("stab", STAB)
                .triggerableAnim("pump_fake", PUMP_FAKE)
                .triggerableAnim("banging", BANGING);
    }


    private final AnimationController<SkeletonWalkerEntity>
            deathController = new AnimationController<>(this, "death", 0, this::deathAnimation)
            .triggerableAnim("death", DEATH);


    public SkeletonWalkerEntity(EntityType<? extends SkeletonWalkerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(ATTACK_ANIM, (byte) 0);
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
        this.goalSelector.addGoal(2, new AggroAnimationGoal(this));
        this.goalSelector.addGoal(3, new SkeletonAttackGoal(this, 1.0, false));
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

        controllers.add(this.attackController);

        controllers.add(this.deathController);
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
        this.setAttacking(false);
        this.setAttackLocked(false);
        this.clearAttackAnimation();

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

        if (this.isAttacking() || isDeadOrDying()) {
            return PlayState.STOP;
        }

        if (state.isMoving()) {
            return state.setAndContinue(WALK);
        }

        return state.setAndContinue(IDLE);
    }

    protected PlayState attackingAnimation(AnimationState<SkeletonWalkerEntity> state) {
        if(isDeadOrDying()) {
            return PlayState.STOP;
        }
        byte id = this.entityData.get(ATTACK_ANIM);
        if(id == 1) {
            return state.setAndContinue(SLASH);
        }
        if(id == 2) {
            return state.setAndContinue(STAB);
        }
        if(id == 3) {
            return state.setAndContinue(PUMP_FAKE);
        }
        if(id == 4) {
            return state.setAndContinue(BANGING);
        }
        return PlayState.STOP;
    }

    private PlayState deathAnimation(AnimationState<SkeletonWalkerEntity> state) {
        if (this.isDeadOrDying()) {
            return state.setAndContinue(DEATH);
        }
        return PlayState.STOP;
    }

    public void playAttackAnimation(String animation) {
        byte id = switch (animation) {
            case "slash" -> 1;
            case "stab" -> 2;
            case "pump_fake" -> 3;
            case "banging" -> 4;
            default -> 0;
        };
        this.entityData.set(ATTACK_ANIM, id);
    }

    public void clearAttackAnimation() {
        this.entityData.set(ATTACK_ANIM, (byte) 0);
    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (ATTACK_ANIM.equals(key)) {
            this.attackController.forceAnimationReset();
        }
        if (this.isDeadOrDying()) {
            this.attackController.stop();
            this.attackController.forceAnimationReset();
            this.deathController.forceAnimationReset();
        }
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

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SKULL_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SKULL_HURT.get();
    }
}
