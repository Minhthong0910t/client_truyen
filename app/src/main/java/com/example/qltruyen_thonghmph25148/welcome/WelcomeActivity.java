package com.example.qltruyen_thonghmph25148.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.qltruyen_thonghmph25148.MainActivity;
import com.example.qltruyen_thonghmph25148.R;
import com.google.android.material.textfield.TextInputEditText;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}