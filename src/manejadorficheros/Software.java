/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manejadorficheros;

/**
 *
 * @author David Caamaño Aldemunde
 */
public class Software {
    private String nombre;
    private String descripcion;
    private String version;
    private Double precio;
    private String requisitos;
    
    public Software(){
        this.nombre = "";
        this.descripcion = "";
        this.version = "";
        this.precio = 0.0;
        this.requisitos = "";
    }
    
    public Software(String nombre,String descripcion, String version, 
            Double precio,String requisitos){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
        this.precio = precio;
        this.requisitos = requisitos;
    }
    
    public Software(String nombre, String descripcion, String version){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.version = version;
    }
    public Software(String nombre){
        this.nombre = nombre;

    }
    
    //Getters
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public String getVersion(){
        return version;
    }
    public Double getPrecio(){
        return precio;
    }
    public String getRequisitos(){
        return requisitos;
    }
    
    //Setters
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    public void setVersion(String version){
        this.version = version;
    }
    public void setPrecio(Double precio){
        this.precio = precio;
    }
    public void setRequisitos(String requisitos){
        this.requisitos = requisitos;
    }
    
    @Override
    public String toString(){
        String s = nombre+ "\n" + descripcion + "\n" + version + "\n" + 
                    precio + "\n" + requisitos + "\n";
        return s;
    }
}
