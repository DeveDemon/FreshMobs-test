package net.devedemon.freshmobs.entity.armored_skeleton_walker.client;

import net.devedemon.freshmobs.entity.armored_skeleton_walker.main.ArmoredSkeletonWalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArmoredSkeletonWalkerRenderer extends GeoEntityRenderer<ArmoredSkeletonWalkerEntity> {
    public ArmoredSkeletonWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmoredSkeletonWalkerModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    protected float getDeathMaxRotation(ArmoredSkeletonWalkerEntity animatable) {
        return 0.0F;
    }
}
