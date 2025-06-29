package com.nikoverflow.overflowclient.injection.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void overflow$render(GuiGraphics guiGraphics, int width, int height, boolean spin, CallbackInfo ci) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath("overflowclient", "textures/background.png"), 0, 0, 0, 0, width, height, width, height);
        ci.cancel();
    }
}