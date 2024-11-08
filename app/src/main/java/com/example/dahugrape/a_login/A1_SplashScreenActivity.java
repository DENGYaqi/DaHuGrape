package com.example.dahugrape.a_login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dahugrape.R;

/**
 * 入场动画
 */
public class A1_SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "A1_SplashScreenActivity";

    private final static int SPLASH_TIME_OUT = 2000;
    ImageView iv_logo;

    public void initUI() {
        iv_logo = findViewById(R.id.a1_iv_grape);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_activity_splash_sreen);
        initUI();

        // 要让入场动画出现的整个屏幕上
        // 1. 在themes.xml文件中添加一个NoActionBar主题并且调用它
        // 2. 把入口移到入场动画的Activity(Manifest)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(A1_SplashScreenActivity.this, A2_ChooseLogin.class);
                startActivity(intent);
                // 增加这两项活动之间的过渡。
                // 在res中创建一个动画文件夹 -> res/anim。
                // 添加两个文件fadein.xml和fadeout.xml。
                // - 在处理程序中调用过渡文件
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}