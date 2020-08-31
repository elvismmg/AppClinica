package com.example.appclinica.ui.centers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CentersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CentersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is campus fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}