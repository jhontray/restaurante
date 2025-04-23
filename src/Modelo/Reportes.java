package Modelo;

import java.sql.Date;

public class Reportes {
    private int idOrden;
    private Date fecha;
    private double total;

    // reportes especiales
    private String nombre; // nombre del producto o cliente

    // Constructor para reportes diarios/semanales/mensuales
    public Reportes(int idOrden, Date fecha, double total) {
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.total = total;
    }

    // Constructor para producto más vendido o cliente que más compra
    public Reportes(String nombre, double total) {
        this.nombre = nombre;
        this.total = total;
    }

    // reporte normal
    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // reportes especiales
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


