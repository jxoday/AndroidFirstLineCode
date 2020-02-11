package com.example.androidfirstlinecode.broadcast.example_local_broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidfirstlinecode.R;

/**
 * @author JinXin
 */
public class LoginActivity extends AppCompatActivity {

    private EditText accountEdit,passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                // 如果账号是admin且密码是123456，就认为登录成功
                if (account.equals("admin") && password.equals("123456")) {
                    Intent intent = new Intent(LoginActivity.this, BroadcastMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"请输入正确的账号和密码",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
