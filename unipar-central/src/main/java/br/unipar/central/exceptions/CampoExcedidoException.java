
package br.unipar.central.exceptions;

public class CampoExcedidoException extends RuntimeException {
    
    public CampoExcedidoException(String campo, int qtd) {
        super("O campo " + campo + " excede a quantidade máxima de caracteres!\nTamanho: " + qtd);
    }
}
