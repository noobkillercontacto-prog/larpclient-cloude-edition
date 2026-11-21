package com.larpclient.module;

import com.larpclient.event.EventBus;
import com.larpclient.event.events.EventRender2D;
import com.larpclient.event.events.EventRender3D;
import com.larpclient.event.events.EventMotion;
import com.larpclient.module.modules.combat.*;
import com.larpclient.module.modules.movement.*;
import com.larpclient.module.modules.visuals.*;
import com.larpclient.module.modules.player.*;
import com.larpclient.module.modules.misc.*;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();
    private final EventBus bus;

    public ModuleManager(EventBus bus) {
        this.bus = bus;
        registerAll();
    }

    private void registerAll() {
        // Combat
        register(new Aura());
        register(new AntiKnockback());
        register(new Reach());
        register(new Criticals());
        register(new AutoTotem());
        register(new AutoClicker());
        register(new TriggerBot());
        register(new WTap());
        register(new AimAssist());
        register(new AutoCrystal());

        // Movement
        register(new Fly());
        register(new Speed());
        register(new NoFall());
        register(new AutoSprint());
        register(new NoSlowdown());
        register(new Jesus());
        register(new NoJumpDelay());
        register(new ElytraFly());

        // Visuals
        register(new ESP());
        register(new Tracers());
        register(new Fullbright());
        register(new Freecam());
        register(new Zoom());
        register(new Chams());
        register(new XRay());
        register(new Nametags());
        register(new NoWeather());
        register(new HUD());
        register(new BlockOverlay());
        register(new CustomCrosshair());

        // Player
        register(new AutoEat());
        register(new AutoRespawn());
        register(new AutoTool());
        register(new ChestStealer());
        register(new InvCleaner());

        // Misc
        register(new FakeFPS());
        register(new FakePlayer());
    }

    private void register(Module m) {
        m.registerKeybind();
        modules.add(m);
    }

    public void onTick() {
        for (Module m : modules) {
            if (m.wasPressed()) m.toggle();
        }
        for (Module m : modules) {
            if (m.isEnabled()) m.onTick();
        }
    }

    public void onRender2D(EventRender2D e) {
        for (Module m : modules) if (m.isEnabled()) m.onRender2D(e);
    }

    public void onRender3D(EventRender3D e) {
        for (Module m : modules) if (m.isEnabled()) m.onRender3D(e);
    }

    public void onMotion(EventMotion e) {
        for (Module m : modules) if (m.isEnabled()) m.onMotion(e);
    }

    public List<Module> getModules() { return Collections.unmodifiableList(modules); }

    public List<Module> getByCategory(Category cat) {
        return modules.stream().filter(m -> m.getCategory() == cat).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T get(Class<T> cls) {
        return (T) modules.stream().filter(m -> m.getClass() == cls).findFirst().orElse(null);
    }
}
