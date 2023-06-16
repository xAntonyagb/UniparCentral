package br.unipar.central.models.enums;

import java.util.Random;

public enum TipoContaEnum {
    POUPANCA(1, "Conta Poupança"),
    CORRENTE(2, "Conta Corrente"),
    SALARIO(3, "Conta Salário");
    
    private int codigo;
    private String descricao;

    private TipoContaEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public static TipoContaEnum paraEnum(int codigo){
        TipoContaEnum tipo = null;
        
        for (TipoContaEnum op : TipoContaEnum.values()) {
            if (op.getCodigo() == codigo) 
                tipo = op;
        }
        
        //Pegar qualquer conta caso tenham colocado errado:
        if(tipo == null){
            Random r = new Random();
            paraEnum(r.nextInt(1,3));
        }
        
        return tipo;
    }
    
}
