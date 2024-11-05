package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.data.InputData;

public interface EntityBuilder {

    Entity build();

    Entity build(InputData data);
}
