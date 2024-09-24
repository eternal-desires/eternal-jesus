package com.eternity.jessemood.client;

import com.eternity.jessemood.Jessemood;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class guihandler {

    private static final int NUMBEROFIMAGES = 5;
    private static final int DISPLAY_DURATION = 500;
    private static long startTime = -1;
    private static boolean shouldDisplay = false;

    public static ResourceLocation image_id = new ResourceLocation(Jessemood.MODID, "textures/gui/jesus0.png");
    public static final List<ResourceLocation> images = new ArrayList<>();

    public static void init() {
        for (int i = 0; i < NUMBEROFIMAGES; i++) {
            ResourceLocation image = new ResourceLocation(Jessemood.MODID, "textures/gui/jesus" + i + ".png");
            images.add(image);
        }
    }

    public static void triggerDisplay() {
        if (!shouldDisplay) {
            shouldDisplay = true;
            display();
        }
    }

    public static void display() {
        System.out.println("attempting to display...");
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && mc.player != null) {
            if (startTime == -1 || System.currentTimeMillis() - startTime > DISPLAY_DURATION * 2) {
                if (!images.isEmpty()) {
                    image_id = images.get(new Random().nextInt(images.size()));
                } else {
                    System.err.println("Error: images list is empty.");
                    return;
                }

                startTime = System.currentTimeMillis();

                // Play sound here
                playLocalSound(Jessemood.JESUS_BELL_SOUND.get(), mc.player);
            }
        }
    }

    public static void playLocalSound(SoundEvent soundEvent, Player player) {
        if (soundEvent == null) {
            System.err.println("Sound event not present.");
            return;
        }

        Minecraft.getInstance().execute(() -> {
            Minecraft.getInstance().level.playSound(
                    player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    soundEvent,
                    SoundSource.MASTER,
                    30.0F,
                    1.0F
            );
        });
    }

    public static void render(GuiGraphics guiGraphics) {
        System.out.println("attempting to render...");
        if (!shouldDisplay || startTime < 0 || Minecraft.getInstance().level == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= DISPLAY_DURATION) {
            startTime = -1;
            shouldDisplay = false;
            return;
        }

        Minecraft client = Minecraft.getInstance();
        int screenWidth = client.getWindow().getGuiScaledWidth();
        int screenHeight = client.getWindow().getGuiScaledHeight();

        float opacity = 1.0f - (float) elapsedTime / DISPLAY_DURATION;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, image_id); // Ensure texture is set
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, opacity);

        guiGraphics.blit(image_id, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);

        RenderSystem.disableBlend();
    }
}
