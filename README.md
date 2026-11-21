# LarpClient Free — Fabric 1.21.1

Free open-source reimplementation of LarpClient's module system.

## Build

```bash
./gradlew build
```

Output: `build/libs/larpclient-free-1.0.0.jar`

## Install

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.21.1
2. Drop the jar into `.minecraft/mods/`
3. Launch Minecraft with Fabric profile

## Controls

| Key    | Action          |
|--------|----------------|
| INSERT | Open ClickGUI  |
| R      | Toggle Aura    |
| F      | Toggle Fly     |

## Modules implemented

### Combat
Aura, AntiKnockback, Reach, Criticals, AutoTotem, AutoClicker, TriggerBot, WTap, AimAssist, AutoCrystal

### Movement
Fly, Speed, NoFall, AutoSprint, NoSlowdown, Jesus, NoJumpDelay, ElytraFly

### Visuals
ESP, Tracers, Fullbright, Freecam, Zoom, Chams, XRay, Nametags, NoWeather, HUD, BlockOverlay, CustomCrosshair

### Player
AutoEat, AutoRespawn, AutoTool, ChestStealer, InvCleaner

### Misc
FakeFPS, FakePlayer

## Notes

- `Tracers.worldToScreen()` requires matrix injection from MixinGameRenderer — marked with `// FILL:`
- `NoJumpDelay` requires a `@Shadow` or `@Accessor` for the private `jumpingCooldown` field
- `Chams` and `ESP` box rendering require GL tessellator calls in the WorldRenderer mixin tail
- XRay triggers a chunk reload on toggle — expected behavior
