package Modelo;

import java.sql.Date;

public class Reportes {
    private int idOrden;
    private Date fecha;
    private double total;

    public Reportes(int idOrden, Date fecha, double total) {
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.total = total;
    }

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
}

