package com.eternity.jessemood.client.mixin;


import com.eternity.jessemood.client.guihandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class PlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            PlayerEntity player = client.player;
            World world = player.getWorld();

            boolean isInWorld = world != null;
            boolean isAlive = player.isAlive();

            if (isInWorld && isAlive) {
                if (player.getHealth()<=3&&!player.isCreative()){
                    guihandler.display();
                }

            }
        }
    }
}