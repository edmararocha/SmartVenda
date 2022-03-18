package com.example.smartvenda.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartvenda.R;
import com.example.smartvenda.helpers.SaleDAO;
import com.example.smartvenda.model.Sale;
import com.example.smartvenda.utils.MaskEditUtil;
import com.example.smartvenda.utils.MoneyTextWatcher;

import java.io.Serializable;

public class AddSale extends AppCompatActivity {

    private EditText buyer;
    private EditText cpf;
    private EditText descSale;
    private EditText valueSale;
    private EditText valuePaid;
    private Button btnRegister;
    private Sale atualSale;

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

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        atualSale = (Sale) getIntent().getSerializableExtra("sale");

        if (atualSale != null) {
            buyer.setText(atualSale.getBuyer());
            cpf.setText(atualSale.getCpf());
            descSale.setText(atualSale.getDescription());
            valueSale.setText(atualSale.getValue());
            valuePaid.setText(atualSale.getValuePaid());

            mToolbar.setTitle("Edição de venda");
            btnRegister.setText("Editar");

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String buyerText = buyer.getText().toString();
                    String cpfText = cpf.getText().toString();
                    String descText = descSale.getText().toString();
                    String valueSaleText = valueSale.getText().toString();
                    String valuePaidText = valuePaid.getText().toString();

                    if (!buyerText.isEmpty() && !cpfText.isEmpty() && !descText.isEmpty() && !valueSaleText.isEmpty() && !valuePaidText.isEmpty()) {

                        SaleDAO saleDAO = new SaleDAO(getApplicationContext());

                        Sale saleEdit = new Sale();
                        saleEdit.setId(atualSale.getId());
                        saleEdit.setBuyer(buyerText);
                        saleEdit.setCpf(cpfText);
                        saleEdit.setDescription(descText);
                        saleEdit.setValue(valueSaleText);
                        saleEdit.setValuePaid(valuePaidText);

                        float valueSale = Integer.parseInt(MaskEditUtil.unmask(saleEdit.getValue()));
                        valueSale = (float) (valueSale*0.01);
                        float valuePaid = Integer.parseInt(MaskEditUtil.unmask(saleEdit.getValuePaid()));
                        valuePaid = (float) (valuePaid*0.01);

                        float thing = (valuePaid - valueSale);

                        saleEdit.setThing(String.format("%.2f", thing));

                        Log.i("INFO VENDA", "value: " + valueSale + " | paid: " + valuePaid + " | thing: " + thing);

                        if (valuePaid < valueSale) {
                            Toast.makeText(getApplicationContext(), "insira um valor válido!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (saleDAO.update(saleEdit)) {
                                Toast.makeText(getApplicationContext(), "Sucesso ao atualizar venda!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Sales.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Erro ao atualizar venda!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Preencha todos os campos, por favor.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
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

                        float valueSale = Integer.parseInt(MaskEditUtil.unmask(sale.getValue()));
                        valueSale = (float) (valueSale*0.01);
                        float valuePaid = Integer.parseInt(MaskEditUtil.unmask(sale.getValuePaid()));
                        valuePaid = (float) (valuePaid*0.01);

                        float thing = (valuePaid - valueSale);

                        sale.setThing(String.format("%.2f", thing));

                        Log.i("INFO VENDA", "value: " + valueSale + " | paid: " + valuePaid + " | thing: " + thing);

                        if (valuePaid < valueSale) {
                            Toast.makeText(getApplicationContext(), "insira um valor válido!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (saleDAO.save(sale)) {
                                Toast.makeText(getApplicationContext(), "Sucesso ao salvar venda!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), Sales.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    }
}