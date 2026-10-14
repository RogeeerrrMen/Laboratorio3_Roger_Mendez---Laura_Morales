public class Main {

    public static void main(String[] args) {

        SistemaEnvios sistema = new SistemaEnvios();

        //Datos iniciales propuestos

        sistema.agregarEnvioInicial(new Sobre("S001", "Ana", "Luis", 0.2, "URBANO"));

        sistema.agregarEnvioInicial(new Sobre("S002", "Pedro", "Juana", 0.4, "DEPARTAMENTAL"));

        sistema.agregarEnvioInicial(new Paquete("P001", "Sebastian", "Lucia", 3.2, "URBANO", 40, 30, 20));

        sistema.agregarEnvioInicial(new Paquete("P002", "Roger", "Sofia", 5, "DEPARTAMENTAL", 20, 20, 20));

        sistema.agregarEnvioInicial(new PaqueteFragil("F001", "Diego", "Laura", 3.2, "URBANO", 40, 30, 20, 1200));

        sistema.agregarEnvioInicial(new PaqueteFragil("F002", "Mario", "Elena", 2, "DEPARTAMENTAL", 20, 20, 20, 500));

        sistema.agregarEnvioInicial(new CargaRefrigerada("R001", "Santi", "Pablo", 10, "URBANO", -5));

        sistema.agregarEnvioInicial(new CargaRefrigerada("R002", "Marta", "Julia", 5, "URBANO", 4));

        sistema.registrarVehiculo(new Vehiculo("P111ABC", 100, false));

        sistema.registrarVehiculo(new Vehiculo("P222ABC", 500, true));

        sistema.registrarVehiculo(new Vehiculo("P333ABC", 50, false));

        VistaConsola vista = new VistaConsola(sistema);
        vista.mostrarMenu();
        
    }
}