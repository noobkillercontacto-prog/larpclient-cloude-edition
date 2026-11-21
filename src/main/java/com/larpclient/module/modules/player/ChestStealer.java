package com.larpclient.module.modules.player;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealer extends Module {
    private final SliderSetting delay = new SliderSetting("Delay ms", 50.0, 10.0, 500.0);
    private long last = 0;

    public ChestStealer() {
        super("ChestStealer", "Automatically loot chests", Category.PLAYER);
        settings.add(delay);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!(mc.currentScreen instanceof GenericContainerScreen screen)) return;
        long now = System.currentTimeMillis();
        if (now - last < delay.intValue()) return;

        // Shift-click first non-empty slot in chest (top half of container)
        int chestSize = screen.getScreenHandler().slots.size() - 36;
        for (int i = 0; i < chestSize; i++) {
            if (!screen.getScreenHandler().slots.get(i).getStack().isEmpty()) {
                mc.interactionManager.clickSlot(
                    screen.getScreenHandler().syncId, i, 0, SlotActionType.QUICK_MOVE, mc.player);
                last = now;
                return;
            }
        }
    }
}
