package com.example.capstone_project_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GoogleCalendar extends AppCompatActivity {
    EditText Title;
    EditText Location;
    EditText Description;
    Button Add_Event;
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
//        Toast.makeText(GoogleCalendar.this, "Thời gian khi mới nhận về là: " + data, Toast.LENGTH_SHORT).show();

        Title = findViewById(R.id.etTile);
        Location = findViewById(R.id.etLocation);
        Description = findViewById(R.id.etDescription);
        Add_Event = findViewById(R.id.btnAdd);
        Add_Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Title.getText().toString().isEmpty() && !Location.getText().toString().isEmpty() && !Description.getText().toString().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, Title.getText().toString()); // Tựa đề
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, Location.getText().toString()); // Địa điểm xảy ra sự kiện
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, Description.getText().toString()); // Mô tả sự kiện
                    // Đặt thời gian bắt đầu và kết thúc cho sự kiện
//                    Calendar startCalendar = Calendar.getInstance();
//                    startCalendar.set(2023, Calendar.AUGUST, 25, 0, 0, 0);
//                    long startMillis = startCalendar.getTimeInMillis();
//                    Calendar endCalendar = Calendar.getInstance();
//                    endCalendar.set(2023, Calendar.AUGUST, 26, 0, 0, 0);
//                    long endMillis = endCalendar.getTimeInMillis();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = null; // chuyển đổi chuỗi ngày thành đối tượng Date
                    try {
                        date = sdf.parse(data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar startCalendar = Calendar.getInstance();
                    startCalendar.setTime(date); // thiết lập thời gian bắt đầu sự kiện
                    startCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    startCalendar.set(Calendar.MINUTE, 0);
                    startCalendar.set(Calendar.SECOND, 0);
                    long startMillis = startCalendar.getTimeInMillis(); // lấy giá trị thời gian bắt đầu sự kiện dưới dạng millis

                    Calendar endCalendar = Calendar.getInstance();
                    endCalendar.setTime(date); // thiết lập thời gian kết thúc sự kiện
                    endCalendar.set(Calendar.HOUR_OF_DAY, 23);
                    endCalendar.set(Calendar.MINUTE, 59);
                    endCalendar.set(Calendar.SECOND, 59);
                    long endMillis = endCalendar.getTimeInMillis(); // lấy giá trị thời gian kết thúc sự kiện dưới dạng millis


                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis);
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);// Lấy cả ngày hôm đấy cho sự kiện, nếu là True thì lấy cả ngày, False thì cần setup thời gian nữa
                    intent.putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com"); // Thêm người tham gia sự kiện thông qua việc Add Email
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(GoogleCalendar.this, "Lỗi!! Hãy kiểm tra lại thiết bị để đảm bảo có ứng dụng lịch", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(GoogleCalendar.this, "Hãy điền đầy đủ thông tin cần thiết", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}