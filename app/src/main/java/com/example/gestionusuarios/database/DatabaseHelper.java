package com.example.gestionusuarios.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gestionusuarios.dao.UsuarioDao;
import com.example.gestionusuarios.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE users " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT," +
                "lastName TEXT," +
                "email TEXT," +
                "password TEXT," +
                "fechaCreado TEXT," +
                "estado INTEGER" +
                ")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    public UsuarioDao getUserDao(boolean write){
        if (!write){
            return new UsuarioDao(getReadableDatabase());
        }
        return new UsuarioDao(getWritableDatabase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        onCreate(sqLiteDatabase);
    }

    public boolean deleteUserById(int id){
        return getUserDao(true).delete(id);
    }
    public boolean updateUser(Usuario user){
        return getUserDao(true).update(user);
    }
    public boolean insertUser(Usuario user){
        return getUserDao(true).create(user);
    }
    public Usuario login(String email, String password){
        return getUserDao(false).login(email, password);
    }
    public List<Usuario> getUsers(){
        return getUserDao(false).getAll();
    }

    public Usuario getUserById(int id){
        return getUserDao(false).getById(id);
    }

    private Usuario getUserByCursor(Cursor cursor){
        Usuario item = new Usuario();
        item.id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        item.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        item.email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        item.lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        item.createDate = cursor.getString(cursor.getColumnIndexOrThrow("fechaCreado"));
        item.estado = cursor.getInt(cursor.getColumnIndexOrThrow("estado"));

        return item;
    }
}
