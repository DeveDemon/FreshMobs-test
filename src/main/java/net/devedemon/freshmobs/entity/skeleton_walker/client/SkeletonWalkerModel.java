package net.devedemon.freshmobs.entity.skeleton_walker.client;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SkeletonWalkerModel extends GeoModel<SkeletonWalkerEntity> {
    @Override
    public ResourceLocation getModelResource(SkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "geo/entity/skeleton_walker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "texture/entity/skeleton_walker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "animations/entity/skeleton_walker.animation.json");
    }

}
