package com.example.edgemedicaloficial.Model.mRegistro;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "user")
public class User
{
    String nombres;
    String apellidos;
    String birthdate;
    String telefono;
    String email;
    String pais;
    String token;
    String username;

    @NonNull
    @PrimaryKey
    String id_paciente;

    public User(String nombres, String apellidos, String birthdate, String telefono, String email, String pais, String token, String username, @NonNull String id_paciente) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.birthdate = birthdate;
        this.telefono = telefono;
        this.email = email;
        this.pais = pais;
        this.token = token;
        this.username = username;
        this.id_paciente = id_paciente;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    public String getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(@NonNull String id_paciente) {
        this.id_paciente = id_paciente;
    }
}