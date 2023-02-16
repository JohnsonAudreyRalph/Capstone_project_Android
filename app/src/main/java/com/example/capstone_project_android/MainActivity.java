package com.example.capstone_project_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView Show_webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //Ẩn ActionBar đi
        // Làm ẩn đi thanh trạng thái của điện thoại
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Show_Web();
    }
    @SuppressLint("JavascriptInterface")
    public void Show_Web(){
        // Trỏ tới đối tượng phía bên trên giao diện
        Show_webView = findViewById(R.id.webView);
        // Cho phép javascript được chạy
        Show_webView.getSettings().setJavaScriptEnabled(true);
        Show_webView.addJavascriptInterface(this, "runJavaScript");
        // Kiểm tra kết nối Internet của thiết bị
        ConnectivityManager Connect =(ConnectivityManager) MainActivity.this.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Connect.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            Show_webView.loadUrl("file:///android_asset/index.html");
        }else{
            Show_webView.loadUrl("file:///android_asset/Disconnect.html");
        }
    }
}