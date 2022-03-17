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
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.helpers.UserDAO;
import com.example.smartvenda.model.Sale;
import com.example.smartvenda.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText userEditText;
    private TextInputEditText password;
    private Button btnLogin;
    private Button btnRegister;
    private List<User> usersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEditText = findViewById(R.id.email_input);

        password = findViewById(R.id.password_input);

        btnLogin = findViewById(R.id.login_button);

        btnRegister = findViewById(R.id.register_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userText = userEditText.getText().toString();

                String passwordText = password.getText().toString();

                if (!userText.isEmpty() && !passwordText.isEmpty()) {

                    UserDAO userDAO = new UserDAO(getApplicationContext());
                    usersList = userDAO.list();

                    User user = new User();

                    boolean userExists = false;

                    for (int i = 0; i < usersList.size(); i++) {

                        if (usersList.get(i).getName().equals(userText)) {
                            userExists = true;
                            user.setPassword(usersList.get(i).getPassword());
                        }
                    }

                    if (userExists) {
                        if (user.getPassword().equals(passwordText)) {
                            Intent intent = new Intent(getApplicationContext(), Sales.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Senha incorreta!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "O usuário inserido não existe.", Toast.LENGTH_SHORT).show();
                    }

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