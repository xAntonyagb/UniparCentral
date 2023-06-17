
package br.unipar.central.exceptions;

public class CampoNaoInformadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public CampoNaoInformadoException(String campo) {
        super("O campo " + campo + " não foi informado!");
    }
}
