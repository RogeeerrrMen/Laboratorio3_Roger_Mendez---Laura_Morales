public class PaqueteFragil extends Paquete {

    private double valorDeclarado;

    public PaqueteFragil(String codigo, String remitente, String destinatario, double pesoReal, String destino, double largo, double ancho, double alto, double valorDeclarado) {

        super (codigo, remitente, destinatario, pesoReal, destino, largo, ancho, alto);

        if (valorDeclarado <= 0) {

            throw new IllegalArgumentException("El valor declarado debe ser mayor a 0");
        }

        this.valorDeclarado = valorDeclarado;
    }

    @Override
    public double calcularCosto() {

        double costoPaquete = super.calcularCosto();
        double embalaje = 15;
        double seguro = valorDeclarado * 0.3;

        return costoPaquete + embalaje + seguro;

    }

    @Override
    public int estimarEntrega () {

        return super.estimarEntrega() + 1;

    }

    @Override
    public int maxIntentos() {

        return 2;
    }

}