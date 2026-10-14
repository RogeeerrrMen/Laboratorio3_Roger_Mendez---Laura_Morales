import java.util.ArrayList;

public class Despacho {


    private Vehiculo vehiculo;
    private ArrayList<Envio> envios;
    private String estado;

    public Despacho(Vehiculo vehiculo) {

        if (vehiculo == null) {

            throw new IllegalArgumentException("Debe haber vehiculo");
        }

        this.vehiculo = vehiculo;
        this.envios = new ArrayList<Envio>();
        this.estado = "PREPARACION";
    }

    public double calcularPeso() {

        double total = 0;

        for (Envio envio : envios) {
            
            total += envio.getPesoReal();

        }

        return total;
    }

    public boolean asignarEnvio(Envio envio) {

        if (!estado.equals("PREPARACION") || envio == null) {

            return false;

        }

        if (!envio.getEstado().equals("EN_BODEGA")) {

            return false;

        }

        if (envio.getIntentos() >= envio.maxIntentos()) {

            return false;

        }

        if (envios.contains(envio)) {

            return false;

        }

        if (!envio.esCompatible(vehiculo)) {

            return false;

        }

        if (calcularPeso() + envio.getPesoReal() > vehiculo.getCapacidadMaxima()) {

            return false;

        }

        envios.add(envio);
        return true;   
    }

    public boolean retirarEnvio(Envio envio) {

        if (!estado.equals("PREPARACION")) {

            return false;

        }

        return envios.remove(envio);
    }

    public boolean salir() {

        if (!estado.equals("PREPARACION") || envios.isEmpty()) {
            
            return false;
        }

        
        for (Envio envio : envios) {

            if (!envio.getEstado().equals("EN_BODEGA") || envio.getIntentos() >= envio.maxIntentos()) {

                return false;
            }
        }

        for (Envio envio : envios) {

            envio.registrarSalida();

        }

        estado = "EN_RUTA";
        return true;

    }

    public void registrarResultado(Envio envio, boolean entregado) {

        if (!estado.equals("EN_RUTA")) {

            throw new IllegalStateException("El despacho no esta en ruta");

        }

        if (envio == null || !envios.contains(envio)) {

            throw new IllegalArgumentException("El envio no pertenece al despacho");

        }

        if (!envio.getEstado().equals("EN_RUTA")) {

            throw new IllegalStateException("El envio ya tiene un resultado registrado");

        }

        envio.registrarResultado(entregado);

        boolean todosProcesados = true;

        for (Envio actual : envios) {

            if(actual.getEstado().equals("EN_RUTA")) {

                todosProcesados = false;
            }
        }

        if (todosProcesados) {

            estado = "CERRADO";
        }
    }

    public String getEstado() {

        return estado;

    }

    public Vehiculo getVehiculo() {

        return vehiculo;

    }

    //Ajuste al diseno, se agrega este arraylist para que el controlador pueda consultar los envios asignados.

    public ArrayList<Envio> getEnvios() {

        return new ArrayList<Envio>(envios);
    }

}