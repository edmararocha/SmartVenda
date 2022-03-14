package com.example.smartvenda.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartvenda.R;
import com.example.smartvenda.helpers.UserDAO;
import com.example.smartvenda.model.User;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterUser extends AppCompatActivity {

    private TextInputEditText user;
    private TextInputEditText password;
    private TextInputEditText email;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        user = findViewById(R.id.user_input);

        password = findViewById(R.id.password_input);

        email = findViewById(R.id.email_input);

        btnRegister = findViewById(R.id.register_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userText = user.getText().toString();
                String passwordText = password.getText().toString();
                String emailText = email.getText().toString();

                if (!userText.isEmpty() && !passwordText.isEmpty() && !emailText.isEmpty()) {
                    Log.i("Info", "user: " + user.getText().toString());

                    UserDAO userDAO = new UserDAO(getApplicationContext());

                    User user = new User();
                    user.setName(userText);
                    user.setPassword(passwordText);
                    user.setEmail(emailText);

                    if (userDAO.save(user)) {
                        Toast.makeText(getApplicationContext(), "Sucesso ao salvar usuário!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao salvar usuário!", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(), Sales.class);
                    startActivity(intent);

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos, por favor.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}