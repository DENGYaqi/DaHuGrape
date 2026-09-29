package com.example.dahugrape.a_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dahugrape.MainActivity;
import com.example.dahugrape.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * 登录选项
 * <p>
 * FirebaseAuth.AuthStateListener(){}是用作验证监听，监听用户是否登录。
 * 与GoogleSignInOptions.Builder()方法结合，获取通过邮箱验证的用户信息，能把用户资料显示在profil页面。
 * 点击了登录按钮之后，调用Auth.GoogleSignInApi.getSignInIntent(googleApiClient)这个方法，
 * 并且通过startActivityForResult()运行intention，通过onActivityResult()获得Google要求的结果，
 * 这结果是通过handleSignInResult(result)验证的。如果返回true，那么我们用idToken获得了Google的验证，
 * 然后我们的页面就会导向下一个Activity(DashboardActivity.java)
 */
public class A2_ChooseLogin extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A2_ChooseLogin";

    // 全局变量声明
    Button btn_login_with_email, btn_login_with_google;
    TextView btn_register, btn_forgot_password;

    // Firebase的变量
    FirebaseAuth user_auth;
    FirebaseUser user;

    // 初始化UI
    public void initUI() {
        btn_login_with_email = findViewById(R.id.a2_btn_login_email);
        btn_login_with_google = findViewById(R.id.a2_btn_login_with_wechat);
        btn_register = findViewById(R.id.a2_btn_register);
        btn_forgot_password = findViewById(R.id.a2_btn_forgot_password);

        // 可点击元素的初始化
        btn_login_with_email.setOnClickListener(this);
        btn_login_with_google.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgot_password.setOnClickListener(this);

        // Firebase的初始化
        user_auth = FirebaseAuth.getInstance();
        user = user_auth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a2_activity_choose_login);
        // 调用UI初始值
        initUI();
    }

    /**
     * 使用onStart生命周期检查用户是否已登录
     */
    @Override
    public void onStart() {
        super.onStart();
        // 检查用户是否已登录（非空），并相应地更新用户界面。
        if (user != null) {
            updateUI(user);
        }
    }

    /**
     * 对不同元素的点击管理
     *
     * @param v 被点击的视图
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.a2_btn_login_email) {
            destination(A5_SignInWithMailAndPassword.class);
        } else if (v.getId() == R.id.a2_btn_login_with_wechat) {
            //TODO 添加intent去微信登录的activity
        } else if (v.getId() == R.id.a2_btn_register) {
            destination(A3_RegisterWithMail.class);
        } else if (v.getId() == R.id.a2_btn_forgot_password) {
            destination(A6_ForgotPassword.class);
        }
    }

    /**
     * 管理intent的方法
     *
     * @param dest 转去的class
     */
    private void destination(Class dest) {
        Intent intent = new Intent(A2_ChooseLogin.this, dest);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    /**
     * updateUI可以进入MainActivity, 这个能让用户的选项从不透明变成透明
     *
     * @param user Firebase用户
     */
    private void updateUI(FirebaseUser user) {
        // 检查用户是否已经登录
        if (user != null) {
            // 如果用户的电子邮件被验证了，那么就可以进入登录。
            if (user.isEmailVerified()) {
                destination(MainActivity.class);
            }
        }
    }
}
