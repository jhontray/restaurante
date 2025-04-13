package Modelo;

/**
 * Clase modelo que representa la relación entre una orden y un producto.
 * Incluye el ID de la orden, el ID del producto, la cantidad comprada y el precio unitario.
 */
public class OrdenProducto {

    // Atributos de la clase
    private int idOrden;           // ID de la orden
    private int idProducto;        // ID del producto
    private int cantidad;          // Cantidad del producto en la orden
    private double precioUnitario; // Precio unitario del producto en ese momento

    /**
     * Constructor vacío.
     * Es necesario para algunas operaciones como la lectura desde base de datos o frameworks.
     */
    public OrdenProducto() {
    }

    /**
     * Constructor con parámetros.
     * Se utiliza cuando se quiere crear una relación orden-producto para guardar en la base de datos.
     *
     * @param idOrden        ID de la orden
     * @param idProducto     ID del producto
     * @param cantidad       Cantidad del producto
     * @param precioUnitario Precio unitario del producto
     */
    public OrdenProducto(int idOrden, int idProducto, int cantidad, double precioUnitario) {
        this.idOrden = idOrden;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Métodos Getter y Setter

    /**
     * @return ID de la orden
     */
    public int getIdOrden() {
        return idOrden;
    }

    /**
     * @param idOrden Establece el ID de la orden
     */
    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    /**
     * @return ID del producto
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto Establece el ID del producto
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return Cantidad del producto
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad Establece la cantidad del producto
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return Precio unitario del producto
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * @param precioUnitario Establece el precio unitario del producto
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
