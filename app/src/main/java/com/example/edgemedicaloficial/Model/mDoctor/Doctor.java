package com.example.edgemedicaloficial.Model.mDoctor;

public class Doctor
{
    private String id;
    private String nombres;
    private String email;
    private String tel;

    public Doctor(String id, String nombres, String email, String tel) {
        this.id = id;
        this.nombres = nombres;
        this.email = email;
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
