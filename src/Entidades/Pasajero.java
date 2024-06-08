/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.util.Date;

/**
 *
 * @author Luis Vasquez
 */
public class Pasajero {
    
    private int idPasajero;
    private int idVuelo;
    private int Avion;
    private String Nombre;
    private String Cedula;
    private String Edad;
    private int Asientos;
    private String Fecha;
    private String Hora;
    private String Origen;
    private String Destino;
    private int Precio;
    private String Categoria;
    private int Total;

    public Pasajero() {
    }

    public Pasajero(int idPasajero, int idVuelo, int Avion, String Nombre, String Cedula, String Edad, int Asientos, String Fecha, String Hora, String Origen, String Destino, int Precio, String Categoria, int Total) {
        this.idPasajero = idPasajero;
        this.idVuelo = idVuelo;
        this.Avion = Avion;
        this.Nombre = Nombre;
        this.Cedula = Cedula;
        this.Edad = Edad;
        this.Asientos = Asientos;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Origen = Origen;
        this.Destino = Destino;
        this.Precio = Precio;
        this.Categoria = Categoria;
        this.Total = Total;
    }

    public int getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }

    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getAvion() {
        return Avion;
    }

    public void setAvion(int Avion) {
        this.Avion = Avion;
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

    public int getAsientos() {
        return Asientos;
    }

    public void setAsientos(int Asientos) {
        this.Asientos = Asientos;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String Origen) {
        this.Origen = Origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String Destino) {
        this.Destino = Destino;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    
}
