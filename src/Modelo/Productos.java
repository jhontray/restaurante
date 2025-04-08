package Modelo;
/*
ESTE MODELO NOS SIRVE PARA TRAER Y ENVIAR INFORMACION A LA DB DESDE INTELLIJ
**/
public class Productos{

    int id_productos;
    String nombre;
    String categoria;
    Double precio;
    boolean disponibilidad;

    public  Productos(int id_Productos, String nombre, String categoria, double precio, boolean disponibilidad){
        this.id_productos = id_Productos;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.disponibilidad = disponibilidad;

    }

    public int getId_Productos(){return id_productos;}
    public void  setId_Productos(int id_productos){this.id_productos = id_productos;}


    public  String getNombre(){return nombre;}
    public void  setNombre(String nombre){this.nombre=nombre;}


    public  String getCategoria(){return categoria;}
    public void  setCategoria(String categoria){this.categoria=categoria;}


    public  double getPrecio(){return precio;}
    public void  setPrecio(double precio){this.precio=precio;}


    public  boolean getDisponibilidad(){return disponibilidad;}
    public void  setDisponibilidad(boolean disponibilidad){this.disponibilidad=disponibilidad;}
}
