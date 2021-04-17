package com.gupta.cst438_s21_groupa_proj3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CreateAccountActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EditText usernameText = findViewById(R.id.createUsernameEditText);
        EditText passwordText = findViewById(R.id.createPasswordEditText);
        Button createAccountButton = findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!usernameText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()){
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameText.getText().toString());
                    user.setPassword((passwordText.getText().toString()));
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                //valid account creation
                                Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_LONG).show();
                            }
                            else{
                                //invalid account creation
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}