package com.nikoverflow.overflowclient.injection.mixin.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SplashManager;getSplash()Lnet/minecraft/client/gui/components/SplashRenderer;"))
    private SplashRenderer overflow$init(SplashManager instance) {
        return null;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V"))
    private void overflow$render(TitleScreen instance, GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.blit(RenderType::guiTextured, ResourceLocation.fromNamespaceAndPath("overflowclient", "textures/background.png"), 0, 0, 0, 0, instance.width, instance.height, instance.width, instance.height);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;realmsNotificationsEnabled()Z"))
    private boolean overflow$render$2(TitleScreen instance) {
        return false;
    }
}
