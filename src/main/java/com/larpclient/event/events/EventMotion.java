package com.larpclient.event.events;
public class EventMotion {
    private float yaw, pitch;
    private double x, y, z;
    private boolean onGround;
    public EventMotion(float yaw, float pitch, double x, double y, double z, boolean onGround) {
        this.yaw = yaw; this.pitch = pitch;
        this.x = x; this.y = y; this.z = z;
        this.onGround = onGround;
    }
    public float getYaw() { return yaw; }
    public void setYaw(float yaw) { this.yaw = yaw; }
    public float getPitch() { return pitch; }
    public void setPitch(float pitch) { this.pitch = pitch; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean v) { this.onGround = v; }
}
