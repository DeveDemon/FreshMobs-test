package net.devedemon.freshmobs.entity.skeleton_walker.ai;

import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.PathfinderMob;

public class SkeletonAttackGoal extends MeleeAttackGoal {

    private final SkeletonWalkerEntity entity;

    private AttackKind currentAttack;
    private int attackAnimationTick;
    private boolean attackStarted;
    private boolean attackPerformed;

    public SkeletonAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
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
        if (attackStarted || entity.isAttackLocked()) {
            return true;
        }
        return super.canContinueToUse();
    }

    @Override
    public void start() {
        super.start();
        resetSwingState();
    }

    @Override
    public void stop() {
        if (attackStarted) {
            finishAttack(false);
        } else {
            entity.setAttacking(false);
            entity.setAttackLocked(false);
            entity.clearAttackAnimation();
        }
        resetSwingState();
        super.stop();
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();

        if (attackStarted) {
            this.mob.getNavigation().stop();
            if (target != null) {
                this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            attackAnimationTick++;

            if (currentAttack != null
                    && attackAnimationTick == currentAttack.getAttackTime()
                    && !attackPerformed) {
                performAttack();
            }

            if (currentAttack != null && attackAnimationTick >= currentAttack.getAnimationLength()) {
                finishAttack(true);
            }
            return;
        }

        super.tick();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target, double distanceSquared) {
        if (attackStarted || entity.isAttacking()) {
            return;
        }

        AttackKind chosen = chooseAttack(target, distanceSquared);
        if (chosen != null) {
            beginAttack(chosen);
        }
    }

    private AttackKind chooseAttack(LivingEntity target, double distanceSquared) {
        double baseReach = Math.sqrt(getAttackReachSqr(target));

        double slashStart = baseReach + AttackKind.SLASH.getStartReachBonus();
        double stabStart = baseReach + AttackKind.STAB.getStartReachBonus();

        double dist = Math.sqrt(distanceSquared);

        if (dist <= slashStart) {
            return AttackKind.SLASH;
        }
        if (dist <= stabStart) {
            return AttackKind.STAB;
        }
        return null;
    }

    private void beginAttack(AttackKind kind) {

        this.currentAttack = kind;
        this.attackStarted = true;
        this.attackAnimationTick = 0;
        this.attackPerformed = false;

        entity.setAttacking(true);
        entity.setAttackLocked(true);
        this.mob.getNavigation().stop();
        entity.playAttackAnimation(kind.getAnimationName());
    }

    private void performAttack() {
        LivingEntity target = mob.getTarget();

        if (target != null && currentAttack != null && isWithinHitReach(target, currentAttack)) {
            mob.swing(InteractionHand.MAIN_HAND);
            mob.doHurtTarget(target);
        }

        resetAttackCooldown();
        attackPerformed = true;
    }

    private boolean isWithinHitReach(LivingEntity target, AttackKind kind) {
        double baseReach = Math.sqrt(getAttackReachSqr(target));
        double hitReach = baseReach + kind.getHitReachBonus();
        return mob.distanceToSqr(target) <= hitReach * hitReach;
    }

    private void finishAttack(boolean resetCooldown) {
        attackStarted = false;
        currentAttack = null;
        entity.setAttacking(false);
        entity.setAttackLocked(false);
        entity.clearAttackAnimation();
        if (resetCooldown) {
            resetAttackCooldown();
        }
    }

    private void resetSwingState() {
        this.currentAttack = null;
        this.attackAnimationTick = 0;
        this.attackStarted = false;
        this.attackPerformed = false;
    }
}
