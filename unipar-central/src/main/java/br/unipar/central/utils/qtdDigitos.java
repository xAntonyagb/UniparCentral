package br.unipar.central.utils;

import java.math.BigDecimal;

public class qtdDigitos {
    
    public static int qtdDigitos(long num){
        String aux = Long.toString(num);
        return aux.length();
    }
    
    public static int qtdDigitosAntesVirgula(BigDecimal num){
        String aux = num.toString();
        aux = aux.substring(0, aux.indexOf("."));
        return aux.length();
    }
    
    public static int qtdDigitosDepoisVirgula(BigDecimal num){
        String aux = num.toString();
        aux = aux.substring(aux.indexOf(".")+1, aux.length());
        return aux.length();
    }
}
