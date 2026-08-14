package net.devedemon.freshmobs.entity.general.ai;

import net.devedemon.freshmobs.sound.ModSounds;
import net.minecraft.sounds.SoundEvent;

public enum AttackKind {
    SLASH(
            "slash",
            13,
            35,
            1.0D,
            ModSounds.SHIELD_SKELETON_SLASH.get()
    ),
    STAB(
            "stab",
            20,
            45,
            2.0D,
            ModSounds.SHIELD_SKELETON_STAB.get()
    ),

    PUMP_FAKE(
            "pump_fake",
            0,
            40,
            0.0D,
            null
    ),

    BANGING(
            "banging",
            0,
            65,
            0.0D,
            null
    );

    private final String animationName;
    private final int attackTime;
    private final int animationLength;
    private final double hitReachBonus;
    private final SoundEvent attackSound;

    AttackKind(String animationName, int attackTime, int animationLength,
               double hitReachBonus, SoundEvent attackSound) {
        this.animationName = animationName;
        this.attackTime = attackTime;
        this.animationLength = animationLength;
        this.hitReachBonus = hitReachBonus;
        this.attackSound = attackSound;
    }

    public String getAnimationName() {
        return animationName;
    }

    public int getAttackTime() {
        return attackTime;
    }

    public int getAnimationLength() {
        return animationLength;
    }

    public double getHitReachBonus() {
        return hitReachBonus;
    }

    public SoundEvent getAttackSound() {
        return attackSound;
    }

}
