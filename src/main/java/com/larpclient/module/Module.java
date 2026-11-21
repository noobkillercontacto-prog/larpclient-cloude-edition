package com.larpclient.module;

import com.larpclient.event.events.EventRender2D;
import com.larpclient.event.events.EventRender3D;
import com.larpclient.event.events.EventMotion;
import com.larpclient.module.setting.Setting;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {

    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled = false;

    protected final List<Setting<?>> settings = new ArrayList<>();

    private KeyBinding keyBinding;
    private final int defaultKey;

    public Module(String name, String description, Category category, int defaultKey) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.defaultKey = defaultKey;
    }

    public Module(String name, String description, Category category) {
        this(name, description, category, GLFW.GLFW_KEY_UNKNOWN);
    }

    public void registerKeybind() {
        if (defaultKey == GLFW.GLFW_KEY_UNKNOWN) return;
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.larpclient." + name.toLowerCase().replace(" ", "_"),
            InputUtil.Type.KEYSYM,
            defaultKey,
            "category.larpclient"
        ));
    }

    public boolean wasPressed() {
        return keyBinding != null && keyBinding.wasPressed();
    }

    // Lifecycle hooks
    public void onEnable()  {}
    public void onDisable() {}
    public void onTick()    {}
    public void onRender2D(EventRender2D event) {}
    public void onRender3D(EventRender3D event) {}
    public void onMotion(EventMotion event)     {}

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void setEnabled(boolean enabled) {
        if (this.enabled != enabled) toggle();
    }

    public boolean isEnabled()       { return enabled; }
    public String getName()          { return name; }
    public String getDescription()   { return description; }
    public Category getCategory()    { return category; }
    public List<Setting<?>> getSettings() { return settings; }

    protected boolean nullCheck() {
        return mc.player != null && mc.world != null;
    }
}
