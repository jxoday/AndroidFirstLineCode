package com.example.androidfirstlinecode.widget.spinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidfirstlinecode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉列表
 * @author JinXin
 */
public class SpinnerActivity extends AppCompatActivity {

    private TextView textView;
    private Spinner spinner;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        textView = findViewById(R.id.text);
        spinner = findViewById(R.id.spinner);

        // 设置数据源
        list = new ArrayList<>();
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");

        // 新建数组适配器ArrayAdapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        // adapter设置一个下拉样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spinner.setAdapter(adapter);
        // spinner监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                textView.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
