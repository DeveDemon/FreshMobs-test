package net.devedemon.freshmobs.entity.skeleton_walker.ai;

public enum AttackKind {
    SLASH(
            "slash",
            13,
            35,
            0.0D,
            1.0D
    ),
    STAB(
            "stab",
            20,
            45,
            1.5D,
            2.5D
    ),

    PUMP_FAKE(
            "pump_fake",
            0,
            40,
            0.0D,
            0.0D
    ),

    BANGING(
            "banging",
            0,
            65,
            0.0D,
            0.0D
    );

    private final String animationName;
    private final int attackTime;
    private final int animationLength;
    private final double startReachBonus;
    private final double hitReachBonus;

    AttackKind(String animationName, int attackTime, int animationLength,
               double startReachBonus, double hitReachBonus) {
        this.animationName = animationName;
        this.attackTime = attackTime;
        this.animationLength = animationLength;
        this.startReachBonus = startReachBonus;
        this.hitReachBonus = hitReachBonus;
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

    public double getStartReachBonus() {
        return startReachBonus;
    }

    public double getHitReachBonus() {
        return hitReachBonus;
    }

    public boolean isMeleeSelectable() {
        return this == SLASH || this == STAB;
    }
}
