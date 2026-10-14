public class Sobre extends Envio {

    public Sobre(String codigo, String remitente, String destinatario, double pesoReal, String destino) {

        super(codigo, remitente, destinatario, pesoReal, destino);

        if (pesoReal > 0.5) {

            throw new IllegalArgumentException("El sobre no puede pesar mas de 0.5 kg");
        }
    }

    @Override 
    public double calcularCosto() {

        if (getDestino().equals("URBANO")) {

            return 20;
        } else {

            return 35;
        }
    }

    @Override 
    public int estimarEntrega() {

        if (getDestino().equals("URBANO")) {

            return 1;
        } else {

            return 2;
        }
    }

    @Override
    public int maxIntentos() {

        return 3;
    }
}