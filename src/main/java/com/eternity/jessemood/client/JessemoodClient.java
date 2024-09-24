package com.eternity.jessemood.client;

import com.eternity.jessemood.Jessemood;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = Jessemood.MODID, value = Dist.CLIENT)
public class JessemoodClient {

    public static void init() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(JessemoodClient::clientSetup);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        System.out.println("Client setup initiated.");
        guihandler.init();
        MinecraftForge.EVENT_BUS.register(JessemoodClient.class);
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        // Log every overlay that triggers this event to see what is being called
        // System.out.println("Overlay Render Event: " + event.getOverlay().id().toString());

        // Test with a specific overlay ID or with event conditions as needed
        if (event.getOverlay().id().toString().equals("minecraft:player_health")) {
            guihandler.render(event.getGuiGraphics());
        }
    }

}
