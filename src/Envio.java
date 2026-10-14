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
        
            throw new IllegalArgumentException("El codigo no puede estar vacio.");

        }

        if (remitente == null || remitente.equals("")) {

            throw new IllegalArgumentException("El remitente no puede estar vacio.");

        }

        if (destinatario == null || destinatario.equals("")) {

            throw new IllegalArgumentException("El destinatario no puede estar vacio.");

        }

        if (pesoReal <= 0 ) {

            throw new IllegalArgumentException("El peso debe ser positivo y valido.");

        }

        if (destino == null || (!destino.equals("URBANO") && !destino.equals("DEPARTAMENTAL"))) {

            throw new IllegalArgumentException("El destino no es valido.");

        }

        this.codigo = codigo;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.pesoReal = pesoReal;
        this.destino = destino;
        this.estado = "EN_BODEGA";
        this.intentos = 0;

    }

}