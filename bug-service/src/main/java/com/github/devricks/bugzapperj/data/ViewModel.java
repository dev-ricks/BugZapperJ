package com.github.devricks.bugzapperj.data;

public class ViewModel {

    public final Integer id;
    public final String status;
    public final String message;

    public ViewModel(OutputData data) {
        this.id = data.id;
        this.status = data.status;
        this.message = data.message;
    }
}
