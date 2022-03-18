package com.example.smartvenda.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartvenda.MainActivity;
import com.example.smartvenda.R;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.model.Sale;

public class SaleDetail extends AppCompatActivity {

    private TextView id;
    private TextView buyer;
    private TextView cpf;
    private TextView description;
    private TextView valueSale;
    private TextView valuePaid;
    private TextView thing;
    private Button update;
    private Button delete;
    private Sale sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        id = findViewById(R.id.id_detail);
        buyer = findViewById(R.id.buyer_detail);
        cpf = findViewById(R.id.cpf_detail);
        description = findViewById(R.id.description_detail);
        valueSale = findViewById(R.id.value_sale_detail);
        valuePaid = findViewById(R.id.value_paid_detail);
        thing = findViewById(R.id.thing_detail);


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        sale = (Sale) getIntent().getSerializableExtra("sale");

        id.setText(sale.getId());
        buyer.setText(sale.getBuyer());
        cpf.setText(sale.getCpf());
        description.setText(sale.getDescription());
        valueSale.setText(sale.getValue());
        valuePaid.setText(sale.getValuePaid());
        thing.setText(sale.getThing());

        update = findViewById(R.id.update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SaleDetail.this, AddSale.class);
                intent.putExtra("sale", sale);

                startActivity(intent);

                finish();
            }
        });

        delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Sale sale = new Sale();

                sale.setId(id.getText().toString());

                Log.i("INFO", "id da venda:" + sale.getId());

                AlertDialog.Builder dialog = new AlertDialog.Builder(SaleDetail.this);

                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir essa venda?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SaleDAO saleDAO = new SaleDAO(getApplicationContext());

                        if (saleDAO.delete(sale)) {

                            Toast.makeText(getApplicationContext(), "Sucesso ao deletar venda!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), Sales.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao deletar venda!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("Não", null);

                dialog.create();
                dialog.show();
            }
        });
    }
}