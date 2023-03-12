package com.example.capstone_project_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;
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
        private int[] count = new int[10];
        @JavascriptInterface
        public void sendAlarmList(String json) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String alarmTime = jsonArray.getString(i);
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    String[] timeParts = alarmTime.split(":");
                    // Lấy giờ và phút
                    String hourString = timeParts[0];
                    String[] temp = timeParts[1].split(" ");
                    String minuteString = temp[0];
                    String AMP = temp[1];
                    // Chuyển đổi giờ và phút sang số nguyên
                    int Get_hour = Integer.parseInt(hourString);
                    int Get_minute = Integer.parseInt(minuteString);
                    if(AMP.equals("PM")){
                        Get_hour = Get_hour + 12;
                    }
                    int Get_time_in_hours;
                    int Get_time_in_minutes;
                    if (Get_hour > hour || (Get_hour == hour && Get_minute >= minute)) {
                        // Nếu giờ lấy về lớn hơn giờ thực tế, hoặc giờ lấy về bằng giờ thực tế và phút lấy về lớn hơn hoặc bằng phút thực tế
                        Get_time_in_hours = Get_hour - hour;
                        Get_time_in_minutes = Get_minute - minute;
                    } else {
                        // Ngược lại, tức là giờ lấy về nhỏ hơn giờ thực tế
                        Get_time_in_hours = Get_hour + 24 - hour;
                        Get_time_in_minutes = Get_minute - minute - 60;
                    }
                    // Nếu phút lấy về nhỏ hơn phút thực tế, cộng thêm 60 phút và giảm đi 1 giờ
                    if (Get_minute < minute) {
                        Get_time_in_hours--;
                        Get_time_in_minutes += 60;
                    }
                    int Get_Time = (Get_time_in_hours * 60) + Get_time_in_minutes;
                    Toast.makeText(MainActivity.this, "Thời gian lấy được còn lại là : " + Get_Time + " p", Toast.LENGTH_SHORT).show();
                    if(Get_Time <= 0){
                        Toast.makeText(MainActivity.this, "Cảnh báo !!!!!: " + Get_Time + " đã hết thời gian", Toast.LENGTH_SHORT).show();
                        sendNotification();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendNotification(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound);

        Notification notification = new NotificationCompat.Builder(MainActivity.this, "CHANNEL 1")
                .setContentTitle("Báo thức")
                .setContentText("Đã đến giờ cài đặt của bạn!!!!!!")
                .setSound(uri)
                .setColor(getResources().getColor(R.color.purple_500))
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(bitmap)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationId(), notification);
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }
}