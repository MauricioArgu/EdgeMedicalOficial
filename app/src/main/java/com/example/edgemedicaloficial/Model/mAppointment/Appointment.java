package com.example.edgemedicaloficial.Model.mAppointment;

public class Appointment {
    String idPaciente;
    String titulo;
    String descripcion;
    String doctor;
    String fecha;

    public Appointment(String idPaciente, String titulo, String descripcion, String doctor, String fecha) {
        this.idPaciente = idPaciente;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.doctor = doctor;
        this.fecha = fecha;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
