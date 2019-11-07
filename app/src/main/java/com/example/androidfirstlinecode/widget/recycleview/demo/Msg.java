package com.example.androidfirstlinecode.widget.recycleview.demo;

public class Msg {

    /**
     * 收到的消息
     */
    public static final int TYPE_RECEIVED = 0;

    /**
     * 发出的消息
     */
    public static final int TYPE_SENT = 1;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息类型
     */
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
