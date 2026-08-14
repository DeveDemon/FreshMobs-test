package net.devedemon.freshmobs.entity.warrior_skeleton_walker.client;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.warrior_skeleton_walker.main.WarriorSkeletonWalkerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WarriorSkeletonWalkerModel extends GeoModel<WarriorSkeletonWalkerEntity> {
    @Override
    public ResourceLocation getModelResource(WarriorSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "geo/entity/warrior_skeleton_walker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WarriorSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "texture/entity/warrior_skeleton_walker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WarriorSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "animations/entity/warrior_skeleton_walker.animation.json");
    }

}
