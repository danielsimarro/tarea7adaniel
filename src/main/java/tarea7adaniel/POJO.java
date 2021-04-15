/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea7adaniel;

import java.time.LocalDate;


/**
 *
 * @author daniel
 */
public class POJO {
    
    //Atributos de la clase POJO
    private String empleado;
    private String dni;
    private String puesto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private char telefono;
    private boolean evaluador;
    private boolean coordinador;
    
    //Metodos getters y setters
    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public char getTelefono() {
        return telefono;
    }

    public void setTelefono(char telefono) {
        this.telefono = telefono;
    }

    public boolean isEvaluador() {
        return evaluador;
    }

    public void setEvaluador(boolean evaluador) {
        this.evaluador = evaluador;
    }

    public boolean isCoordinador() {
        return coordinador;
    }

    public void setCoordinador(boolean coordinador) {
        this.coordinador = coordinador;
    }

    @Override
    public String toString() {
        return "POJO{" + "empleado=" + empleado + ", dni=" + dni + ", puesto=" + puesto + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", telefono=" + telefono + ", evaluador=" + evaluador + ", coordinador=" + coordinador + '}';
    }
    
    
}
