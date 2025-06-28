package com.nikoverflow.overflowclient.injection.mixin.api;

import com.nikoverflow.overflowclient.module.Module;
import com.nikoverflow.overflowclient.setting.InternalSettings;
import com.nikoverflow.overflowclient.setting.Settings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Module.class)
public class ModuleMixin {

    @Shadow(remap = false)
    private Settings settings;

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void overflow$init(CallbackInfo ci) {
        settings = new InternalSettings();
    }
}