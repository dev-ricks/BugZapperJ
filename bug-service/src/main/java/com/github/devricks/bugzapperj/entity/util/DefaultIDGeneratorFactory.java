package com.github.devricks.bugzapperj.entity.util;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultIDGeneratorFactory implements IDGeneratorFactory {

    @Override
    public IDGenerator createIDGenerator() {
        return new IDGeneratorIncrementByOne();
    }

    @Override
    public IDGenerator createIDGenerator(int start) {
        return new IDGeneratorIncrementByOne(new AtomicInteger(start));
    }
}