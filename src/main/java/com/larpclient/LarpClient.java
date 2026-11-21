package com.larpclient;

import com.larpclient.event.EventBus;
import com.larpclient.module.ModuleManager;
import com.larpclient.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LarpClient implements ClientModInitializer {

    public static final String MOD_NAME = "LarpClient Free";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    private static LarpClient instance;
    public static LarpClient getInstance() { return instance; }
    public static MinecraftClient mc;

    private ModuleManager moduleManager;
    private EventBus eventBus;
    private ClickGUI clickGUI;

    private KeyBinding guiKey;

    @Override
    public void onInitializeClient() {
        instance = this;
        mc = MinecraftClient.getInstance();

        eventBus = new EventBus();
        moduleManager = new ModuleManager(eventBus);
        clickGUI = new ClickGUI(moduleManager);

        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.larpclient.gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_INSERT,
            "category.larpclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (guiKey.wasPressed()) {
                clickGUI.toggle();
            }
            moduleManager.onTick();
        });

        LOGGER.info("[LarpClient] bench is live, {} modules loaded",
            moduleManager.getModules().size());
    }

    public ModuleManager getModuleManager() { return moduleManager; }
    public EventBus getEventBus() { return eventBus; }
    public ClickGUI getClickGUI() { return clickGUI; }
}
