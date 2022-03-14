package com.example.smartvenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartvenda.activities.RegisterUser;
import com.example.smartvenda.activities.Sales;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText user;
    private TextInputEditText password;
    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.email_input);

        password = findViewById(R.id.password_input);

        btnLogin = findViewById(R.id.login_button);

        btnRegister = findViewById(R.id.register_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userText = user.getText().toString();

                String passwordText = password.getText().toString();

                if (!userText.isEmpty() && !passwordText.isEmpty()) {
                    Log.i("Info", "user: " + user.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), Sales.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos, por favor.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intent);
            }
        });

    }
}