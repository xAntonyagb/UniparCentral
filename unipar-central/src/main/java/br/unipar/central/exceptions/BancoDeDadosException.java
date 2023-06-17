package br.unipar.central.exceptions;

public class BancoDeDadosException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public BancoDeDadosException(String msg){
        super("Um erro aconteceu ao atuar no banco de dados: \n"+ msg);
    }
}
