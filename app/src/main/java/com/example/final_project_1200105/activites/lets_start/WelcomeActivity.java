package com.example.final_project_1200105.activites.lets_start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project_1200105.R;
import com.example.final_project_1200105.activites.login_reg.LoginActivity;
import com.example.final_project_1200105.activites.login_reg.RegistrationActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnGoRegister;
    private TextView btnGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnGoRegister = findViewById(R.id.RegFromWelcomeButton);
        btnGoLogin = findViewById(R.id.sign_in_text);

        btnGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

//        btnGoLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });



        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}