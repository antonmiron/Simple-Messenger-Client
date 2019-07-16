package com.example.mymessenger.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mymessenger.R;
import com.example.mymessenger.tools.BindingActivity;
import com.example.mymessenger.viewmodels.MessageViewModel;
import com.example.mymessenger.databinding.ActivityMainBinding;

public class MainActivity extends BindingActivity<ActivityMainBinding, MessageViewModel> {
    private MessageViewModel messageVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_main, MessageViewModel.class);

        ActivityMainBinding binding = getBinding();
        messageVM = getViewModel();

        messageVM.setActivityType(this);
        binding.setMessageVM(messageVM);
    }
    public static Intent getIntent(Context context, boolean closeCurrent){
        Intent intent = new Intent(context, MainActivity.class);
        if(closeCurrent)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
