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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

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
        Show_webView.addJavascriptInterface(new Load_Date(), "Android");
        Show_webView.addJavascriptInterface(new MyJavascriptInterface(), "GetAlarmClock");
        Show_webView.setWebChromeClient(new WebChromeClient());
        // Kiểm tra kết nối Internet của thiết bị
        ConnectivityManager Connect =(ConnectivityManager) MainActivity.this.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Connect.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            Show_webView.loadUrl("file:///android_asset/templates/Home.html");
        }else{
            Show_webView.loadUrl("file:///android_asset/templates/Disconnect.html");
        }
    }

    public class Load_Date {
        @JavascriptInterface
        public void getDataFromJavascript(String data) {
//            Toast.makeText(MainActivity.this, "Thời gian nhận về là: " + data, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, GoogleCalendar.class);
            intent.putExtra("SendActyvity", data);
            startActivity(intent);
        }
    }

    private class MyJavascriptInterface {
        @JavascriptInterface
        public void sendAlarmList(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String alarmTime = jsonArray.getString(i);
//                    Toast.makeText(MainActivity.this, "Giá trị mới nhận được là: " + alarmTime, Toast.LENGTH_SHORT).show();
                    // Ví dụ: Nhận được giá trị của alarmTime là: 08:09 PM
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
//                    Toast.makeText(MainActivity.this, "Bây giờ là : " + hour + " h và: " + minute + " phút", Toast.LENGTH_SHORT).show();


                    String[] timeParts = alarmTime.split(":");
                    // Lấy giờ và phút
                    String hourString = timeParts[0];
                    String[] temp = timeParts[1].split(" ");
                    String minuteString = temp[0];
                    String AMP = temp[1];
//                    Toast.makeText(MainActivity.this, "Kết quả của hourString: " + hourString, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, "Kết quả của minuteString: " + minuteString, Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Kết quả của AMP:_" + AMP, Toast.LENGTH_SHORT).show();
                    // Chuyển đổi giờ và phút sang số nguyên
                    int Get_hour = Integer.parseInt(hourString);
                    int Get_minute = Integer.parseInt(minuteString);
//                    Toast.makeText(MainActivity.this, "Thực tế tính được : " + Get_hour + " h và: " + Get_minute + " phút", Toast.LENGTH_SHORT).show();
                    if(AMP.equals("PM")){
                        Get_hour = Get_hour + 12;
                        Toast.makeText(MainActivity.this, "Đã đổi thời gian về đùng" + Get_hour, Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MainActivity.this, "Kết quả thời gian đúng chính xác là : " + Get_hour + " h và: " + Get_minute + " phút", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}