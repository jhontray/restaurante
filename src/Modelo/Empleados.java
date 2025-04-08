package Modelo;



/**
 * Clase Empleados
 *
 * Representa el modelo de datos para un empleado. Esta clase se utiliza para
 * almacenar y transferir información entre la base de datos y la interfaz gráfica.
 */
public class Empleados {
    // Atributos privados que representan las columnas de la tabla empleados en la base de datos
    private int id_empleado;
    private String nombre;
    private String cargo;
    private String salario;

    /**
     * Constructor con parámetros
     *
     * @param id       Identificador único del empleado
     * @param nombre   Nombre del empleado
     * @param cargo    Cargo o puesto del empleado
     * @param salario  Salario del empleado
     *
     * Este constructor permite crear objetos Empleados con los datos obtenidos desde la base de datos
     * o ingresados por el usuario.
     */
    public Empleados(int id, String nombre, String cargo, String salario) {
        this.id_empleado = id;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    // Getters y Setters
    // Permiten acceder y modificar los valores de los atributos privados

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id) {
        this.id_empleado = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }
}
