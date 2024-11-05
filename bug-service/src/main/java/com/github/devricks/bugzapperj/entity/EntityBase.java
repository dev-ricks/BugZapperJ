package com.github.devricks.bugzapperj.entity;

import com.github.devricks.bugzapperj.entity.util.DefaultIDGeneratorFactory;
import com.github.devricks.bugzapperj.entity.util.IDGenerator;
import com.github.devricks.bugzapperj.entity.util.IDGeneratorFactory;
import com.github.devricks.bugzapperj.entity.validation.Validator;

public abstract class EntityBase implements Entity, Validator {

    private static final IDGeneratorFactory IDGeneratorFactory = new DefaultIDGeneratorFactory();
    public static IDGenerator IDGenerator = IDGeneratorFactory.createIDGenerator();
    private Integer id = 0;
    private Boolean persisted = false;

    public static void resetIDGenerator() {
        IDGenerator.reset();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isPersisted() {
        return persisted;
    }

    public void setPersisted(Boolean persisted) {
        this.persisted = persisted;
    }
}
