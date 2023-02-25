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
import android.webkit.JavascriptInterface;
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
//            Show_webView.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    Log.d("sode","Show_webView onPageFinished url="+url);
//                    //gọi 1 đoạn code js chạy trong trang
//                    //code js này ko có sẵn trong trang
//                    //document.getElementById('NhietDo').innerText
//                    String js1="javascript:window.runJavaScript.nhan_Temp(document.getElementById('NhietDo').innerText);";
//                    Show_webView.loadUrl(js1);
//                    Log.d("sode","Show_webView onPageFinished url="+js1);
//                    //
//                }
//            });
        }else{
            Show_webView.loadUrl("file:///android_asset/Disconnect.html");
        }
    }
//    String giai_db="Loading...";
//    @JavascriptInterface
//    public void nhan_Temp(String giai_db_js){
//        //nhận đc số đề thì https...
//        if(giai_db_js!=null && giai_db_js !="")
//            giai_db=giai_db_js;
//        Log.d("sode","nhan dc giai_db_js = "+giai_db_js);
//        Log.d("sode","nhan dc giai_db = "+giai_db);
//        //truyền xuống trang display: sode.html local
//
//        //chưa truyền đc, có gì đó sai sai
//        //webView_display.loadUrl("javascript:hienthisode('1234')");
//    }
}