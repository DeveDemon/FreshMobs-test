package net.devedemon.freshmobs.entity.warrior_skeleton_walker.client;

import net.devedemon.freshmobs.entity.warrior_skeleton_walker.main.WarriorSkeletonWalkerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WarriorSkeletonWalkerRenderer extends GeoEntityRenderer<WarriorSkeletonWalkerEntity> {

    public WarriorSkeletonWalkerRenderer(EntityRendererProvider.Context context) {
        super(context, new WarriorSkeletonWalkerModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    protected float getDeathMaxRotation(WarriorSkeletonWalkerEntity animatable) {
        return 0.0F;
    }
}
