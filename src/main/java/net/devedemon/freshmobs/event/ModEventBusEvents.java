package net.devedemon.freshmobs.event;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.armored_skeleton_walker.main.ArmoredSkeletonWalkerEntity;
import net.devedemon.freshmobs.entity.general.ModEntities;
import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.devedemon.freshmobs.entity.warrior_skeleton_walker.main.WarriorSkeletonWalkerEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FreshMobsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SKELETON_WALKER.get(), SkeletonWalkerEntity.createAttributes().build());
        event.put(ModEntities.ARMORED_SKELETON_WALKER.get(), ArmoredSkeletonWalkerEntity.createAttributes().build());
        event.put(ModEntities.WARRIOR_SKELETON_WALKER.get(), WarriorSkeletonWalkerEntity.createAttributes().build());
    }
}
