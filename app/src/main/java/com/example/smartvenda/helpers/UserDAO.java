package com.example.smartvenda.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smartvenda.model.User;

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
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> list() {
        return null;
    }
}
