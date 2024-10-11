package com.github.devricks.bugzapperj.presenter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.entity.Bug;

public interface Presenter {

    OutputData present(Bug bug);
}
