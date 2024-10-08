package com.github.devricks.bugzapperj.entity.util;

public interface IDGeneratorFactory {
    IDGenerator createIDGenerator();

    IDGenerator createIDGenerator(int start);
}
