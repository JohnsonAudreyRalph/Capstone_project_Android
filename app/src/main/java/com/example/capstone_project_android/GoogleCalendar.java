package com.example.capstone_project_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

public class GoogleCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_calendar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //Ẩn ActionBar đi
        // Làm ẩn đi thanh trạng thái của điện thoại
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ShowCalendar();
    }
    public void ShowCalendar(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String data = bundle.getString("SendActyvity");
        Toast.makeText(GoogleCalendar.this, "Thời gian khi mới nhận về là: " + data, Toast.LENGTH_SHORT).show();
    }
}