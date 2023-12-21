package com.Stellar.Updates.AndroidApp.stellarupdates.services;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.Stellar.Updates.AndroidApp.stellarupdates.viewmodels.MainViewModel;

public class MainViewModelProvider extends ViewModelProvider {

    private final Application application;

    public MainViewModelProvider(@NonNull ViewModelStoreOwner owner, Application application) {
        super(owner);
        this.application = application;
    }

    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
