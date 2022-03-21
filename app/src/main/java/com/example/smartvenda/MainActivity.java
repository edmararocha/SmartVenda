package com.example.smartvenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_KEY = "user_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String userStr, passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEditText = findViewById(R.id.email_input);

        password = findViewById(R.id.password_input);

        btnLogin = findViewById(R.id.login_button);

        btnRegister = findViewById(R.id.register_button);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        userStr = sharedpreferences.getString(USER_KEY, null);
        passwordStr = sharedpreferences.getString(PASSWORD_KEY, null);

        Log.i("INFO SESSION" , "user - " + sharedpreferences.getString(USER_KEY, null));

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

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(USER_KEY, userText);
                            editor.putString(PASSWORD_KEY, passwordText);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), Sales.class);
                            startActivity(intent);
                            finish();
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

    protected void onStart() {
        super.onStart();
        if (userStr != null && passwordStr != null) {
            Intent i = new Intent(MainActivity.this, Sales.class);
            startActivity(i);
        }

    }
}