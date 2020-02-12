package com.example.androidfirstlinecode.filestore;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件存储————将数据存储到文件中
 *
 * Context类中提供了一个openFileOutput()方法， 可以用于将数据存储到指定的文件中，所有的文件默认存储到/data/data/<packagename>/files/目录下
 * openFileOutput()方法接收两个参数：
 *      第一个参数的文件名，文件名不可包含路径；
 *      第二个是文件的操作模式，主要有两种模式可选，MODE_PRIVATE和MODE_APPEND。MODE_PRIVATE表示默认操作模式所写的内容覆盖原文件内容；MODE_APPEND表示如果该文件已存在，就追加内容，不存在创建新文件
 *
 * @author JinXin
 */
public class FileStoreActivity extends AppCompatActivity {

    private static final String TAG = "FilePersistenceTest";

    private EditText editFilePersistence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_store);

        editFilePersistence = findViewById(R.id.edit_file);

        String inputString  = load();
        if (!TextUtils.isEmpty(inputString)) {
            editFilePersistence.setText(inputString);
            Toast.makeText(this, "获取保存的数据", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从文件中读取数据
     * @return
     */
    private String load() {
        // 使用FileInputStream 输入流读取文件
        FileInputStream fileInputStream = null;
        // 字符缓冲输入流
        BufferedReader reader = null;

        StringBuilder stringBuilder = new StringBuilder();
        try {
            fileInputStream = openFileInput("data");
            // InputStreamReader 标准输入流转换成字符输入流,是字符流通向字节流的桥梁
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            reader = new BufferedReader(inputStreamReader);

            String content = "";
            while ((content = reader.readLine()) != null) {
                stringBuilder.append(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText  = editFilePersistence.getText().toString();
        save(inputText);
    }

    /**
     * 保存输入的数据到文件中
     * @param inputText
     */
    private void save(String inputText) {
        // 使用FileOutputStream 输出流写入文件
        FileOutputStream fileOutputStream = null;
        // 字符缓冲输出流
        BufferedWriter writer = null;

        try {
            fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
            // OutputStreamWriter 标准输出流转换成字符输出流,是字符流通向字节流的桥梁
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            writer = new BufferedWriter(outputStreamWriter);
            // 将文本内容写入文件中
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
