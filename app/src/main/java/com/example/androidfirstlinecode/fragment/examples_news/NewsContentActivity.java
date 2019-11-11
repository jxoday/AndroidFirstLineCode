package com.example.androidfirstlinecode.fragment.examples_news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.androidfirstlinecode.R;

/**
 * @author JinXin
 */
public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        // 获取传入的新闻标题
        String newsTitle = getIntent().getStringExtra("news_title");
        // 获取传入的新闻内容
        String newsContent = getIntent().getStringExtra("news_content");

        // 通过调用Fragment的findFragmentById（）方法得到了NewsContentFragment的实例，接着调用它的refresh（）方法，将新闻标题和内容传入，显示数据
        NewContentFragment newContentFragment = (NewContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        // 刷新NewsContentFragment界面
        NewContentFragment.refresh(newsTitle, newsContent);
    }
}
