package com.eternity.jessemood.mixins;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class PlayerMixin {

    @Inject(method = "hurt", at = @At("HEAD"))
    public void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        System.out.println("Player was hurt!");
        com.eternity.jessemood.client.guihandler.triggerDisplay(); // Set the display flag
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (player.isAlive() && player.getHealth() <= 3 && !player.isCreative()) {
            System.out.println("Player is low on health!");
            com.eternity.jessemood.client.guihandler.triggerDisplay(); // Set the display flag
        }
    }
}
