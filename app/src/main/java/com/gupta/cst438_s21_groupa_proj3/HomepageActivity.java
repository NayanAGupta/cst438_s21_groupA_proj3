package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

public class HomepageActivity extends AppCompatActivity {
    Button logoutButton;
    TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        logoutButton = findViewById(R.id.logoutButton);
        welcomeTextView = findViewById(R.id.welcomeTextView);

        String welcomeMessage = "Welcome, "+ ParseUser.getCurrentUser().getUsername() + "!";
        welcomeTextView.setText(welcomeMessage);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Toast.makeText(getApplicationContext(),"You have been logged out.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}