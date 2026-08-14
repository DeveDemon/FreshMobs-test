package net.devedemon.freshmobs.entity.general;

import net.devedemon.freshmobs.FreshMobsMod;
import net.devedemon.freshmobs.entity.armored_skeleton_walker.main.ArmoredSkeletonWalkerEntity;
import net.devedemon.freshmobs.entity.skeleton_walker.main.SkeletonWalkerEntity;
import net.devedemon.freshmobs.entity.warrior_skeleton_walker.main.WarriorSkeletonWalkerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FreshMobsMod.MOD_ID);

    public static final RegistryObject<EntityType<SkeletonWalkerEntity>> SKELETON_WALKER =
            ENTITY_TYPES.register("skeleton_walker",
                    () -> EntityType.Builder.of(SkeletonWalkerEntity::new, MobCategory.MONSTER)
                            .sized(0.8125f, 2f).build("skeleton_walker"));

    public static final RegistryObject<EntityType<ArmoredSkeletonWalkerEntity>> ARMORED_SKELETON_WALKER =
            ENTITY_TYPES.register("armored_skeleton_walker",
                    () -> EntityType.Builder.of(ArmoredSkeletonWalkerEntity::new, MobCategory.MONSTER)
                            .sized(0.8125f, 2f).build("armored_skeleton_walker"));

    public static final RegistryObject<EntityType<WarriorSkeletonWalkerEntity>> WARRIOR_SKELETON_WALKER =
            ENTITY_TYPES.register("warrior_skeleton_walker",
                    () -> EntityType.Builder.of(WarriorSkeletonWalkerEntity::new, MobCategory.MONSTER)
                            .sized(0.8125f, 2f).build("warrior_skeleton_walker"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
