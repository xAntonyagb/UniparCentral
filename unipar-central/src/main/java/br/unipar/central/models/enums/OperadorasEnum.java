
package br.unipar.central.models.enums;

import java.util.Random;

public enum OperadorasEnum {
    TIM(1, 41),
    VIVO(2, 15),
    CLARO(3, 21),
    OI(4, 31);
    
    int codigoOperadora;
    int codigo;

    private OperadorasEnum(int codigoOperadora, int codigo) {
        this.codigoOperadora = codigoOperadora;
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigoOperadora() {
        return codigoOperadora;
    }
    
    public static OperadorasEnum paraEnum(int codigo){
        OperadorasEnum tipo = null;

        for (OperadorasEnum operadora : OperadorasEnum.values()) {
            if (operadora.getCodigo() == codigo) 
                tipo = operadora;
        }
        
        if(tipo == null)
            for (OperadorasEnum codOperadora : OperadorasEnum.values()) {
                if (codOperadora.getCodigoOperadora()== codigo) 
                    tipo = codOperadora;
            }
        
        //Pegar qualquer operadora caso tenham colocado errado:
        if(tipo == null){
            Random r = new Random();
            paraEnum(r.nextInt(1,4));
        }
        
        return tipo;
    }

}
