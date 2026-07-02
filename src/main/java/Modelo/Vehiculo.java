package Modelo;

public class Vehiculo {

    private int codigo;
    private String tipo;
    private double capacidad;
    private double velocidad;
    private String estado;

    public Vehiculo() {
    }

    public Vehiculo(int codigo, String tipo, double capacidad, double velocidad, String estado) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.velocidad = velocidad;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}