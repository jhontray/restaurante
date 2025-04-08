package Modelo;

public class Orden {
    int id_orden;
    int id_cliente;
    int id_empleado;
    int id_mesa;
    String estado;

    public Orden (int id_orden, int id_cliente, int id_empleado, int id_mesa, String estado) {
        this.id_orden = id_orden;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.id_mesa = id_mesa;
        this.estado = estado;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

