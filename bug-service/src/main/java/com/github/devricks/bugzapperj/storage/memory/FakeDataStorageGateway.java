package com.github.devricks.bugzapperj.storage.memory;

import com.github.devricks.bugzapperj.entity.Bug;
import com.github.devricks.bugzapperj.storage.DataStorageGateway;

import java.util.HashMap;
import java.util.Map;

public class FakeDataStorageGateway implements DataStorageGateway<Bug> {
    final Map<Integer, Bug> storage = new HashMap<>();

    public boolean save(Bug bug) {
        storage.put(bug.getId(), bug);
        return !(storage.get(bug.getId()) == null);
    }

    public Bug find(Integer id) {
        return storage.get(id);
    }
}
