package com.example.edgemedicaloficial.Model.mHoras;

import androidx.annotation.NonNull;

public class Horas
{
    String hora;

    public Horas(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @NonNull
    @Override
    public String toString() {
        return hora;
    }
}
