package com.example.reorder.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reorder.R;
import com.example.reorder.UserInfoUpdate.UserLogin;

public class LoginActivity extends AppCompatActivity {

    EditText et_login_id;//[client_id받는 버튼]
    EditText et_login_password;
    Button bt_login_ok;
    TextView registerButton;
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.parseColor("#2f2f30"));
        }
        registerButton=(TextView)findViewById(R.id.tv_login_register);
        et_login_id=(EditText)findViewById(R.id.et_login_id);
        et_login_password=(EditText)findViewById(R.id.et_login_password);
        bt_login_ok=(Button)findViewById(R.id.bt_login_ok);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (LoginActivity.this,RegisterActivity.class));

            }
        });

        bt_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_login_id.getText().toString().equals("")||et_login_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    Log.d("11111", "입력해");
                }
                else{
                    UserLogin userLogin = new UserLogin();
                    userLogin.Login(et_login_id.getText().toString(),et_login_password.getText().toString(),new Intent(LoginActivity.this, NavigationnActivity.class),LoginActivity.this);
                    Log.d("11111", "여기는왔니");
                }

            }
        });
    }
}