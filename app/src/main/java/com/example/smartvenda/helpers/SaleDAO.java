package com.example.smartvenda.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smartvenda.model.Sale;

import java.util.ArrayList;
import java.util.List;

public class SaleDAO implements ISaleDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public SaleDAO (Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(Sale sale) {
        ContentValues cv = new ContentValues();
        cv.put("comprador", sale.getBuyer());
        cv.put("cpf", sale.getCpf());
        cv.put("descricao", sale.getDescription());
        cv.put("valorDaCompra", sale.getValue());
        cv.put("valorPago", sale.getValuePaid());
        cv.put("troco", sale.getThing());

        try {

            write.insert(DbHelper.TABELA_VENDAS, null, cv);
            Log.i("INFO", "[+] Venda salva com sucesso!");

        } catch (Exception e) {
            Log.e("INFO", "[-] Erro ao salvar venda - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Sale sale) {

        ContentValues cv = new ContentValues();
        cv.put("comprador", sale.getBuyer());
        cv.put("cpf", sale.getCpf());
        cv.put("descricao", sale.getDescription());
        cv.put("valorDaCompra", sale.getValue());
        cv.put("valorPago", sale.getValuePaid());
        cv.put("troco", sale.getThing());

        try {

            String[] args = {sale.getId()};
            write.update(DbHelper.TABELA_VENDAS, cv,"id=?", args);
            Log.i("INFO", "[+] Venda atualizada com sucesso!");

        } catch (Exception e) {

            Log.e("INFO", "[-] Erro ao atualizar venda - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Sale sale) {

        try {

            String[] args = {sale.getId()};
            write.delete(DbHelper.TABELA_VENDAS, "id=?", args);
            Log.i("INFO", "[+] Venda removida com sucesso!");

        } catch (Exception e) {

            Log.e("INFO", "[-] Erro ao remover a venda - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Sale> list() {

        List<Sale> sales = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_VENDAS + " ;";

        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            Sale sale = new Sale();

            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String buyer = cursor.getString(cursor.getColumnIndex("comprador"));
            @SuppressLint("Range") String cpf = cursor.getString(cursor.getColumnIndex("cpf"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("descricao"));
            @SuppressLint("Range") String valueSale = cursor.getString(cursor.getColumnIndex("valorDaCompra"));
            @SuppressLint("Range") String valuePaid = cursor.getString(cursor.getColumnIndex("valorPago"));
            @SuppressLint("Range") String thing = cursor.getString(cursor.getColumnIndex("troco"));


            sale.setId(id);
            sale.setBuyer(buyer);
            sale.setCpf(cpf);
            sale.setDescription(description);
            sale.setValue(valueSale);
            sale.setValuePaid(valuePaid);
            sale.setThing(thing);

            sales.add(sale);
        }

        return sales;
    }
}