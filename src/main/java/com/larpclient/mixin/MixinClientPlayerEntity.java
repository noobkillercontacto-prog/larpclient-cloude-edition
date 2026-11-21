package com.larpclient.mixin;

import com.larpclient.LarpClient;
import com.larpclient.event.events.EventMotion;
import com.larpclient.module.modules.combat.AntiKnockback;
import com.larpclient.module.modules.movement.NoJumpDelay;
import com.larpclient.module.modules.movement.NoSlowdown;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onMotion(CallbackInfo ci) {
        if (LarpClient.getInstance() == null) return;
        ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
        EventMotion event = new EventMotion(
            player.getYaw(), player.getPitch(),
            player.getX(), player.getY(), player.getZ(),
            player.isOnGround()
        );
        LarpClient.getInstance().getModuleManager().onMotion(event);

        // Apply modified values
        player.setYaw(event.getYaw());
        player.setPitch(event.getPitch());
    }

    // Anti-knockback: modify velocity received from server
    @ModifyArg(
        method = "applyMovementInput",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;setVelocity(DDD)V"),
        index = 0
    )
    private double modifyVelX(double x) {
        if (LarpClient.getInstance() == null) return x;
        AntiKnockback mod = LarpClient.getInstance().getModuleManager().get(AntiKnockback.class);
        return (mod != null && mod.isEnabled()) ? mod.modifyH(x) : x;
    }

    // No-jump-delay: clear jumping cooldown
    @Inject(method = "tick", at = @At("HEAD"))
    private void noJumpDelay(CallbackInfo ci) {
        if (LarpClient.getInstance() == null) return;
        NoJumpDelay mod = LarpClient.getInstance().getModuleManager().get(NoJumpDelay.class);
        if (mod != null && mod.isEnabled()) {
            ClientPlayerEntity player = (ClientPlayerEntity)(Object)this;
            // jumpingCooldown is private — use accessor or reflection
            // FILL: add @Accessor interface for jumpingCooldown or use @Shadow
        }
    }
}
