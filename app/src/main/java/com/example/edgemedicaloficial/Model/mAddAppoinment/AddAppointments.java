package com.example.edgemedicaloficial.Model.mAddAppoinment;

public class AddAppointments
{
    String id;
    String doctor;
    String fecha;
    String descripcion;

    public AddAppointments(String id, String doctor, String fecha, String descripcion) {
        this.id = id;
        this.doctor = doctor;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
