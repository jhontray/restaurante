package Modelo;

public class Mesas {
    private int id;
    private int numero;
    private int capacidad;
    private String estado; // "Disponible" o "Ocupada"

    public Mesas() {}

    public Mesas(int id, int numero, int capacidad, String estado) {
        this.id = id;
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public Mesas(int numero, int capacidad, String estado) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Mesa " + numero + " (Cap: " + capacidad + ", Estado: " + estado + ")";
    }
}

