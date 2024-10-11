package com.github.devricks.bugzapperj.service;

import com.github.devricks.bugzapperj.data.InputData;
import com.github.devricks.bugzapperj.data.OutputData;

public interface BugHandlerService {

    OutputData createBug(InputData data);

    OutputData updateBug(InputData data);
}
