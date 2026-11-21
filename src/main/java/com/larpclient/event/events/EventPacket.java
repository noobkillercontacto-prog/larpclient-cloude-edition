package com.larpclient.event.events;
import net.minecraft.network.packet.Packet;
public class EventPacket {
    public enum Direction { SEND, RECEIVE }
    private final Packet<?> packet;
    private final Direction direction;
    private boolean cancelled;
    public EventPacket(Packet<?> packet, Direction direction) {
        this.packet = packet;
        this.direction = direction;
    }
    public Packet<?> getPacket() { return packet; }
    public Direction getDirection() { return direction; }
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
}
