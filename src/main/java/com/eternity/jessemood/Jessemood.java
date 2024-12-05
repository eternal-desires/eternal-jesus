package com.eternity.jessemood;

import com.eternity.jessemood.client.JessemoodClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Jessemood.MODID)
public class Jessemood {
    public static final String MODID = "eternaljesus";
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> JESUS_BELL_SOUND = registerSoundEvents("jesus_bell");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Jessemood.MODID, name)));
    }

    public Jessemood() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        // Register your Deferred Registers here
        SOUNDS.register(modEventBus);

        JessemoodClient.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common setup tasks, if any
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup tasks, if any
    }
}
