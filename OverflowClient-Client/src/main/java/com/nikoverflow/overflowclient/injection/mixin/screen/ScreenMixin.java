package com.nikoverflow.overflowclient.injection.mixin.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
public class ScreenMixin {

    @Redirect(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V"))
    private void overflow$renderBackground(Screen instance, GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.blit(RenderType::guiTextured, ResourceLocation.fromNamespaceAndPath("overflowclient", "textures/background.png"), 0, 0, 0, 0, instance.width, instance.height, instance.width, instance.height);
    }
}
