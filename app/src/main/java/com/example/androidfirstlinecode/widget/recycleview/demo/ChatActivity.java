package com.example.androidfirstlinecode.widget.recycleview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidfirstlinecode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天界面
 * @author Administrator
 */
public class ChatActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化消息数据
        initMsgs();

        // 初始化布局
        initView();
    }

    private void initMsgs() {
        Msg msg = new Msg("hello", Msg.TYPE_RECEIVED);
        Msg msg1 = new Msg("hi", Msg.TYPE_SENT);
        Msg msg2 = new Msg("How are you", Msg.TYPE_RECEIVED);
        Msg msg3 = new Msg("I'm fine, Thank you, and you", Msg.TYPE_SENT);
        Msg msg4 = new Msg("me too", Msg.TYPE_RECEIVED);

        msgList.add(msg);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
        msgList.add(msg4);
    }

    private void initView() {

        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgAdapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(msgAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    // 当有消息时， 刷新RecyclerView中的显示
                    msgAdapter.notifyItemInserted(msgList.size() - 1);
                    // 将RecyclerView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    // 清空输入框
                    inputText.setText("");
                }

            }
        });

    }

}
