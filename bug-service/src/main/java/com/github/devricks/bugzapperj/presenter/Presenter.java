package com.github.devricks.bugzapperj.presenter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.data.ViewModel;

public interface Presenter {

    ViewModel present(OutputData data);
}
