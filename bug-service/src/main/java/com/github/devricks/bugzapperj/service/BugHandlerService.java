package com.github.devricks.bugzapperj.service;

import com.github.devricks.bugzapperj.data.InputData;

public interface BugHandlerService {

    void createBug(InputData data);

    void updateBug(InputData data);

    void findBug(InputData data);

    void inactivateBug(InputData data);
}
