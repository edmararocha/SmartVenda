package com.example.smartvenda.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.smartvenda.MainActivity;
import com.example.smartvenda.adapters.SaleAdapter;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.utils.RecyclerItemClickListener;
import com.example.smartvenda.model.Sale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartvenda.R;

import java.util.ArrayList;
import java.util.List;

public class Sales extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SaleAdapter saleAdapter;
    private List<Sale> salesList = new ArrayList<>();
    private Sale selectedSale;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_KEY = "user_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sales);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        if (sharedpreferences == null || sharedpreferences.equals("")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        recyclerView = findViewById(R.id.recyclerView);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Sale saleSelected = salesList.get(position);

                        Intent intent = new Intent(Sales.this, SaleDetail.class);
                        intent.putExtra("sale", saleSelected);

                        startActivity(intent);

                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        selectedSale = salesList.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Sales.this);

                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja excluir essa venda?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SaleDAO saleDAO = new SaleDAO(getApplicationContext());

                                if (saleDAO.delete(selectedSale)) {

                                    loadSalesList();

                                    Toast.makeText(getApplicationContext(), "Sucesso ao deletar venda!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Erro ao deletar venda!", Toast.LENGTH_SHORT).show();
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

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddSale.class);
                startActivity(intent);


            }
        });

        loadSalesList();
    }

    public void loadSalesList () {

        SaleDAO saleDAO = new SaleDAO(getApplicationContext());
        salesList = saleDAO.list();


        saleAdapter = new SaleAdapter(salesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(saleAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        String userStr = sharedpreferences.getString(USER_KEY, null);
        String passwordStr = sharedpreferences.getString(PASSWORD_KEY, null);


        if (userStr.equals("admin") && passwordStr.equals("admin")) {
            Log.i("INFO", "user is admin!");
            menu.getItem(0).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemLog) {
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.clear();

            editor.apply();

            Intent i=new Intent(Sales.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

        if (id == R.id.itemUser) {

            Intent i=new Intent(Sales.this, Users.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}