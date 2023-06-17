
package br.unipar.central.exceptions;

public class EntidadeNaoInformadaException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public EntidadeNaoInformadaException(String msg) {
        super(msg);
    }
}
