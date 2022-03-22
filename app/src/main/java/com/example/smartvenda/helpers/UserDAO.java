package com.example.smartvenda.helpers;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smartvenda.model.Sale;
import com.example.smartvenda.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public UserDAO (Context context) {
        DbHelper db = new DbHelper(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean save(User user) {

        ContentValues cv = new ContentValues();
        cv.put("usuario", user.getName());
        cv.put("email", user.getEmail());
        cv.put("senha", user.getPassword());

        try {
            write.insert(DbHelper.TABELA_USUARIOS, null, cv);
            Log.i("INFO", "[+] Usuário salvo com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "[-] Erro ao salvar usuário - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(User user) {

        ContentValues cv = new ContentValues();
        cv.put("usuario", user.getName());
        cv.put("email", user.getEmail());
        cv.put("senha", user.getPassword());

        try {

            String[] args = {String.valueOf(user.getId())};
            write.update(DbHelper.TABELA_USUARIOS, cv,"id=?", args);
            Log.i("INFO", "[+] Usuário atualizado com sucesso!");

        } catch (Exception e) {

            Log.e("INFO", "[-] Erro ao atualizar o usuário - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(User user) {


        try {

            String[] args = {String.valueOf(user.getId())};
            write.delete(DbHelper.TABELA_USUARIOS, "id=?", args);
            Log.i("INFO", "[+] Usuário removido com sucesso!");

        } catch (Exception e) {

            Log.e("INFO", "[-] Erro ao remover o usuário - " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<User> list() {


        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_USUARIOS + " ;";

        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            User user = new User();

            @SuppressLint("Range") Long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("usuario"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("senha"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));

            user.setId(id);
            user.setName(name);
            user.setPassword(password);
            user.setEmail(email);

            users.add(user);
        }

        return users;
    }
}
