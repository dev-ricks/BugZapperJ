package com.github.devricks.bugzapperj.presenter;

import com.github.devricks.bugzapperj.data.OutputData;
import com.github.devricks.bugzapperj.data.ViewModel;

public class FakePresenter implements Presenter {

    @Override
    public ViewModel present(OutputData data) {
        return new ViewModel(data);
    }
}
