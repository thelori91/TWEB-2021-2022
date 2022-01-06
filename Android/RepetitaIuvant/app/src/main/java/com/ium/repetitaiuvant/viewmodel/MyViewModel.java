package com.ium.repetitaiuvant.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private MutableLiveData<String> username;

    public LiveData<String> getUsername() {
        return username;
    }

}
