package com.example.smartvenda.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.smartvenda.adapters.SaleAdapter;
import com.example.smartvenda.helpers.RecyclerItemClickListener;
import com.example.smartvenda.model.Sale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sales);

        Toolbar toolbar = findViewById(R.id.toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Sale saleSelected = salesList.get(position);

                        Intent intent = new Intent(Sales.this, SaleDetail.class);

                        startActivity(intent);

                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) { }

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

        Sale sale2 = new Sale();
        sale2.setValue(30);
        sale2.setBuyer("Joãozinho");

        Sale sale3 = new Sale();
        sale3.setValue(40);
        sale3.setBuyer("Zezinho");

        salesList.add(sale2);
        salesList.add(sale3);

        saleAdapter = new SaleAdapter(salesList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(saleAdapter);
    }
}