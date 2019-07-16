package com.example.mymessenger.tools;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

public class VMFactory {
    /**Используем Factory для создания ViewModel**/
    public static <T extends BindingViewModel> T obtainViewModel(FragmentActivity activity, Class<T> viewmodelClass)
    {
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());
        T viewModel = ViewModelProviders.of(activity,factory).get(viewmodelClass);
        return viewModel;
    }
}
