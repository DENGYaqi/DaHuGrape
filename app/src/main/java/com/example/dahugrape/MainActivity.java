package com.example.dahugrape;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.dahugrape.database.model.Cart;
import com.example.dahugrape.database.model.Grape;
import com.example.dahugrape.database.viewmodels.GrapeViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 命名规则 :
 * 静态变量 : 全大写
 * ClassName : 驼峰
 * 包名 : 小写+下划线
 * 其他都是编号+下划线+小写
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // 底部导航初始化
    BottomNavigationView bottomNavigationView;

    // Fragment NavController
    NavController navController;

    // 工具栏初始化
    Toolbar toolbar;
    Context context;

    // 搜索框
    Dialog dialog;
    // 搜索框内的葡萄名字
    ArrayList<String> arrayList;

    // 购物车和购物车小红点需要的数据
    GrapeViewModel grapeViewModel;
    private int cartQuantity = 0;

    // 初始化UI
    public void initUI() {
        toolbar = findViewById(R.id.toolbar_main_activity);
        context = getApplicationContext();

        // 初始化底部导航视图。
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        // Fragment NavController
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        // 显示工具栏
        setSupportActionBar(toolbar);

        // 请求用户相机读取权限
        // TODO 还有别的权限也一起问问
        if (!hasPermission()) {
            requestPermission();
        }

        // 初始化GrapeViewModel
        grapeViewModel = new ViewModelProvider(this).get(GrapeViewModel.class);

        // 获取所有葡萄数据 获取所有名字 加到搜索框的arrayList内
        grapeViewModel.getGrapes(this).observe(this, new Observer<List<Grape>>() {
            @Override
            public void onChanged(List<Grape> grapes) {
                // 搜索框 : 初始化array list
                arrayList = new ArrayList<>();
                // Add value in array list
                for (Grape grape : grapes) {
                    arrayList.add(grape.getName());
                }
            }
        });

        // 根据购物车数量创建小红点的数量
        grapeViewModel.getCart().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                // Log.d(TAG, "onChanged: " + carts.size());
                int quantity = 0; // 每个产品的数量
                for (Cart cart : carts) {
                    quantity += cart.getQuantity();
                }
                cartQuantity = quantity; // 购物车内物品的数量

                // 购物车小红点
                // TODO 用不了if else和while 也就是当数量为0的时候就消除小红点 多于0的时候就显示小红点和数量
                BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.navigation_cart);
                badge.setVisible(true);
                badge.setBackgroundColor(getResources().getColor(R.color.red_pink));
                badge.setNumber(cartQuantity);

                // 运行时改变Menu ItemOnCreateOptionsMenu()只有在menu刚被创建时才会执行，因此要想随时动态改变OptionMenu就要实现onPrepareOptionsMenu()方法，该方法会传给你Menu对象，供使用
                // Android2.3或更低的版本会在每次Menu打开的时候调用一次onPrepareOptionsMenu().
                // Android3.0及以上版本默认menu是打开的，所以必须调用invalidateOptionsMenu()方法，
                // 然后系统将调用onPrepareOptionsMenu()执行update操作。
                invalidateOptionsMenu(); // 更新menu的操作
            }
        });

    }

    // 关于Toolbar的设置
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置Toolbar
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    // 设置Toolbar各种按钮的反应
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // 搜索按钮
            case R.id.item_search:
                // 初始化对话框
                dialog = new Dialog(MainActivity.this);
                // 自制对话框
                dialog.setContentView(R.layout.b5_dialog_searchable_spinner);
                // 自制对话框高度和宽度
                dialog.getWindow().setLayout(850, 1000);
                // 设置透明背景
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // 显示对话框
                dialog.show();

                // 初始化对话框的UI变量
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                // 初始化Array list的adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this
                        , android.R.layout.simple_list_item_1, arrayList);

                // 设置adapter
                listView.setAdapter(adapter);

                // 监听输入框 也就是输入同时填充
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // 填充array list
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 通过position获取葡萄名字
                        String grape_name = adapter.getItem(position);
                        // 通过名字找到葡萄
                        Grape grape = grapeViewModel.getGrapeByName(grape_name);
                        // 设置该葡萄到view model
                        grapeViewModel.setGrape(grape);
                        // 跳去葡萄详细页面
                        // TODO 这里有错误 如果是从别的页面 例如折扣页面进行搜索 就会报错 因为并不是从home去详细页面的
                        // TODO 具体怎么改看看 : https://developer.android.com/jetpack/compose/navigation
                        navController.navigate(R.id.action_navigation_home_to_c1_DetailsGrapeFragment);

                        // 消散/关闭 对话框
                        dialog.dismiss();
                    }
                });
                return true;
            case R.id.item_share:
                // 分享框
                ApplicationInfo api = getApplicationContext().getApplicationInfo();
                Intent intent = new Intent(Intent.ACTION_SEND);
                String shareBody = "Here is the share content body";
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Try subject");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "ShareVia"));
                return true;
            case R.id.item_setting:
                Intent intentSetting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intentSetting);
                return true;
            default:
                return NavigationUI.onNavDestinationSelected(item, navController) ||
                        super.onOptionsItemSelected(item);
        }
    }

    // toolbar的返回键 但是实际上没有这个也可以 教程用的是因为 他把cartFragment 放在toolbar上了
    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    // TODO 下面两个函数转去入场动画页面 但是要点击同意后再开始软件
    // 查看权限是否存在
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    // 请求权限
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
}