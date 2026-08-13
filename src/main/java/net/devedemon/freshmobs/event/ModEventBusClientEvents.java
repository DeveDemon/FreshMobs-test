package net.devedemon.freshmobs.event;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.general.ModEntities;
import net.devedemon.freshmobs.entity.skeleton_walker.client.SkeletonWalkerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FreshMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SKELETON_WALKER.get(), SkeletonWalkerRenderer::new);
    }
}
