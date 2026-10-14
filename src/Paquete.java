public class Paquete extends Envio {

    private double largo;
    private double ancho;
    private double alto;

    public Paquete(String codigo, String remitente, String destinatario, double pesoReal, String destino, double largo, double ancho, double alto) {

        super(codigo, remitente, destinatario, pesoReal, destino);

        if (largo <= 0 || ancho <= 0 || alto <= 0) {

            throw new IllegalArgumentException("Las dimensiones deben ser mayor a 0");
        }

        this.largo = largo;
        this.ancho = ancho;
        this.alto = alto;
    }

    public double calcularPesoFavorable() {

        double pesoVolumetrico = (largo * ancho * alto) / 5000;
        double pesoMayor = Math.max(getPesoReal(), pesoVolumetrico);

        return Math.ceil(pesoMayor);
    }

    @Override
    public double calcularCosto() {

        double tarifaBase;

        if (getDestino().equals("URBANO")) {

            tarifaBase = 25;
        } else {

            tarifaBase = 40;
        }

        return tarifaBase + (8 * calcularPesoFavorable());

    }

    @Override
    public int estimarEntrega() {

        if (getDestino().equals("URBANO")) {

            return 2;

        } else {

            return 4;
        }

    }

    @Override
    public int maxIntentos() {

        return 3;
    }
}