package com.example.mymessenger.tools;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;

public abstract class BindingViewModel extends ViewModel {

    public void setActivityType(Activity activity){}
    /*Lifecycle*/
    public void onStart(Activity activity) {}
    public void onResume(Activity activity){}
    public void onPause(Activity activity){}
    public void onStop(Activity activity) {}
    public void onDestroy(Activity activity) {}
}
