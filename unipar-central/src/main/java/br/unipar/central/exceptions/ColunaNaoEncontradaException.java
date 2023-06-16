package br.unipar.central.exceptions;

public class ColunaNaoEncontradaException extends RuntimeException {
    public ColunaNaoEncontradaException(String coluna) {
        super("Não foi possivel encontrar um(a) "+ coluna +" com as informações fornecidas!");
    }
}
