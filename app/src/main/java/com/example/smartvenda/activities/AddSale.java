package com.example.smartvenda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartvenda.R;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.helpers.UserDAO;
import com.example.smartvenda.model.Sale;
import com.example.smartvenda.model.User;
import com.example.smartvenda.utils.MaskEditUtil;
import com.example.smartvenda.utils.MoneyTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class AddSale extends AppCompatActivity {

    private EditText buyer;
    private EditText cpf;
    private EditText descSale;
    private EditText valueSale;
    private EditText valuePaid;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        buyer = findViewById(R.id.buyer_input);
        cpf = findViewById(R.id.cpf_buyer_input);
        descSale = findViewById(R.id.description_input);
        valueSale = findViewById(R.id.value_sale_input);
        valuePaid = findViewById(R.id.value_paid_input);
        btnRegister = findViewById(R.id.register_sale_button);

        // masks
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));
        valueSale.addTextChangedListener(new MoneyTextWatcher(valueSale));
        valuePaid.addTextChangedListener(new MoneyTextWatcher(valuePaid));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String buyerText = buyer.getText().toString();
                String cpfText = cpf.getText().toString();
                String descText = descSale.getText().toString();
                String valueSaleText = valueSale.getText().toString();
                String valuePaidText = valuePaid.getText().toString();

                if (!buyerText.isEmpty() && !cpfText.isEmpty() && !descText.isEmpty() && !valueSaleText.isEmpty() && !valuePaidText.isEmpty()) {
                    Log.i("Info", "buyer: " + buyer.getText().toString());

                    SaleDAO saleDAO = new SaleDAO(getApplicationContext());

                    Sale sale = new Sale();
                    sale.setBuyer(buyerText);
                    sale.setCpf(cpfText);
                    sale.setDescription(descText);
                    sale.setValue(valueSaleText);
                    sale.setValuePaid(valuePaidText);

                    int valueSale = Integer.parseInt(MaskEditUtil.unmask(sale.getValue()));
                    valueSale = (int) (valueSale*0.01);
                    int valuePaid = Integer.parseInt(MaskEditUtil.unmask(sale.getValuePaid()));
                    valuePaid = (int) (valuePaid*0.01);

                    int thing = (valuePaid - valueSale);

                    sale.setThing(String.valueOf(thing));

                    Log.i("INFO VENDA", "value: " + valueSale + " | paid: " + valuePaid + " | thing: " + thing);

                    if (valuePaid < valueSale) {
                        Toast.makeText(getApplicationContext(), "insira um valor válido!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (saleDAO.save(sale)) {
                            Toast.makeText(getApplicationContext(), "Sucesso ao salvar venda!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), Sales.class);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar venda!", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos, por favor.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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