/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import DAO.AvionDAO;

/**
 *
 * @author Luis Vasquez
 */
public class Avion {
    private int idAvion;
    private String Marca;
    private String Modelo;
    private String Matricula;
    private int Capacidad;
    private String Piloto;
    private String Copiloto;
    private String Azafata;
        
    private int CapacidadOriginal;
    
    public Avion() {
    }

    public Avion(int idAvion, String Marca, String Modelo, String Matricula, int Capacidad, String Piloto, String Copiloto, String Azafata) {
        AvionDAO si= new AvionDAO();
        this.idAvion = idAvion;
        this.Marca = Marca;
        this.Modelo = Modelo;
        this.Matricula = Matricula;
        this.Capacidad = Capacidad;
        this.CapacidadOriginal = Capacidad ;
        this.Piloto = Piloto;
        this.Copiloto = Copiloto;
        this.Azafata = Azafata;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int Capacidad) {
        this.Capacidad = Capacidad;
    }

    public String getPiloto() {
        return Piloto;
    }

    public void setPiloto(String Piloto) {
        this.Piloto = Piloto;
    }

    public String getCopiloto() {
        return Copiloto;
    }

    public void setCopiloto(String Copiloto) {
        this.Copiloto = Copiloto;
    }

    public String getAzafata() {
        return Azafata;
    }

    public void setAzafata(String Azafata) {
        this.Azafata = Azafata;
    }

    public int getCapacidadOriginal() {
        return CapacidadOriginal;
    }

    public void setCapacidadOriginal(int CapacidadOriginal) {
        this.CapacidadOriginal = CapacidadOriginal;
    }
    
    public void reiniciarCapacidad() {
    this.Capacidad = this.CapacidadOriginal;
    System.out.println("Capacidad reiniciada a: " + this.Capacidad);
}

}
