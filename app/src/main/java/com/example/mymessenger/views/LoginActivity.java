package com.example.mymessenger.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mymessenger.R;
import com.example.mymessenger.databinding.ActivityLoginBinding;
import com.example.mymessenger.tools.BindingActivity;
import com.example.mymessenger.viewmodels.MessageViewModel;

public class LoginActivity extends BindingActivity<ActivityLoginBinding, MessageViewModel> {

    private MessageViewModel messageVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_login,MessageViewModel.class);

        ActivityLoginBinding binding = getBinding();
        messageVM = getViewModel();

        messageVM.setActivityType(this);
        binding.setMessageVM(messageVM);
    }
    public static Intent getIntent(Context context, boolean closeCurrent){
        Intent intent = new Intent(context, LoginActivity.class);
        if(closeCurrent)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
