package com.larpclient.event.events;
import net.minecraft.client.render.Camera;
public class EventRender3D {
    private final float tickDelta;
    private final Camera camera;
    public EventRender3D(float tickDelta, Camera camera) {
        this.tickDelta = tickDelta;
        this.camera = camera;
    }
    public float getTickDelta() { return tickDelta; }
    public Camera getCamera() { return camera; }
}
