package com.example.edgemedicaloficial.Model.mRegistro;

public class RegistroBody
{
    String username;
    String password;
    String pais;
    String nombres;
    String apellidos;
    String birth;
    String email;
    String telefono;

    public RegistroBody(String username, String password, String pais, String nombres, String apellidos, String birth, String email, String telefono) {
        this.username = username;
        this.password = password;
        this.pais = pais;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.birth = birth;
        this.email = email;
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
