public abstract class Envio {

    private String codigo;
    private String remitente;
    private String destinatario;
    private double pesoReal;
    private String destino;
    private String estado;
    private int intentos;


    protected Envio(String codigo, String remitente, String destinatario, 
    double pesoReal, String destino) {

        if (codigo == null || codigo.equals("")) {
        
            throw new IllegalArgumentException("El codigo no puede estar vacio");

        }

        if (remitente == null || remitente.equals("")) {

            throw new IllegalArgumentException("El remitente no puede estar vacio");

        }

        if (destinatario == null || destinatario.equals("")) {

            throw new IllegalArgumentException("El destinatario no puede estar vacio");

        }

        if (pesoReal <= 0 ) {

            throw new IllegalArgumentException("El peso debe ser positivo y valido");

        }

        if (destino == null || (!destino.equals("URBANO") && !destino.equals("DEPARTAMENTAL"))) {

            throw new IllegalArgumentException("El destino no es valido");

        }

        this.codigo = codigo;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.pesoReal = pesoReal;
        this.destino = destino;
        this.estado = "EN_BODEGA";
        this.intentos = 0;

    }

    public abstract double calcularCosto();

    public abstract int estimarEntrega();

    public abstract int maxIntentos();

    public boolean esCompatible(Vehiculo vehiculo) {

        return vehiculo != null;
    }

    public void registrarSalida() {

        if (!estado.equals("EN_BODEGA")) {

            throw new IllegalStateException("El envio no esta en bodega");
        }

        if (intentos >= maxIntentos()) {

            throw new IllegalStateException("El envio ya no tiene intentos");
        }

        intentos++;
        estado = "EN_RUTA";

    }

    public void registrarResultado(boolean entregado) {

        if (!estado.equals("EN_RUTA")) {

            throw new IllegalStateException("El envio no esta en ruta");

        }

        if (entregado) {

            estado = "ENTREGADO";
        } else if (intentos >= maxIntentos()) {

            estado = "DEVUELTO";
        } else {

            estado = "EN_BODEGA";
        }
    }

    public String getCodigo() {

        return codigo;
    }

    public double getPesoReal() {

        return pesoReal;
    }

    public String getEstado() {

        return estado;
    }

    public int getIntentos() {

        return intentos;
    }


    //Ajuste al diseno, hizo falta un getter para destino, esto para que las clases hijas consulten el destino

    public String getDestino() {

        return destino;
    }

}