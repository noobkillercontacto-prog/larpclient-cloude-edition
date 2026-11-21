package com.larpclient.event;

import java.util.*;

public class EventBus {

    private final Map<Class<?>, List<Listener<?>>> listeners = new HashMap<>();

    public <T> void subscribe(Class<T> type, Listener<T> listener) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }

    public <T> void unsubscribe(Class<T> type, Listener<T> listener) {
        List<Listener<?>> list = listeners.get(type);
        if (list != null) list.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void post(T event) {
        List<Listener<?>> list = listeners.get(event.getClass());
        if (list == null) return;
        for (Listener<?> l : new ArrayList<>(list)) {
            ((Listener<T>) l).onEvent(event);
        }
    }
}
