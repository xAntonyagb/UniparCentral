
package br.unipar.central.exceptions;

public class CampoExcedidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public CampoExcedidoException(String campo, int qtd) {
        super("O campo " + campo + " excede a quantidade máxima de caracteres!\nTamanho: " + qtd);
    }
}
