package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.util.DefaultIDGeneratorFactory;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import com.github.devricks.bugzapperj.entity.util.IDGeneratorFactory;
import com.github.devricks.bugzapperj.entity.validation.Validator;

public abstract class EntityBase implements Entity, Validator {

    private static final IDGeneratorFactory IDGeneratorFactory = new DefaultIDGeneratorFactory();
    public static IDGenerator IDGenerator = IDGeneratorFactory.createIDGenerator();
    private int id;

    public static void resetIDGenerator() {
        IDGenerator.reset();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
