package com.example.dahugrape.a_login;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dahugrape.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class A3_RegisterWithMail extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A3_RegisterWithMail";

    // 全局变量声明
    TextInputLayout a3_til_name, a3_til_email;
    EditText a3_user_name, a3_user_email, a3_user_password;
    ImageView a3_user_avatar_profile;
    MaterialButton a3_btn_register;
    TextView a3_btn_register_with_mail;
    Uri a3_image_uri;

    // Firebase的变量
    private FirebaseAuth user_auth;
    private FirebaseUser user;

    /**
     * 初始化UI
     */
    public void initUI() {
        a3_til_name = findViewById(R.id.l02_til_name);
        a3_til_email = findViewById(R.id.l02_til_email);
        a3_user_name = findViewById(R.id.l02_et_name);
        a3_user_email = findViewById(R.id.l02_et_email);
        a3_user_password = findViewById(R.id.l02_et_password);
        a3_user_avatar_profile = findViewById(R.id.user_avatar_profil);

        a3_btn_register = findViewById(R.id.l02_bt_register);

        a3_btn_register_with_mail = findViewById(R.id.btn_login_on_register_with_mail);

        // 可点击元素的初始化
        a3_user_avatar_profile.setOnClickListener(this);
        a3_btn_register.setOnClickListener(this);
        a3_btn_register_with_mail.setOnClickListener(this);

        // Firebase实例的初始化
        user_auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3_activity_register_with_mail);

        // 调用initUI()方法。
        initUI();

        // 验证输入框内容
        a3_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty() && !s.toString().matches("[a-zA-Z]+")) {
                    String nameErrorHint = getResources().getString(R.string.nameErrorHint);
                    // 当内容不是空并且包含数字，显示错误
                    a3_til_name.setError(nameErrorHint);
                } else {
                    // 当内容只有文字，隐藏错误
                    a3_til_name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        a3_til_email.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除内容
                a3_user_email.getText().clear();
            }
        });
    }

    /**
     * 添加了与View.OnClickListener的实现相链接的onClick方法
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.l02_bt_register) { // 注册按钮
            RegisterUser(a3_user_email.getText().toString(), a3_user_password.getText().toString());
        } else if (v.getId() == R.id.btn_login_on_register_with_mail) { // 注册页面的登录按钮
            Intent intent = new Intent(A3_RegisterWithMail.this, A5_SignInWithMailAndPassword.class);
            // 恢复邮件和密码数据，以避免重写它们。
            startActivity(intent);
        } else if (v.getId() == R.id.user_avatar_profil) { // 添加头像图片的按钮。
            SelectAvatarImg();
        }
    }

    /**
     * 管理从相机或图库中添加头像的工作，需要在清单中添加权限
     */
    private void SelectAvatarImg() {
        // alertDialog文本
        String add_photo = getResources().getString(R.string.add_photo);
        String take_photo = getResources().getString(R.string.take_photo);
        String from_gallery = getResources().getString(R.string.from_gallery);
        String cancel = getResources().getString(R.string.cancel);
        // 把选项放在提示框里
        final CharSequence[] options = {take_photo, from_gallery, cancel};
        // 创建提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(A3_RegisterWithMail.this);
        // 加入标题
        builder.setTitle(add_photo);
        // 使用该函数构建带有点击处理的提示框
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // 用相机拍照
                if (options[item].equals(take_photo)) {
                    // 验证终端版本是否高于M(Marshmallow)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        // 检查是否给予了权限，如果没有，将显示它们
                        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                            requestPermissions(permission, 1000);
                        } else {
                            openCamera();
                        }
                    } else {
                        openCamera();
                    }
                } else if (options[item].equals(from_gallery)) { // 显示手机的照片库
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals(cancel)) {
                    dialog.dismiss(); // 取消并退出提示框
                }
            }
        });
        builder.show();
    }

    /**
     * 管理相机开关
     */
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        a3_image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // 管理照片intent传输
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, a3_image_uri);
        startActivityForResult(takePictureIntent, 1);
    }

    /**
     * 管理手机权限弹出的显示方式
     *
     * @param requestCode  根据code执行不同的动作
     * @param permissions  权限们
     * @param grantResults 授予的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                // 如果用户授予了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 打开相机
                    openCamera();
                } else {
                    // 用户拒绝授予权限
                    Toast.makeText(A3_RegisterWithMail.this,
                            getResources().getText(R.string.permission_denied),
                            Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * 添加头像时根据来源进行反馈管理
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        时间
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 只有当用户添加照片时，结果代码才是RESULT_OK。
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 1:
                    // 当用户从相机中添加图片
                    a3_user_avatar_profile.setImageURI(a3_image_uri);
                    break;
                case 2:
                    // 当用户从图库中添加照片
                    // data.getData返回所选图片的URI并显示出来。
                    a3_image_uri = data.getData();
                    a3_user_avatar_profile.setImageURI(a3_image_uri);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 管理新用户的添加
     *
     * @param email    用户邮件
     * @param password 用户密码
     */
    private void RegisterUser(String email, String password) {
        if (email.equals("")) {
            Toast.makeText(A3_RegisterWithMail.this, getResources().getText(R.string.enter_email),
                    Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(A3_RegisterWithMail.this, getResources().getText(R.string.enter_password),
                    Toast.LENGTH_SHORT).show();
        } else if ((a3_user_name.getText().toString()).equals("")) {
            Toast.makeText(A3_RegisterWithMail.this, getResources().getText(R.string.enter_name),
                    Toast.LENGTH_SHORT).show();
        } else if (a3_image_uri == null) {
            Toast.makeText(A3_RegisterWithMail.this, getResources().getText(R.string.select_avatar),
                    Toast.LENGTH_SHORT).show();
        } else {
            user_auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                userProfile();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(A3_RegisterWithMail.this,
                                        getResources().getText(R.string.sign_up_failed) + task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * 在Firebase中保存个人头像
     */
    private void userProfile() {
        user = user_auth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(a3_user_name.getText().toString())
                    .setPhotoUri(a3_image_uri).build();
            user.updateProfile(profileUpdates).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    verifyEmailRequest();
                }
            });
        }
    }

    /**
     * 向用户提供的地址发送验证邮件。
     **/
    private void verifyEmailRequest() {
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(A3_RegisterWithMail.this,
                                    getResources().getText(R.string.sent_email) + "\n" + a3_user_email.getText().toString(),
                                    Toast.LENGTH_LONG).show();
                            user_auth.signOut();
                            startActivity(new Intent(A3_RegisterWithMail.this, A5_SignInWithMailAndPassword.class));
                        } else {
                            Toast.makeText(A3_RegisterWithMail.this,
                                    getResources().getText(R.string.sign_up_okl_but_email_not_sent),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}