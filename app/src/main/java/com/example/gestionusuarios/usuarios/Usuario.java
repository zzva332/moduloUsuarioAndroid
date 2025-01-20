package com.example.gestionusuarios.usuarios;

public class Usuario {
    public int id;
    public String name;
    public String password;
    public String lastName;
    public String email;
    public String createDate;
    public int estado;

    public static String[] columns = new String[] { "id", "name", "lastName", "email", "fechaCreado", "estado"};
}
