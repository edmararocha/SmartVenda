package com.example.smartvenda.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.smartvenda.R;

public class SaleDetail extends AppCompatActivity {

    private TextView id;
    private TextView buyer;
    private TextView cpf;
    private TextView description;
    private TextView valueSale;
    private TextView valuPaid;
    private TextView thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        id = findViewById(R.id.id_detail);
        buyer = findViewById(R.id.buyer_detail);
        cpf = findViewById(R.id.cpf_detail);
        description = findViewById(R.id.description_detail);
        valueSale = findViewById(R.id.value_sale_detail);
        valuPaid = findViewById(R.id.value_paid_detail);
        thing = findViewById(R.id.thing_detail);

        Intent intent = getIntent();

        id.setText(intent.getStringExtra("id"));
        buyer.setText(intent.getStringExtra("buyer"));
        cpf.setText(intent.getStringExtra("cpf"));
        description.setText(intent.getStringExtra("description"));
        valueSale.setText(intent.getStringExtra("valueSale"));
        valuPaid.setText(intent.getStringExtra("valuePaid"));
        thing.setText(intent.getStringExtra("thing"));

        Log.i("ID da Compra", " " + id);
    }
}