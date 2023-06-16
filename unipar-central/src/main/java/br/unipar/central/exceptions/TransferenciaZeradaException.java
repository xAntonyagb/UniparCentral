package br.unipar.central.exceptions;

public class TransferenciaZeradaException extends RuntimeException {
    public TransferenciaZeradaException() {
        super("Você não pode transferir R$ 0,00 para outra conta!");
    }
}
