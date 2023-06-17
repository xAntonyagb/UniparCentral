package br.unipar.central.exceptions;

public class ColunaNaoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ColunaNaoEncontradaException(String coluna) {
        super("Não foi possivel encontrar um(a) "+ coluna +" com as informações fornecidas!");
    }
}
