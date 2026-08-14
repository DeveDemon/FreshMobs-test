package net.devedemon.freshmobs.entity.armored_skeleton_walker.client;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.armored_skeleton_walker.main.ArmoredSkeletonWalkerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ArmoredSkeletonWalkerModel extends GeoModel<ArmoredSkeletonWalkerEntity> {
    @Override
    public ResourceLocation getModelResource(ArmoredSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "geo/entity/armored_skeleton_walker.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArmoredSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "texture/entity/armored_skeleton_walker.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArmoredSkeletonWalkerEntity animatable) {
        return new ResourceLocation(FreshMobsMod.MOD_ID,
                "animations/entity/armored_skeleton_walker.animation.json");
    }

}
