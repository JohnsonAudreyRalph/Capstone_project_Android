package com.example.capstone_project_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    WebView Show_webView;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Làm ẩn đi thanh trạng thái của điện thoại
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Trỏ tới đối tượng phía bên trên giao diện
        Show_webView = findViewById(R.id.webView);
        // Cho phép javascript được chạy
        Show_webView.getSettings().setJavaScriptEnabled(true);
        Show_webView.addJavascriptInterface(this, "runJavaScript");
        Show_webView.loadUrl("file:///android_asset/index.html");
    }
}