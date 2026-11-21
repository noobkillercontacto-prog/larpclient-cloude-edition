package com.larpclient.module.modules.misc;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class FakePlayer extends Module {
    private final List<OtherClientPlayerEntity> fakes = new ArrayList<>();

    public FakePlayer() {
        super("FakePlayer", "Spawns a local fake player for combat and visual-effect testing", Category.MISC);
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) return;
        OtherClientPlayerEntity fake = new OtherClientPlayerEntity(
            mc.world, mc.player.getGameProfile());
        fake.setPosition(mc.player.getPos());
        fake.setYaw(mc.player.getYaw());
        fake.setPitch(mc.player.getPitch());
        mc.world.addEntity(fake);
        fakes.add(fake);
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        for (Entity e : fakes) mc.world.removeEntity(e.getId(), Entity.RemovalReason.DISCARDED);
        fakes.clear();
    }
}
