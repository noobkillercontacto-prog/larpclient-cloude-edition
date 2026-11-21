package com.larpclient.event;

@FunctionalInterface
public interface Listener<T> {
    void onEvent(T event);
}
