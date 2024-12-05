package com.eternity.jessemood.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class guihandler {
    private static final int NUMBEROFIMAGES=5;
    private static final int DISPLAY_DURATION = 500;
    private static long startTime = -1; // Time when the image starts displaying
    private static long lastTime = -1;
    private static Identifier image_id = Identifier.of("eternaljesus", "textures/gui/jesus0.png");
    private static List<Identifier> images = new ArrayList<Identifier>();
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    public static void init() {
        for (int i = 0; i < NUMBEROFIMAGES; i++) {
            images.add(Identifier.of("eternaljesus", "textures/gui/jesus"+i+".png"));
        }
    }
    public static void playLocalSound(SoundEvent soundEvent, PlayerEntity player) {
        if (MinecraftClient.getInstance().world != null) {
            MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().world.playSound(
                    player,              // Player to play the sound to
                    player.getX(),       // X position
                    player.getY(),       // Y position
                    player.getZ(),       // Z position
                    soundEvent,                 // Your sound event
                    SoundCategory.MASTER,       // Category
                    30.0F,                       // Volume
                    1.0F                        // Pitch
            ));
        }
    }
    public static void display() {
        if (MinecraftClient.getInstance().world !=null && MinecraftClient.getInstance().player != null) {
            if (lastTime == -1 || System.currentTimeMillis() - lastTime > DISPLAY_DURATION *2) {
                image_id = images.get(getRandomNumberUsingNextInt(0, images.size()));
                startTime = System.currentTimeMillis();
                lastTime = System.currentTimeMillis();
                playLocalSound(JessemoodClient.JESUS_BELL_SOUND, MinecraftClient.getInstance().player);
            }
        }
    }
    public static void render(DrawContext context) {
        if (startTime < 0 || MinecraftClient.getInstance().world == null) {
            return; // Do not render if the display hasn't started
        }

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= DISPLAY_DURATION) {
            startTime = -1; // Reset startTime to stop rendering
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        float opacity = 1.0f - (float) elapsedTime / DISPLAY_DURATION;
        RenderSystem.setShaderTexture(0, image_id);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, opacity);

        context.drawTexture(image_id, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);

        RenderSystem.disableBlend();
    }
}
