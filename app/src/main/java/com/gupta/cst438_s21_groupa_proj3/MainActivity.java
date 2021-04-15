package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}