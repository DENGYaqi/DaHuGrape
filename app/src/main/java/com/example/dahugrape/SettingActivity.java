package com.example.dahugrape;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dahugrape.a_login.A2_ChooseLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Toolbar上的设置页面
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingActivity";
    Button btn_change_password, btn_logout, btn_update_password;
    EditText input_new_password;

    Toolbar toolbar;

    // Firebase的变量
    FirebaseAuth mAuth;
    FirebaseUser user;

    void initUI() {
        btn_change_password = findViewById(R.id.btn_change_password);
        btn_update_password = findViewById(R.id.btn_update_password);
        btn_logout = findViewById(R.id.btn_logout);
        input_new_password = findViewById(R.id.input_new_password);
        toolbar = findViewById(R.id.toolbar_setting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // 监听按钮
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_update_password.setOnClickListener(this);

        // 初始化Firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_change_password) { // 修改密码
            Log.i(TAG, "onClick: change pass");
            // 显示不可见元素
            input_new_password.setVisibility(View.VISIBLE);
            btn_update_password.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.btn_update_password) { // 更新修改后的密码,
            Log.i(TAG, "onClick: update pass");
            ChangePasswordRequest();
        } else if (v.getId() == R.id.btn_logout) {
            FirebaseAuth.getInstance().signOut(); //用户登出
            Intent intent = new Intent(getApplicationContext(), A2_ChooseLogin.class);
            startActivity(intent);
            Log.i(TAG, "onClick: logout");
        }
    }

    /**
     * 返回上一个页面, 不管是Activity或者是Fragment都可以
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * 更改密码的methode
     **/
    private void ChangePasswordRequest() {
//        if (input_new_password.equals("")) {
//            Toast.makeText(getActivity(), "Enter Password!! ", Toast.LENGTH_LONG).show();
//        } else {
//            FirebaseUser user = mAuth.getCurrentUser();
//            user.updatePassword(input_new_password)
//                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(getActivity(), "Client Password Updated.", Toast.LENGTH_LONG).show();
//                            }
//                            else {
//                                Toast.makeText(getActivity().getApplicationContext() "For Securtiy resons you have to re-login first.\nThen try to update password.", Toast.LENGTH_LONG).show();
//
//                            }
//                        }
//                    });
    }
}