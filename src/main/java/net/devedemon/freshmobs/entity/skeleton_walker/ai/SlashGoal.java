package net.devedemon.freshmobs.entity.skeleton_walker.ai;

import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.PathfinderMob;

public class SlashGoal extends MeleeAttackGoal {

    private final SkeletonWalkerEntity entity;

    private int attackAnimationTick;
    private boolean attackStarted;
    private boolean animationFinished;
    private boolean attackPerformed;

    private static final int ATTACK_TIME = 13;
    private static final int ANIMATION_LENGTH = 35;

    public SlashGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedModifier, followingTargetEvenIfNotSeen);
        this.entity = (SkeletonWalkerEntity) mob;
    }

    @Override
    public boolean canUse() {
        if (entity.isAttacking()) {
            return false;
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {

        if (entity.isAttackLocked()) {
            return true;
        }

        return super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();

        entity.setAttackLocked(true);

        this.attackAnimationTick = 0;
        this.attackStarted = false;
        this.attackPerformed = false;
        this.animationFinished = false;
    }

    @Override
    public void tick() {
        super.tick();

        if (attackStarted) {
            attackAnimationTick++;

            if (attackAnimationTick == ATTACK_TIME && !attackPerformed) {
                performAttack();
            }

            if (attackAnimationTick >= ANIMATION_LENGTH) {
                attackStarted = false;
                entity.setAttacking(false);
                animationFinished = true;
                entity.setAttackLocked(false);
                resetAttackCooldown();
            }
        }
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target, double distanceSquared) {

        if(attackStarted || !animationFinished && entity.isAttacking()) {
            return;
        }

        double reach = getAttackReachSqr(target);

        if (distanceSquared <= reach && !attackStarted) {
            performAttackAnimation();
        }
    }

    private void performAttackAnimation() {
        attackStarted = true;
        attackAnimationTick = 0;
        attackPerformed = false;

        entity.setAttacking(true);
        entity.playAttackAnimation("slash");
    }

    private void performAttack() {
        LivingEntity target = mob.getTarget();

        if (target != null && isEnemyWithinAttackDistance(target)) {
            mob.swing(InteractionHand.MAIN_HAND);
            mob.doHurtTarget(target);
        }

        resetAttackCooldown();
        attackPerformed = true;
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity target) {
        return mob.distanceToSqr(target) <= getAttackReachSqr(target);
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        entity.setAttackLocked(false);
        super.stop();
    }
}