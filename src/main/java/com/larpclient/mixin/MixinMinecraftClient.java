package com.larpclient.mixin;

import com.larpclient.LarpClient;
import com.larpclient.module.modules.misc.FakeFPS;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "getCurrentFps", at = @At("RETURN"), cancellable = true)
    private static void fakeFPS(CallbackInfoReturnable<Integer> cir) {
        if (LarpClient.getInstance() == null) return;
        FakeFPS mod = LarpClient.getInstance().getModuleManager().get(FakeFPS.class);
        if (mod != null && mod.isEnabled()) {
            cir.setReturnValue(mod.getFakeFPS());
        }
    }
}
