/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Luis Vasquez
 */
public class Azafata {
    private int idAzafata;
    private String Nombre;
    private String Cedula;
    private String Edad;
    private boolean asignado;

    public Azafata() {
        this.asignado=false;
    }

    public Azafata(int idAzafata, String Nombre, String Cedula, String Edad) {
        this.idAzafata = idAzafata;
        this.Nombre = Nombre;
        this.Cedula = Cedula;
        this.Edad = Edad;
    }

    public int getIdAzafata() {
        return idAzafata;
    }

    public void setIdAzafata(int idAzafata) {
        this.idAzafata = idAzafata;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String Edad) {
        this.Edad = Edad;
    }

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }
    public void marcarComoAsignado() {
        this.asignado = true;
    }

    public void marcarComoNoAsignado() {
        this.asignado = false;
    }

    public boolean estaAsignado() {
        return this.asignado;
    }
    
}
