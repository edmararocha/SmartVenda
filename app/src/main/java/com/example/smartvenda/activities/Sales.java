package com.example.smartvenda.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.smartvenda.MainActivity;
import com.example.smartvenda.adapters.SaleAdapter;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.utils.RecyclerItemClickListener;
import com.example.smartvenda.model.Sale;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Sale saleSelected = salesList.get(position);

                        Intent intent = new Intent(Sales.this, SaleDetail.class);

                        intent.putExtra("id", String.valueOf(saleSelected.getId()));
                        intent.putExtra("buyer", String.valueOf(saleSelected.getBuyer()));
                        intent.putExtra("cpf", String.valueOf(saleSelected.getCpf()));
                        intent.putExtra("description", String.valueOf(saleSelected.getDescription()));
                        intent.putExtra("valueSale", String.valueOf(saleSelected.getValue()));
                        intent.putExtra("valuePaid", String.valueOf(saleSelected.getValuePaid()));
                        intent.putExtra("thing", String.valueOf(saleSelected.getThing()));

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
}