package com.example.appclinica.ui.session;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SessionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SessionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is session fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}