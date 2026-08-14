package net.devedemon.freshmobs.entity.general.main;

import net.devedemon.freshmobs.entity.general.ai.ArmoredAggroAnimationGoal;
import net.devedemon.freshmobs.entity.general.ai.SkeletonAttackGoal;
import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.devedemon.freshmobs.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class ArmoredSkeletonEntity extends SkeletonWalkerEntity {

    public ArmoredSkeletonEntity(EntityType<? extends SkeletonWalkerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new ArmoredAggroAnimationGoal(this));
        this.goalSelector.addGoal(3, new SkeletonAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 10f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected AnimationController<SkeletonWalkerEntity> createAttackController() {
        return new AnimationController<>(this, "attack", 1, this::attackingAnimation)
                .triggerableAnim("slash", SLASH)
                .triggerableAnim("stab", STAB)
                .triggerableAnim("pump_fake", PUMP_FAKE);
    }

    @Override
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
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.remove("attack");
        controllers.add(attackController);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SKULL_ARMORED_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.SKULL_ARMORED_HURT.get();
    }
}
