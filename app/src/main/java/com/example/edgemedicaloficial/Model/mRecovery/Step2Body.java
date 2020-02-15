package com.example.edgemedicaloficial.Model.mRecovery;

public class Step2Body
{
    String correo;
    String codigo;

    public Step2Body(String correo, String codigo) {
        this.correo = correo;
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
