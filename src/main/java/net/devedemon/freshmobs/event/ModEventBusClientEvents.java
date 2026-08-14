package net.devedemon.freshmobs.event;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.armored_skeleton_walker.client.ArmoredSkeletonWalkerRenderer;
import net.devedemon.freshmobs.entity.general.ModEntities;
import net.devedemon.freshmobs.entity.skeleton_walker.client.SkeletonWalkerRenderer;
import net.devedemon.freshmobs.entity.warrior_skeleton_walker.client.WarriorSkeletonWalkerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FreshMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SKELETON_WALKER.get(), SkeletonWalkerRenderer::new);
        event.registerEntityRenderer(ModEntities.ARMORED_SKELETON_WALKER.get(), ArmoredSkeletonWalkerRenderer::new);
        event.registerEntityRenderer(ModEntities.WARRIOR_SKELETON_WALKER.get(), WarriorSkeletonWalkerRenderer::new);
    }
}
