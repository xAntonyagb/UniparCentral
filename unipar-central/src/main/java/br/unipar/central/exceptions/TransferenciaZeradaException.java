package br.unipar.central.exceptions;

public class TransferenciaZeradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public TransferenciaZeradaException() {
        super("Você não pode transferir R$ 0,00 para outra conta!");
    }
}
