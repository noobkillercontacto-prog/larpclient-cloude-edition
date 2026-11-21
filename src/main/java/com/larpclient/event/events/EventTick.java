package com.larpclient.event.events;
public class EventTick {
    private final boolean pre;
    public EventTick(boolean pre) { this.pre = pre; }
    public boolean isPre() { return pre; }
}
