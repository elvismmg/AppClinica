package com.example.appclinica.ui.campus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CampusViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CampusViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is campus fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}