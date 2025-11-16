package com.eternity.jessemood.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiHandler {
    private static final int NUMBER_OF_IMAGES = 7;
    private static final int DISPLAY_DURATION = 500;
    private static long startTime = -1; // Time when the image starts displaying
    private static long lastTime = -1;
    private static Identifier image_id = Identifier.of("eternaljesus", "textures/gui/jesus0.png");
    private static final List<Identifier> images = new ArrayList<>();
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    public static void init() {
        for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
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

        context.drawTexture(RenderPipelines.GUI_TEXTURED, image_id, 0, 0, 0, 0,
                screenWidth, screenHeight, screenWidth, screenHeight, ColorHelper.withAlpha(opacity, -1));
    }

    public static Identifier getImageId() {
        return image_id;
    }
}
