package Modelo;

public class Entrega {

    private int codigo;
    private String fecha;
    private double tiempo;
    private double co2;
    private int codigoVehiculo;
    private int codigoPedido;
    private String estado;

    public Entrega() {
    }

    public Entrega(int codigo, String fecha, double tiempo, double co2, int codigoVehiculo, int codigoPedido, String estado) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.co2 = co2;
        this.codigoVehiculo = codigoVehiculo;
        this.codigoPedido = codigoPedido;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public double getCo2() {
        return co2;
    }

    public void setCo2(double co2) {
        this.co2 = co2;
    }

    public int getCodigoVehiculo() {
        return codigoVehiculo;
    }

    public void setCodigoVehiculo(int codigoVehiculo) {
        this.codigoVehiculo = codigoVehiculo;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}