package com.example.smartvenda.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smartvenda.MainActivity;
import com.example.smartvenda.R;
import com.example.smartvenda.adapters.SaleAdapter;
import com.example.smartvenda.adapters.UserAdapter;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.helpers.UserDAO;
import com.example.smartvenda.model.User;
import com.example.smartvenda.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Users extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private User selectedUser;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        if (sharedpreferences == null || sharedpreferences.equals("")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        recyclerView = findViewById(R.id.recyclerViewUsers);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        User userSelected = userList.get(position);

                        Intent intent = new Intent(Users.this, RegisterUser.class);
                        intent.putExtra("user", userSelected);

                        startActivity(intent);

                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        selectedUser = userList.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Users.this);

                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir esse usuário?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                UserDAO userDAO = new UserDAO(getApplicationContext());

                                if (userDAO.delete(selectedUser)) {

                                    loadUserList();

                                    Toast.makeText(getApplicationContext(), "Sucesso ao deletar usuário!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao deletar usuário!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        dialog.setNegativeButton("Não", null);

                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { }
                })
        );

        loadUserList();
    }

    public void loadUserList () {

        UserDAO userDAO = new UserDAO(getApplicationContext());
        userList = userDAO.list();


        userAdapter = new UserAdapter(userList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(userAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                Intent intent = new Intent(getApplicationContext(), Sales.class);
                startActivity(intent);  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}