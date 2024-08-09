package com.eternity.jessemood.client;

import com.eternity.jessemood.client.mixin.PlayerMixin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class JessemoodClient implements ClientModInitializer {
    public static final SoundEvent JESUS_BELL_SOUND = Registry.register(Registries.SOUND_EVENT, Identifier.of("eternaljesus", "jesus_bell"),
            SoundEvent.of(Identifier.of("eternaljesus", "jesus_bell")));

    @Override
    public void onInitializeClient() {
        guihandler.init();
        HudRenderCallback.EVENT.register((DrawContext context, RenderTickCounter counter) -> {
        guihandler.render (context);
    });
    }
}
