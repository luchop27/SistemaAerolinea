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
public class Vuelo {
    private int idVuelo;
    private int Avion;
    private String Origen;
    private String Destino;
    private Date Fecha;
    private String Hora;
    private int Precio;

    public Vuelo() {
    }

    public Vuelo(int idVuelo, int Avion, String Origen, String Destino, Date Fecha, String Hora, int Precio) {
        this.idVuelo = idVuelo;
        this.Avion = Avion;
        this.Origen = Origen;
        this.Destino = Destino;
        this.Fecha = Fecha;
        this.Hora = Hora;
        this.Precio = Precio;
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

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int Precio) {
        this.Precio = Precio;
    }
    

}
