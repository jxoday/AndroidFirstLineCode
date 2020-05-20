package com.example.androidfirstlinecode.widget.date_picker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.androidfirstlinecode.R;

import java.util.Calendar;

/**
 * 时间控件练习
 * @author JinXin
 */
public class DatePickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        initView();
    }

    private void initView() {

        DatePicker datePicker = findViewById(R.id.date_picker);
        TimePicker timePicker = findViewById(R.id.timer_picker);

        Calendar calendar = Calendar.getInstance();
        // 年
        int year = calendar.get(Calendar.YEAR);
        // 月份从0开始计算
        int month = calendar.get(Calendar.MONDAY) + 1;
        // 日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 时
        int hour = calendar.get(Calendar.HOUR);
        // 分
        int minute = calendar.get(Calendar.MINUTE);
        setTitle(year + "-" + month + "-" + day + "-" + hour + ":" + minute);

        // 日期选择器初始化
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setTitle(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        });

        // 时间选择器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay + ":" + minute);
            }
        });

        // 日期选择器对话框
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setTitle(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, year, calendar.get(Calendar.MONTH), day).show();

        // 时间选择器对话框
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTitle(hourOfDay + ":" + minute);
            }
        }, hour, minute, true).show();
    }
}
