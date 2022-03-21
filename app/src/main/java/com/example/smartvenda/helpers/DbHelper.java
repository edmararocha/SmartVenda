package com.example.smartvenda.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_APP";
    public static String TABELA_USUARIOS = "usuarios";
    public static String TABELA_VENDAS = "vendas";

    public DbHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Criando tabela users

        String sqlUsers = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIOS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, usuario VARCHAR(25) NOT NULL, email TEXT NOT NULL, senha VARCHAR(20) NOT NULL); ";

        try {
            db.execSQL(sqlUsers);
            Log.i("INFO DB", "[+] Sucesso ao criar a tabela USUARIOS");
        } catch (Exception e) {
            Log.i("INFO DB", "[-] Erro ao criar o banco de dados - " + e.getMessage());
        }

        String sqlAdmin = "INSERT INTO " + TABELA_USUARIOS + "(usuario, email, senha) VALUES ('admin', 'admin@gmail.com', 'admin'); ";
        try {
            db.execSQL(sqlAdmin);
            Log.i("INFO DB", "[+] Sucesso ao criar o usuario ADMIN");
        } catch (Exception e) {
            Log.i("INFO DB", "[-] Erro ao criar o usuario ADMIN - " + e.getMessage());
        }

        String sqlSales = "CREATE TABLE IF NOT EXISTS " + TABELA_VENDAS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, comprador VARCHAR NOT NULL, cpf VARCHAR NOT NULL, descricao TEXT, valorDaCompra VARCHAR NOT NULL, valorPago VARCHAR NOT NULL, troco VARCHAR NOT NULL); ";

        try {
            db.execSQL(sqlSales);
            Log.i("INFO DB", "[+] Sucesso ao criar a tabela VENDAS");
        } catch (Exception e) {
            Log.i("INFO DB", "[-] Erro ao criar o banco de dados - " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
