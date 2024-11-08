package com.example.dahugrape.a_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dahugrape.MainActivity;
import com.example.dahugrape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static java.lang.String.valueOf;

/**
 * 通过邮件登录
 */
public class A5_SignInWithMailAndPassword extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A5_SignInWithMailAndPassword";

    // 全局变量声明
    TextInputLayout a5_til_email;
    EditText a5_et_mail, a5_et_password;
    Button a5_btn_login_with_email;
    TextView a5_btn_sign_in_with_email, a5_btn_forgot_password;
    // 声明intent为了用户在不同方法内的使用
    Intent intent;
    Boolean back = false;
    // Firebase变量声明
    private FirebaseAuth user_auth;
    private FirebaseUser user;

    /**
     * 初始化UI
     **/
    public void initUI() {
        a5_til_email = findViewById(R.id.a5_til_email);
        a5_et_mail = findViewById(R.id.a5_et_email);
        a5_et_password = findViewById(R.id.a5_et_password);
        a5_btn_login_with_email = findViewById(R.id.a5_bt_login);
        a5_btn_sign_in_with_email = findViewById(R.id.a5_btn_sign_in_with_email);
        a5_btn_forgot_password = findViewById(R.id.a5_btn_forgot_password);

        // 可点击元素的初始化
        a5_et_mail.setOnClickListener(this);
        a5_et_password.setOnClickListener(this);
        a5_btn_login_with_email.setOnClickListener(this);
        a5_btn_sign_in_with_email.setOnClickListener(this);
        a5_btn_forgot_password.setOnClickListener(this);

        // Firebase元素的初始化
        user_auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a5_activity_sign_in_with_mail);

        // 初始化界面
        initUI();

        // 验证输入框内容
        a5_til_email.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除内容
                a5_et_mail.getText().clear();
            }
        });

    }

    /**
     * 使用obStart生命周期检查用户是否已登录
     **/
    @Override
    public void onStart() {
        super.onStart();
        // 检查用户是否已登录（非空），并相应地更新用户界面。
        user = user_auth.getCurrentUser();
        updateUI(user);
    }

    /**
     * 对不同元素的点击管理
     **/
    @Override
    public void onClick(View v) {
        String email = a5_et_mail.getText().toString();
        String password = a5_et_password.getText().toString();
        if (v.getId() == R.id.a5_bt_login) {
            loginUser(email, password);
        } else if (v.getId() == R.id.a5_btn_sign_in_with_email) {
            //intent.putExtra("email", email);
            //intent.putExtra("password", password);
            destination(A3_RegisterWithMail.class);
        } else if (v.getId() == R.id.a5_btn_forgot_password) {
            destination(A6_ForgotPassword.class);
        }
    }

    /**
     * 管理intent的方法
     *
     * @param dest 转去的class
     */
    private void destination(Class dest) {
        intent = new Intent(A5_SignInWithMailAndPassword.this, dest);
        startActivity(intent);
        // 添加过渡动画
        if (!back) {
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        finish();
    }

    /**
     * 用户登录管理方法
     *
     * @param email    用户邮件
     * @param password 用户密码
     */
    private void loginUser(String email, String password) {
        if (email.equals("")) { // 如果邮件是空的 就显示消息框 提示用户邮件为空
            Toast.makeText(A5_SignInWithMailAndPassword.this, R.string.enter_email,
                    Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) { // 如果密码是空的 就显示消息框 提示用户密码为空
            Toast.makeText(A5_SignInWithMailAndPassword.this, R.string.enter_password,
                    Toast.LENGTH_SHORT).show();
        } else { // 邮件和密码都输入了 就用这个邮件和密码进行登录
            user_auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { // 如果没有问题，我们调用updateUI方法
                                user = user_auth.getCurrentUser();
                                updateUI(user);
                            } else {  // 如果用户不存在 就在消息框显示问题所在
                                Toast.makeText(A5_SignInWithMailAndPassword.this, valueOf(R.string.authentication_failed)
                                        + task.getException(), Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    /**
     * updateUI可以进入MainActivity, 这个能让用户的选项从不透明变成透明
     *
     * @param user Firebase的用户
     */
    private void updateUI(FirebaseUser user) { //TODO 这里有错误 貌似形参没用到
        user = user_auth.getCurrentUser();
        // 检查用户是否已经登录。
        if (user != null) {
            // 如果用户的邮箱已被验证，那么就可以访问登录。
            if (user.isEmailVerified()) {
                String login_success = getResources().getString(R.string.login_success);
                Toast.makeText(A5_SignInWithMailAndPassword.this, login_success,
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(A5_SignInWithMailAndPassword.this, MainActivity.class));
            } else { // 当用户没有被验证
                String mail_not_verified = getResources().getString(R.string.mail_not_verified);
                Toast.makeText(A5_SignInWithMailAndPassword.this, mail_not_verified,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 增加了onBackPressed方法，以避免在点击返回键时退出应用。
     **/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back = true;
        destination(A2_ChooseLogin.class);
    }
}