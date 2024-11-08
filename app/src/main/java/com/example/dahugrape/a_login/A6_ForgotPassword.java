package com.example.dahugrape.a_login;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dahugrape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * 忘记密码
 */
public class A6_ForgotPassword extends AppCompatActivity {

    private static final String TAG = "A6_ForgotPassword";

    TextInputLayout a6_til_email;
    String email_address;
    TextView a6_tv_hint_mail;
    EditText a6_et_email;
    Button a6_btn_reset_passe_word;
    Context context;

    FirebaseAuth user_auth;
    FirebaseUser user;


    void initUI() {
        user_auth = FirebaseAuth.getInstance();
        a6_tv_hint_mail = findViewById(R.id.a6_tv_hint_mail);
        a6_til_email = findViewById(R.id.a6_til_email);
        a6_et_email = findViewById(R.id.a6_et_email);
        a6_btn_reset_passe_word = findViewById(R.id.a6_btn_reset_passe_word);
        context = getApplicationContext();
        user = user_auth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a6_activity_forgot_password);
        initUI();

        a6_til_email.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除内容
                a6_et_email.getText().clear();
            }
        });

        a6_btn_reset_passe_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 发送重置密码邮件
                email_address = a6_et_email.getText().toString();// 获取用户输入的邮件
                if (email_address.equals("")) { // 如果输入是空就要求用户再次输入
                    String require_input = context.getString(R.string.require_mail);
                    Toast.makeText(context, require_input, Toast.LENGTH_SHORT).show();
                } else { // TODO 不是空就要验证邮件是否存在, 不存在就要从新发送
                    user_auth.sendPasswordResetEmail(email_address)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) { // 验证成功就发送邮件
                                        String mail_is_send = context.getString(R.string.reset_password_mail_is_send);
                                        Toast.makeText(context, mail_is_send, Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Email sent.");
                                    } else { // TODO 写一个函数 验证邮件是否存在 : 否则就发送 邮件不存在
                                        String mail_not_exist = context.getString(R.string.reset_password_mail_is_send);
                                        Toast.makeText(context, mail_not_exist, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}