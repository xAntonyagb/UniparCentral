package br.unipar.central.exceptions;

public class IdInvalidoException extends RuntimeException {
    public IdInvalidoException() {
        super("O tamanho do id não contiz com com os padrões do banco!");
    }
    
}
