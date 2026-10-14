public class Vehiculo {

    private String placa;
    private double capacidadMaxima;
    private boolean refrigerado;

    public Vehiculo(String placa, double capacidadMaxima, boolean refrigerado) {

        if (placa == null || placa.equals("")) {

            throw new IllegalArgumentException("La placa no puede estar vacia");
        }

        if (capacidadMaxima <= 0) {

            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");

        }

        this.placa = placa;
        this.capacidadMaxima = capacidadMaxima;
        this.refrigerado = refrigerado;
    }

    public String getPlaca() {

        return placa;
    }

    
    public double getCapacidadMaxima() {

        return capacidadMaxima;
    }

    
    public boolean tieneRefrigeracion() {
        
        return refrigerado;
    }
}