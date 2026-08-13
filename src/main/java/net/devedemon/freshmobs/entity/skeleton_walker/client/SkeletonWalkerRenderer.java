package net.devedemon.freshmobs.entity.skeleton_walker.client;

import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkeletonWalkerRenderer extends GeoEntityRenderer<SkeletonWalkerEntity> {
    public SkeletonWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonWalkerModel());
        this.shadowRadius = 0.5f;
    }
}
