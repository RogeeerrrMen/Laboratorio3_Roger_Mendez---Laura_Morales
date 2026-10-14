import java.util.ArrayList;

public class SistemaEnvios {

    private ArrayList<Envio> envios;
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Despacho> despachos;
    private double ingresosRegistro;
    private double ingresosReintentos;

    //Ajuste en el diseno, se agrega un arrayList que distingue los envios ya cobrados para calcular ingresos

    private ArrayList<Envio> enviosCobrados;

    public SistemaEnvios() {

        envios = new ArrayList<Envio>();
        vehiculos = new ArrayList<Vehiculo>();
        despachos = new ArrayList<Despacho>();
        enviosCobrados = new ArrayList<Envio>();
        ingresosRegistro = 0;
        ingresosReintentos = 0;

    }

    public double cotizar(Envio envio) {

        if (envio == null) {

            throw new IllegalArgumentException("Envio no valido")

        }

        return envio.calcularCosto();
    }

    public boolean registrarEnvio(Envio envio) {

        if (envio == null) {

            return false;

        }

        for (Envio actual : envios) {

            if (actual.getCodigo().equals(envio.getCodigo())) {

                return false;

            }
        }

        envios.add(envio);
        enviosCobrados.add(envio);
        ingresosRegistro += envio.calcularCosto();
        return true;

    }

    //Ajuste al diseno, Precargar envios sin tener ingresos

    public boolean agregarEnvioInicial(Envio envio) {

        if (envio == null) {

            return false;

        }

        for (Envio actual : envios) {

            if (actual.getCodigo().equals(envio.getCodigo())) {

                return false;

            }
        }

        envios.add(envio);
        return true;

    }

    public boolean registrarVehiculo(Vehiculo vehiculo) {

        if (vehiculo == null) {

            return false;

        }

        for (Vehiculo actual : vehiculos) {

            if (actual.getPlaca().equals(vehiculo.getPlaca())) {

                return false;

            }
        }

        vehiculos.add(vehiculo);
        return true;

    }

    public Despacho crearDespacho(Vehiculo vehiculo) {

        if (vehiculo == null || !vehiculos.contains(vehiculo)) {

            return null;

        }

        for (Despacho actual : despachos) {

            if (actual.getVehiculo() == vehiculo && !actual.getEstado().equals("CERRADO")) {

                return null;

            }
        }

        Despacho nuevo = new Despacho(vehiculo);
        despachos.add(nuevo);
        return nuevo;

    }

    //modificacion: este metodo impide que un envio tenga dos despachos ocupados

    public boolean asignarEnvioADespacho(Despacho despacho, Envio envio) {

        if (despacho == null || envio == null || !despachos.contains(despacho) || !envios.contains(envio)) {

            return false;

        }

        for (Despacho actual : despachos) {

            if (!actual.getEstado().equals("CERRADO") && actual.getEnvios().contains(envio)) {

                return false;

            }
        }

        return despacho.asignarEnvio(envio);

    }

    //modificacion al diseno
    //se implementa este metodo para centralizar el retiro de envios

    public boolean retirarEnvioDeDespacho(Despacho despacho, Envio envio) {

        if (despacho == null || !despachos.contains(despacho)) {

            return false;

        }

        return despacho.retirarEnvio(envio);

    }

    // se anade Metodo que registra el cargo por reintento al salir, se modifco el diseno

    public boolean salirDespacho(Despacho despacho) {

        if (despacho == null || !despachos.contains(despacho)) {

            return false;

        }

        boolean salio = despacho.salir();

        if (salio) {

            for (Envio envio : despacho.getEnvios()) {

                if (envio.getIntentos() > 1) {

                    ingresosReintentos += 15;

                }
            }
        }

        return salio;

    }

    public boolean registrarResultado(Despacho despacho, Envio envio, boolean entregado) {

        if (despacho == null || !despachos.contains(despacho)) {

            return false;

        }

        try {

            despacho.registrarResultado(envio, entregado);
            return true;

        } catch (IllegalArgumentException e) {

            return false;

        } catch (IllegalStateException e) {

            return false;

        }
    }

    public ArrayList<Envio> consultarEnvios() {

        return new ArrayList<Envio>(envios);

    }

    public ArrayList<Vehiculo> consultarVehiculos() {

        return new ArrayList<Vehiculo>(vehiculos);

    }

     public ArrayList<Despacho> consultarDespachos() {

        return new ArrayList<Despacho>(despachos);

    }

    //Reportes generados

     public String generarReportes() {

        String reporte = "=== REPORTE GENERAL ===\n";

        String[] categorias = {"Sobre", "Paquete", "PaqueteFragil","CargaRefrigerada"};

        String[] estados = {"EN_BODEGA", "EN_RUTA","ENTREGADO", "DEVUELTO"};

        for (String categoria : categorias) {

            reporte += "\n" + categoria + ":\n";

            for (String estado : estados) {

                int cantidad = 0;

                for (Envio envio : envios) {

                    if (envio.getClass().getSimpleName().equals(categoria) && envio.getEstado().equals(estado)) {

                        cantidad++;

                    }
                }

                reporte += estado + ": " + cantidad + "\n";
            }
        }

        reporte += "\n=== INGRESOS ===\n";

        reporte += String.format("Registros: Q%.2f\n", ingresosRegistro);

        reporte += String.format("Reintentos: Q%.2f\n", ingresosReintentos);

        reporte += String.format("Total: Q%.2f\n",ingresosRegistro + ingresosReintentos);

        for (String categoria : categorias) {

            double total = 0;

            for (Envio envio : enviosCobrados) {

                if (envio.getClass().getSimpleName().equals(categoria)) {

                    total += envio.calcularCosto();

                }
            }

            for (Envio envio : envios) {

                if (envio.getClass().getSimpleName().equals(categoria) && envio.getIntentos() > 1) {

                    total += (envio.getIntentos() - 1) * 15;

                }
            }

            reporte += String.format("%s: Q%.2f\n", categoria, total);

        }

        reporte += "\n=== DESPACHOS ABIERTOS ===\n";

        for (Despacho despacho : despachos) {

            if (!despacho.getEstado().equals("CERRADO")) {

                double peso = despacho.calcularPeso();
                double capacidad = despacho.getVehiculo().getCapacidadMaxima();

                reporte += "Vehiculo: " + despacho.getVehiculo().getPlaca() + "\n";

                reporte += "Estado: " + despacho.getEstado() + "\n";

                reporte += String.format("Peso: %.2f kg\n", peso);

                reporte += String.format("Disponible: %.2f kg\n", capacidad - peso);

            }
        }

        return reporte;

    }

    
}






