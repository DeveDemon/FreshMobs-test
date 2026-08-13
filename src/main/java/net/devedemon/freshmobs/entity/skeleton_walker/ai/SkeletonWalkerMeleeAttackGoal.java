package net.devedemon.freshmobs.entity.skeleton_walker.ai;

import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class SkeletonWalkerMeleeAttackGoal extends MeleeAttackGoal {
    private final SkeletonWalkerEntity skeletonWalker;
    private AttackType currentAttack = null;
    private int attackDelay = 0;
    private int ticksUntilNextAttack;
    private boolean shouldCountAttack;
    private boolean hasHit = false;

    private enum AttackType {
        SLASH("slash", 35, 13, 0),
        STAB("stab", 45, 20, 0.5);

        private final String animationName;
        private final int animationLength;
        private final int hitTick;
        private final double extraRange;

        AttackType(String animationName, int animationLength, int hitTick, double extraRange) {
            this.animationName = animationName;
            this.animationLength = animationLength;
            this.hitTick = hitTick;
            this.extraRange = extraRange;

        }
    }


    public SkeletonWalkerMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        skeletonWalker = ((SkeletonWalkerEntity) pMob);
    }

    @Override
    public void start() {
        super.start();

        this.currentAttack = chooseRandomAttack();
        this.attackDelay = 0;
        this.ticksUntilNextAttack = 20;
        this.shouldCountAttack = false;
        this.hasHit = false;
    }

    @Override
    public void tick() {
        super.tick();

        if(!this.shouldCountAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            return;
        }

        this.attackDelay++;

        if(this.attackDelay >= this.currentAttack.hitTick && !this.hasHit) {
            LivingEntity target = this.skeletonWalker.getTarget();

            if (target != null && target.isAlive()) {
                this.mob.doHurtTarget(target);
            }

            this.hasHit = true;

        }
        if (
                this.attackDelay >= this.currentAttack.animationLength
        ) {
            this.shouldCountAttack = false;
            this.ticksUntilNextAttack = 10;
            this.attackDelay = 0;
            this.currentAttack = null;
            this.hasHit = false;
        }
    }



    @Override
    protected void checkAndPerformAttack(
            LivingEntity pEnemy,
            double pDistToEnemySqr
    ) {
        if (this.shouldCountAttack || this.ticksUntilNextAttack > 0) {
            return;
        }


        AttackType attack = chooseRandomAttack();
        double attackRange = Math.pow(Math.sqrt(this.getAttackReachSqr(pEnemy)) + attack.extraRange, 2);
        if(pDistToEnemySqr > attackRange) {
            return;
        }
        this.currentAttack = attack;
        this.attackDelay = 0;
        this.shouldCountAttack = true;

        this.skeletonWalker.triggerAnim("attack", this.currentAttack.animationName);

    }

    private AttackType chooseRandomAttack() {
        if(this.skeletonWalker.getRandom().nextBoolean()) {
            return AttackType.SLASH;
        } else{
            return AttackType.STAB;
        }
    }

    @Override
    public void stop() {
        super.stop();

        this.currentAttack = null;
        this.attackDelay = 0;
        this.ticksUntilNextAttack = 20;
        this.shouldCountAttack = false;
    }
}
