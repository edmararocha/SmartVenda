package com.example.smartvenda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartvenda.R;
import com.example.smartvenda.utils.MaskEditUtil;
import com.example.smartvenda.utils.MoneyTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

public class AddSale extends AppCompatActivity {

    private TextInputEditText buyer;
    private TextInputEditText cpf;
    private TextInputEditText descSale;
    private TextInputEditText valueSale;
    private TextInputEditText valuePaid;
    private TextInputEditText thing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        buyer = findViewById(R.id.buyer_input);
        cpf = findViewById(R.id.cpf_buyer_input);
        descSale = findViewById(R.id.description_input);
        valueSale = findViewById(R.id.sale_value_input);
        valuePaid = findViewById(R.id.value_paid_input);
        thing = findViewById(R.id.thing_input);

        // masks
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));
        valueSale.addTextChangedListener(new MoneyTextWatcher(valueSale));
        valuePaid.addTextChangedListener(new MoneyTextWatcher(valuePaid));
        thing.addTextChangedListener(new MoneyTextWatcher(thing));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_sale, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}