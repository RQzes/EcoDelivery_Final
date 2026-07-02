package Modelo;

public class Pedido {

    private int codigo;
    private String descripcion;
    private double distancia;
    private int codigoCliente;
    private String estado;

    public Pedido() {
    }

    public Pedido(int codigo, String descripcion, double distancia, int codigoCliente, String estado) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.codigoCliente = codigoCliente;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}