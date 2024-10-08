package com.github.devricks.bugzapperj.entity.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGeneratorIncrementByOne implements IDGenerator {

    private final AtomicInteger id;

    public IDGeneratorIncrementByOne() {
        this.id = new AtomicInteger(0);
    }

    public IDGeneratorIncrementByOne(AtomicInteger id) {
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
