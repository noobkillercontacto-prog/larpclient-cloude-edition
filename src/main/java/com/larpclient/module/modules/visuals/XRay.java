package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.HashSet;
import java.util.Set;

public class XRay extends Module {
    private final BooleanSetting diamonds  = new BooleanSetting("Diamonds",  true);
    private final BooleanSetting ancient   = new BooleanSetting("Ancient Debris", true);
    private final BooleanSetting iron      = new BooleanSetting("Iron",      false);
    private final BooleanSetting gold      = new BooleanSetting("Gold",      false);
    private final BooleanSetting emerald   = new BooleanSetting("Emerald",   false);

    public XRay() {
        super("XRay", "Highlight ores through blocks", Category.VISUALS);
        settings.add(diamonds); settings.add(ancient); settings.add(iron);
        settings.add(gold); settings.add(emerald);
    }

    public Set<Block> getTargetBlocks() {
        Set<Block> set = new HashSet<>();
        if (diamonds.isEnabled())  { set.add(Blocks.DIAMOND_ORE); set.add(Blocks.DEEPSLATE_DIAMOND_ORE); }
        if (ancient.isEnabled())   { set.add(Blocks.ANCIENT_DEBRIS); }
        if (iron.isEnabled())      { set.add(Blocks.IRON_ORE); set.add(Blocks.DEEPSLATE_IRON_ORE); }
        if (gold.isEnabled())      { set.add(Blocks.GOLD_ORE); set.add(Blocks.DEEPSLATE_GOLD_ORE); }
        if (emerald.isEnabled())   { set.add(Blocks.EMERALD_ORE); set.add(Blocks.DEEPSLATE_EMERALD_ORE); }
        return set;
    }

    @Override
    public void onEnable()  { if (nullCheck()) mc.worldRenderer.reload(); }
    @Override
    public void onDisable() { if (nullCheck()) mc.worldRenderer.reload(); }
}
