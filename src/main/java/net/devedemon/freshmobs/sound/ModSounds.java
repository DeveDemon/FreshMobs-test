package net.devedemon.freshmobs.sound;

import net.devedemon.freshmobs.FreshMobsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FreshMobsMod.MOD_ID);

    public static final RegistryObject<SoundEvent>  SHIELD_SKELETON_SLASH = registerSoundEvents("shield_skeleton_slash");
    public static final RegistryObject<SoundEvent>  SHIELD_SKELETON_STAB = registerSoundEvents("shield_skeleton_stab");
    public static final RegistryObject<SoundEvent>  SKULL_DEATH = registerSoundEvents("skull_death");
    public static final RegistryObject<SoundEvent>  SKULL_HURT = registerSoundEvents("skull_hurt");
    public static final RegistryObject<SoundEvent>  SKULL_ARMORED_DEATH = registerSoundEvents("skull_armored_death");
    public static final RegistryObject<SoundEvent>  SKULL_ARMORED_HURT = registerSoundEvents("skull_armored_hurt");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent
                .createVariableRangeEvent(new ResourceLocation(FreshMobsMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
