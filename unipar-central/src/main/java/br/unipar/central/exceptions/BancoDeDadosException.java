package br.unipar.central.exceptions;

public class BancoDeDadosException extends RuntimeException {
    public BancoDeDadosException(String msg){
        super("Um erro aconteceu ao atuar no banco de dados: \n"+ msg);
    }
}
