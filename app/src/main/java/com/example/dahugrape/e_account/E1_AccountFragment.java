package com.example.dahugrape.e_account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.dahugrape.R;
import com.example.dahugrape.databinding.E1FragmentAccountBinding;
import com.example.dahugrape.ml1_cv.classification_image.ClassificationImageActivity;
import com.example.dahugrape.ml2_nlu.chatbot.ChatBotActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * 用户页面
 */
public class E1_AccountFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "E1_AccountFragment";

    E1FragmentAccountBinding e1FragmentAccountBinding;

    // Firebase的变量
    FirebaseAuth user_auth;
    FirebaseUser user;

    private NavController navController;

    public E1_AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        e1FragmentAccountBinding = E1FragmentAccountBinding.inflate(inflater, container, false);

        // 添加监听器
        e1FragmentAccountBinding.e1UserImg.setOnClickListener(this);
        e1FragmentAccountBinding.e1BtnOrderManage.setOnClickListener(this);
        e1FragmentAccountBinding.e1BtnMyChatBot.setOnClickListener(this);
        e1FragmentAccountBinding.e1BtnMyClassificationImage.setOnClickListener(this);

        // 初始化Firebase
        user_auth = FirebaseAuth.getInstance();
        user = user_auth.getCurrentUser();

        // 登录后显示用户信息
        if (user != null) {
            showUserProfil();
        }else {
            Log.d(TAG, "onCreateView: " + "not show");
        }

        return e1FragmentAccountBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    /**
     * 显示用户账户信息的methode
     **/
    private void showUserProfil() {
        // 姓名、电子邮件地址和个人照片的网址
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri avatar = user.getPhotoUrl();
        // 与部件的关联
        e1FragmentAccountBinding.e1UserEmail.setText(email);
        e1FragmentAccountBinding.e1UserName.setText(name);
        // 设置用户照片
        Glide.with(getActivity())
                .load(avatar)
                .into(e1FragmentAccountBinding.e1UserImg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.e1_user_img) { // 裁剪, 修改用户头像
            // TODO 增加点击修改头像的功能
            // 显示选择照相还是从相册选择的提示框
            // 如果是相机就打开相机
            // 如果是相册就打开相册
            // 保存
            Log.i(TAG, "onClick: img");
        } else if (v.getId() == R.id.e1_btn_order_manage) {
            navController.navigate(R.id.action_navigation_account_to_e3_OrderFragment);
            Log.i(TAG, "onClick: order manage");
        } else if (v.getId() == R.id.e1_btn_my_ChatBot) { // 人工智能客服
            Intent intent = new Intent(getActivity(), ChatBotActivity.class);
            startActivity(intent);
            Log.i(TAG, "onClick: my chatbot");
        } else if (v.getId() == R.id.e1_btn_my_Classification_Image) { // 图像识别 和 实时识别
            Intent intent = new Intent(getActivity(), ClassificationImageActivity.class);
            startActivity(intent);
            Log.i(TAG, "onClick: Classification Image");
        }
    }

}