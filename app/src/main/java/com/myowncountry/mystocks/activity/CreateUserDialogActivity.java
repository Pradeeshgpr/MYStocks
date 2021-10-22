package com.myowncountry.mystocks.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.myowncountry.mystocks.R;
import com.myowncountry.mystocks.service.UserService;

import java.util.function.Function;

public class CreateUserDialogActivity extends Dialog {

    private Context activity;
    private EditText emailId;
    private Button cancel, create;
    private UserService userService;
    private Function<String, Void> callback;

    public CreateUserDialogActivity(@NonNull Context context) {
        super(context);
        activity = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_user_dialog);

        emailId = findViewById(R.id.create_user_dialog_email);
        cancel = findViewById(R.id.create_user_dialog_cancel);
        create = findViewById(R.id.create_user_dialog_create);

        cancel.setOnClickListener(v -> this.cancel());
        create.setOnClickListener(v -> createUser());

        userService = UserService.getInstance();


        setCanceledOnTouchOutside(true);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT );
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void createUser() {
        String email = emailId.getText().toString();
        userService.createUser(email).addOnSuccessListener(command -> {
            callback.apply(email);
            this.cancel();
        });
    }

    public void setCallbackFunction(Function<String, Void> callback) {
        this.callback = callback;
    }
}
