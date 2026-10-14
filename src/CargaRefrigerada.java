public class CargaRefrigerada extends Envio {

    private double temperatura;

    public CargaRefrigerada(String codigo, String remitente, String destinatario, double pesoReal, String destino, double temperatura) {

        super(codigo, remitente, destinatario, pesoReal, destino);

        if (temperatura < -25 || temperatura > 8) {

            throw new IllegalArgumentException("La temperatura debe estar entre -25 y 8 grados");
        }

        if (!destino.equals("URBANO")) {

            throw new IllegalArgumentException("La carga refrigerada solo permite destino urbano");
        }

        this.temperatura = temperatura;
    }

    @Override
    public double calcularCosto() {

        double tarifaBase = 25;
        double costoPeso = getPesoReal() * 12;
        double cadenaFrio = 60;

        return tarifaBase + costoPeso + cadenaFrio;
    }

    @Override
    public int estimarEntrega() {

        return 1;
    }

    @Override
    public int maxIntentos() {

        return 1;
    }

    @Override
    public boolean esCompatible(Vehiculo vehiculo) {

        if (vehiculo == null) {

            return false;

        }

        return vehiculo.tieneRefrigeracion();
    }

}