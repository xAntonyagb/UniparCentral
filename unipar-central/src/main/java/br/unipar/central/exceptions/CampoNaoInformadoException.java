
package br.unipar.central.exceptions;

public class CampoNaoInformadoException extends RuntimeException {
    
    public CampoNaoInformadoException(String campo) {
        super("O campo " + campo + " não foi informado!");
    }
}
