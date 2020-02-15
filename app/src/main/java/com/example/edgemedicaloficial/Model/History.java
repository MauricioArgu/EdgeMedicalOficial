package com.example.edgemedicaloficial.Model;

public class History
{
    private String historyTitulo;
    private String historyFecha;
    private String historyDoctor;
    private int historyEstado;

    public History(String historyTitulo, String historyFecha, String historyDoctor, int historyEstado) {
        this.historyTitulo = historyTitulo;
        this.historyFecha = historyFecha;
        this.historyDoctor = historyDoctor;
        this.historyEstado = historyEstado;
    }

    public String getHistoryTitulo() {
        return historyTitulo;
    }

    public void setHistoryTitulo(String historyTitulo) {
        this.historyTitulo = historyTitulo;
    }

    public String getHistoryFecha() {
        return historyFecha;
    }

    public void setHistoryFecha(String historyFecha) {
        this.historyFecha = historyFecha;
    }

    public String getHistoryDoctor() {
        return historyDoctor;
    }

    public void setHistoryDoctor(String historyDoctor) {
        this.historyDoctor = historyDoctor;
    }

    public int getHistoryEstado() {
        return historyEstado;
    }

    public void setHistoryEstado(int historyEstado) {
        this.historyEstado = historyEstado;
    }
}
