package br.unipar.central.exceptions;

public class IdInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public IdInvalidoException() {
        super("O tamanho do id não contiz com com os padrões do banco!");
    }
    
}
