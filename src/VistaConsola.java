import java.util.Scanner;
import java.util.ArrayList;

public class VistaConsola {

    private Scanner scanner;
    private SistemaEnvios sistema;

    public VistaConsola(SistemaEnvios sistema) {

        this.scanner = new Scanner(System.in);
        this.sistema = sistema;
    }

    //Modificacion: se agregan metodos privados para manejo de las opciones del menu y validar entradas

    private String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return scanner.nextLine();

    }

    private int leerEntero(String mensaje) {

        while(true) {

            try {

                return Integer .parseInt(leerTexto(mensaje));

            } catch (NumberFormatException e) {

                mostrarMensaje("Ingrese un numero entero, sin decimales");
            }
        }
    }

    private double leerDouble(String mensaje) {

        while (true) {

            try {

                return Double.parseDouble(leerTexto(mensaje));

            } catch (NumberFormatException e) {

                mostrarMensaje("Debe ingresar un numero valido");

            }
        }
    }

    private boolean leerBooleano(String mensaje) {

        while (true) {

            String respuesta = leerTexto(mensaje);

            if (respuesta.equals("S") || respuesta.equals("s")) {

                return true;

            }

            if (respuesta.equals("N") || respuesta.equals("n")) {

                return false;
            }

            mostrarMensaje("Responda S o N.");

        }
    }

    private int solicitarOpcion() {

        return leerEntero("Seleccione una opcion: ");
    }

    public void mostrarMensaje(String mensaje) {

        System.out.println(mensaje);
    }

    private Envio crearEnvio() {

        mostrarMensaje("\n1. Sobre");
        mostrarMensaje("2. Paquete");
        mostrarMensaje("3. Paquete fragil");
        mostrarMensaje("4. Carga refrigerada");

        int tipo = leerEntero("Categoria: ");

        if (tipo < 1 || tipo > 4) {

            mostrarMensaje("Categoria inexistente");
            return null;

        }

        String codigo = leerTexto("Codigo: ");
        String remitente = leerTexto("Remitente: ");
        String destinatario = leerTexto("Destinatario: ");
        double peso = leerDouble("Peso real (kg): ");

        mostrarMensaje("1. Urbano");
        mostrarMensaje("2. Departamental");

        int opcionDestino = leerEntero("Destino: ");
        String destino;

        if (opcionDestino == 1) {

            destino = "URBANO";

        } else if (opcionDestino == 2) {

            destino = "DEPARTAMENTAL";

        } else {

            mostrarMensaje("Destino no existente");
            return null;

        }

        if (tipo == 1) {

            return new Sobre(codigo, remitente,destinatario, peso, destino);
        }

        if (tipo == 2 || tipo == 3) {

            double largo = leerDouble("Largo (cm): ");
            double ancho = leerDouble("Ancho (cm): ");
            double alto = leerDouble("Alto (cm): ");

            if (tipo == 2) {
                return new Paquete(codigo, remitente,destinatario, peso, destino, largo, ancho, alto);
            }

            double valor = leerDouble("Valor declarado (Q): ");

            return new PaqueteFragil(codigo, remitente, destinatario, peso, destino, largo, ancho, alto, valor);

        }

        double temperatura = leerDouble("Temperatura requerida (C): ");

        return new CargaRefrigerada(codigo, remitente, destinatario, peso, destino, temperatura);

    }

    private void cotizarEnvio() {

        Envio envio = crearEnvio();

        if (envio != null) {

            mostrarMensaje(String.format("Costo: Q%.2f", sistema.cotizar(envio)));

            mostrarMensaje("Entrega estimada: " + envio.estimarEntrega() + " dias habiles");
        }

    }

    private void registrarEnvio() {

        Envio envio = crearEnvio();

        if (envio != null) {

            if (sistema.registrarEnvio(envio)) {

                mostrarMensaje(String.format("Envio registrado. Cobro: Q%.2f", envio.calcularCosto()));

            } else {

                mostrarMensaje("No se pudo registrar el envio");

            }
        }
    }

    private void registrarVehiculo() {

        String placa = leerTexto("Placa: ");
        double capacidad = leerDouble("Capacidad (kg): ");
        boolean refrigerado = leerBooleano("Tiene refrigeracion? (S/N): ");

        Vehiculo vehiculo = new Vehiculo(placa, capacidad, refrigerado);

        if (sistema.registrarVehiculo(vehiculo)) {

            mostrarMensaje("Vehiculo registrado");

        } else {

            mostrarMensaje("Placa repetida o vehiculo invalido");

        }
    }

    private Envio buscarEnvio(String codigo) {

        for (Envio envio : sistema.consultarEnvios()) {

            if (envio.getCodigo().equals(codigo)) {

                return envio;
            }
        }

        return null;
    }

    private Vehiculo buscarVehiculo(String placa) {

        for (Vehiculo vehiculo : sistema.consultarVehiculos()) {

            if (vehiculo.getPlaca().equals(placa)) {

                return vehiculo;

            }
        }

        return null;

    }

    private Despacho seleccionarDespacho() {

        ArrayList<Despacho> lista = sistema.consultarDespachos();

        if (lista.isEmpty()) {

            mostrarMensaje("No hay despachos registrados");
            return null;

        }

        for (int i = 0; i < lista.size(); i++) {

            Despacho despacho = lista.get(i);

            mostrarMensaje((i + 1) + ". " + despacho.getVehiculo().getPlaca() + " - " + despacho.getEstado());
        }

        int numero = leerEntero("Numero de despacho: ");

        if (numero < 1 || numero > lista.size()) {

            mostrarMensaje("Despacho inexistente");
            return null;

        }

        return lista.get(numero - 1);

    }

    private void crearDespacho() {

        String placa = leerTexto("Placa del vehiculo: ");
        Vehiculo vehiculo = buscarVehiculo(placa);

        Despacho despacho = sistema.crearDespacho(vehiculo);

        if (despacho == null) {

            mostrarMensaje("Vehiculo no existe u ocupado");

        } else {

            mostrarMensaje("Despacho creado");

        }
    }

    private void asignarEnvio() {

        Despacho despacho = seleccionarDespacho();

        if (despacho == null) {

            return;

        }

        String codigo = leerTexto("Codigo del envio: ");
        Envio envio = buscarEnvio(codigo);

        if (sistema.asignarEnvioADespacho(despacho, envio)) {

            mostrarMensaje("Envio asignado");

        } else {

            mostrarMensaje("No se pudo asignar el envio");

        }
    }

    private void retirarEnvio() {

        Despacho despacho = seleccionarDespacho();

        if(despacho == null) {

            return;
        }

        String codigo = leerTexto("Codigo del envio: ");
        Envio envio = buscarEnvio(codigo);

        if (sistema.retirarEnvioDeDespacho(despacho, envio)) {

            mostrarMensaje("Envio retirado");

        } else {

            mostrarMensaje("No se pudo quitar el envio");
            
        }
    }

    private void salirDespacho() {

        Despacho despacho = seleccionarDespacho();

        if(despacho == null) {

            return;
        }

        if (sistema.salirDespacho(despacho)) {

            mostrarMensaje("Despacho en ruta");

        } else {

            mostrarMensaje("No se pudo hacer la salida");
        }
    }

    private void registrarResultado() {

        Despacho despacho = seleccionarDespacho();

        if (despacho == null) {

            return;

        }

        String codigo = leerTexto("Codigo del envio: ");
        Envio envio = buscarEnvio(codigo);

        boolean entregado = leerBooleano("Entrega exitosa? (S/N): ");

        if (sistema.registrarResultado(despacho, envio, entregado)) {

            mostrarMensaje("Resultado registrado.");

        } else {

            mostrarMensaje("No se pudo registrar el resultado.");

        }
    }

    private void consultarEnvios() {

        for (Envio envio : sistema.consultarEnvios()) {

            mostrarMensaje(envio.getCodigo() + " | " + envio.getClass().getSimpleName() + " | " + envio.getEstado() + " | Peso: " + envio.getPesoReal() + " | Intentos: " + envio.getIntentos());
        }
    }

    private void consultarVehiculos() {

        for(Vehiculo vehiculo : sistema.consultarVehiculos()) {

            mostrarMensaje(vehiculo.getPlaca() + " | Capacidad: " + vehiculo.getCapacidadMaxima() + " kg | Refrigeracion: " + vehiculo.tieneRefrigeracion());

        }
    }

    public void mostrarMenu() {

        int opcion = -1;

        while (opcion != 0){

            mostrarMensaje("\n===== SISTEMA ENVIAGT =====");
            mostrarMensaje("1. Cotizar envio");
            mostrarMensaje("2. Registrar envio");
            mostrarMensaje("3. Registrar vehiculo");
            mostrarMensaje("4. Crear despacho");
            mostrarMensaje("5. Asignar envio");
            mostrarMensaje("6. Retirar envio");
            mostrarMensaje("7. Salir despacho");
            mostrarMensaje("8. Registrar resultado");
            mostrarMensaje("9. Consultar envios");
            mostrarMensaje("10. Consultar vehiculos");
            mostrarMensaje("11. Generar reportes");
            mostrarMensaje("0. Salir");

            opcion = solicitarOpcion();

            try {

                switch (opcion) {

                case 1:

                    cotizarEnvio();
                    break;

                case 2:

                    registrarEnvio();
                    break;

                case 3:

                    registrarVehiculo();
                    break;

                case 4:

                    crearDespacho();
                    break;

                case 5:

                    asignarEnvio();
                    break;

                case 6:

                    retirarEnvio();
                    break;

                case 7:

                    salirDespacho();
                    break;

                case 8:

                    registrarResultado();
                    break;

                case 9:

                    consultarEnvios();
                    break;

                case 10:

                    consultarVehiculos();
                    break;

                case 11:

                    mostrarMensaje(sistema.generarReportes());
                    break;

                case 0:

                    mostrarMensaje("Gracias por usar el servicio");
                    break;

                default:

                    mostrarMensaje("Opcion invalida.");
                    break;
                }

            } catch (IllegalArgumentException e) {

                mostrarMensaje("Error: " + e.getMessage());

            } catch (IllegalStateException e) {

                mostrarMensaje("Error: " + e.getMessage());
            }
        }
    }
}