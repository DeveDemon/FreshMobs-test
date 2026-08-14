package net.devedemon.freshmobs.entity.general.ai;

import net.devedemon.freshmobs.entity.general.main.ArmoredSkeletonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class ArmoredAggroAnimationGoal extends Goal {

    private final ArmoredSkeletonEntity entity;
    private AttackKind currentAnim;
    private int tick;
    private int lastPlayedTargetId = Integer.MIN_VALUE;

    public ArmoredAggroAnimationGoal(ArmoredSkeletonEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (entity.isAttacking() || entity.isAttackLocked()) {
            return false;
        }

        LivingEntity target = entity.getTarget();
        if (!(target instanceof Player) || !target.isAlive()) {
            lastPlayedTargetId = Integer.MIN_VALUE;
            return false;
        }

        return target.getId() != lastPlayedTargetId;
    }

    @Override
    public boolean canContinueToUse() {
        return currentAnim != null && tick < currentAnim.getAnimationLength();
    }

    @Override
    public void start() {
        LivingEntity target = entity.getTarget();
        if (target != null) {
            lastPlayedTargetId = target.getId();
        }

        currentAnim = AttackKind.PUMP_FAKE;
        tick = 0;

        entity.setAttacking(true);
        entity.setAttackLocked(true);
        entity.getNavigation().stop();
        entity.playAttackAnimation(currentAnim.getAnimationName());
    }

    @Override
    public void tick() {
        tick++;
        entity.getNavigation().stop();

        LivingEntity target = entity.getTarget();
        if (target != null) {
            entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }

    @Override
    public void stop() {
        currentAnim = null;
        tick = 0;
        entity.setAttacking(false);
        entity.setAttackLocked(false);
        entity.clearAttackAnimation();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}