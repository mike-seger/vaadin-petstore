package com.net128.application.vaadin.petstore.repo;

import com.net128.application.vaadin.petstore.model.Identifiable;
import com.vaadin.flow.shared.Registration;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Slf4j
public class EntityChangeBroadCaster {
    final private static Executor executor = Executors.newSingleThreadExecutor();
    final private static LinkedList<Consumer<Identifiable>> listeners = new LinkedList<>();

    public static synchronized Registration register(
            Consumer<Identifiable> listener) {
        listeners.add(listener);
        return () -> {
            synchronized (EntityChangeBroadCaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public static synchronized void broadcastEntityChanged(Identifiable changedEntity) {
        for (Consumer<Identifiable> listener : listeners) {
            executor.execute(() -> listener.accept(changedEntity));
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public void entityChanged(Object entity) {
        log.info("{}", entity.toString());
        broadcastEntityChanged((Identifiable)entity);
    }
}