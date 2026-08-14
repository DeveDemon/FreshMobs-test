package net.devedemon.freshmobs.entity.warrior_skeleton_walker.main;

import net.devedemon.freshmobs.entity.general.main.ArmoredSkeletonEntity;
import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class WarriorSkeletonWalkerEntity extends ArmoredSkeletonEntity {

    public WarriorSkeletonWalkerEntity(EntityType<? extends SkeletonWalkerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SkeletonWalkerEntity.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25D)
                .add(Attributes.ARMOR, 4D)
                .add(Attributes.ATTACK_DAMAGE, 7D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public int getExperienceReward() {
        return 45;
    }
}
