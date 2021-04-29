package com.gupta.cst438_s21_groupa_proj3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminDeleteUser extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> username;
    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_user);
        username = new ArrayList<>();
        spinner = (Spinner)findViewById(R.id.spinner);
        deleteButton = findViewById(R.id.deleteButton);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground((users, e) -> {
            if(e == null){
                for(ParseUser user1: users){
                    String user = user1.getString("username");
                    username.add(user);
                }
            }
            spinner.setAdapter(new ArrayAdapter<String>(AdminDeleteUser.this, android.R.layout.simple_spinner_dropdown_item, username));


        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String user = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
