package com.larpclient.mixin;

import com.larpclient.LarpClient;
import com.larpclient.event.events.EventRender3D;
import com.larpclient.module.modules.visuals.NoWeather;
import com.larpclient.module.modules.visuals.XRay;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void noWeather(LightmapTextureManager lm, float td, double x, double y, double z, CallbackInfo ci) {
        if (LarpClient.getInstance() == null) return;
        NoWeather mod = LarpClient.getInstance().getModuleManager().get(NoWeather.class);
        if (mod != null && mod.isEnabled()) ci.cancel();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender3D(MatrixStack matrices, float tickDelta, long limitTime,
                             boolean renderBlockOutline, Camera camera,
                             GameRenderer gameRenderer, LightmapTextureManager lm,
                             net.minecraft.util.math.Matrix4f projectionMatrix, CallbackInfo ci) {
        if (LarpClient.getInstance() == null) return;
        EventRender3D event = new EventRender3D(tickDelta, camera);
        LarpClient.getInstance().getModuleManager().onRender3D(event);
    }
}
