package com.larpclient.mixin;

import com.larpclient.LarpClient;
import com.larpclient.module.modules.visuals.NoWeather;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {
    // Placeholder — NoWeather is handled in WorldRenderer mixin above
}
