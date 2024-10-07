package com.github.devricks.bugzapperj.entity.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {

    private final AtomicInteger id;

    public IDGenerator() {
        this.id = new AtomicInteger(0);
    }

    public IDGenerator(AtomicInteger id) {
        this.id = id;
    }

    public int generateID() {
        return id.incrementAndGet();
    }

    public void reset() {
        id.set(0);
    }

    public int getCurrentID() {
        return id.get();
    }
}
