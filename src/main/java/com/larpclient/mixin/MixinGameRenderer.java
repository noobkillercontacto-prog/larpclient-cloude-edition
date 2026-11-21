package com.larpclient.mixin;

import com.larpclient.LarpClient;
import com.larpclient.event.events.EventRender2D;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(
        method = "render",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;draw()V",
            shift = At.Shift.BEFORE)
    )
    private void onRender2D(CallbackInfo ci) {
        // 2D HUD render — fired before DrawContext.draw() flushes
        // EventRender2D dispatch happens via InGameHud mixin below
    }
}
