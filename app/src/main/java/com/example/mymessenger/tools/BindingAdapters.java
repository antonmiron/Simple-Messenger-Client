package com.example.mymessenger.tools;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymessenger.R;
import com.example.mymessenger.models.Message;

public class BindingAdapters {
    /**Конвертируем int в String**/
    @BindingAdapter({"android:text"})
    public static void convertInt(EditText editText, int number){
        if(number!=0)
            editText.setText(String.valueOf(number));
    }

    /**Конвертируем String в int**/
    @InverseBindingAdapter(attribute = "android:text")
    public static int convertString(EditText editText){
        String number = editText.getText().toString();
        try {
            return Integer.parseInt(number);
        }catch (NumberFormatException ex){return 0;}
    }

    /**Достаем текст из сообщения**/
    @BindingAdapter({"android:text"})
    public static void convertMessage(TextView textView, Message message){
        Context context = textView.getContext();
        switch (message.getType()){
            case USER_ADDED:
                textView.setText(context.getString(R.string.message_user_added,message.getData()));
                break;
            case TEXT:
                textView.setText(message.getData());
                break;
            case USER_REMOVED:
                textView.setText(context.getString(R.string.message_user_removed,message.getData()));
                break;
        }
    }
}
