package com.nikoverflow.overflowclient.injection.mixin;

import com.nikoverflow.overflowclient.OverflowClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void overflow$init(CallbackInfo ci) {
        OverflowClient.getInstance().lateInitialize();
    }

    @Inject(method = "createTitle", at = @At("HEAD"), cancellable = true)
    private void overflow$createTitle(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("Overflow Client v" + FabricLoader.getInstance().getModContainer("overflowclient").get().getMetadata().getVersion().toString());
    }
}