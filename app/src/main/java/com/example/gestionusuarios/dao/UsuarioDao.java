package com.example.gestionusuarios.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gestionusuarios.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private SQLiteDatabase db;
    public UsuarioDao(SQLiteDatabase db){
        this.db = db;
    }

    public boolean delete(int id){
        int rowId = this.db.delete("users", "id = ?", new String[] { id + ""});
        return rowId != 0;
    }
    public boolean update(Usuario model){
        ContentValues values = new ContentValues();
        values.put("name", model.name);

        if(model.password != null && !model.password.isEmpty())
            values.put("password", model.password);

        values.put("lastName", model.lastName);
        values.put("email", model.email);
        values.put("estado", model.estado);

        int rowId = this.db.update("users", values, "id = ?", new String[] { String.valueOf(model.id) });
        return rowId != 0;
    }
    public boolean create(Usuario model){
        ContentValues values = new ContentValues();
        values.put("name", model.name);
        values.put("password", model.password);
        values.put("lastName", model.lastName);
        values.put("email", model.email);
        values.put("fechaCreado", model.createDate);
        values.put("estado", model.estado);

        long rowId = this.db.insert("users", null, values);
        return rowId != 0;
    }
    public Usuario getById(int id) {
        Cursor cursor = db.query("users", Usuario.columns,
                "id = ?",
                new String[]{ id + "" },
                null, null, null);
        if (cursor.moveToFirst()){
            return convertCursor(cursor);
        }
        return null;
    }
    public Usuario login(String email, String password){
        Cursor cursor = db.query("users", Usuario.columns,
                "email = ? AND password = ?",
                new String[]{ email, password },
                null, null, null);
        if (cursor.moveToFirst()){
            return convertCursor(cursor);
        }
        return null;
    }

    public List<Usuario> getAll(){
        Cursor cursor = db.query("users", Usuario.columns,
                null,
                null,
                null, null, null);
        ArrayList<Usuario> lista = new ArrayList<>();
        while (cursor.moveToNext()){
            lista.add(convertCursor(cursor));
        }
        return lista;
    }

    private Usuario convertCursor(Cursor cursor){
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
