package com.example.androidfirstlinecode.widget.gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.androidfirstlinecode.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewActivity extends AppCompatActivity {

    private static final String TAG = "GridViewActivity";

    private List<Map<String, Object>> dataList;
    private int[] icon = {R.drawable.music_list_stop,
            R.drawable.music_list_stop,
            R.drawable.music_list_stop,
            R.drawable.music_list_stop,
            R.drawable.music_list_stop};
    private String [] name = {"测试1", "测试2","测试3","测试4", "测试5"};
    private SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        initView();
    }

    private void initView() {
        GridView gridView = findViewById(R.id.grid_view);

        // 1、准备数据源
        // 2、新建适配器（SimpleAdapter）
        // 3、GridView加载适配器
        // 4、GridView配置事件监听器
        dataList = new ArrayList<Map<String, Object>>();
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.item_grid_view, new String[]{"image", "text"}, new int[]{R.id.image, R.id.text});
        gridView.setAdapter(simpleAdapter);
    }

    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", name[i]);
            dataList.add(map);
        }
        return dataList;
    }
}
