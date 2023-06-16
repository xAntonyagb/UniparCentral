package br.unipar.central.models.enums;

import java.util.Random;

public enum TipoTransacaoEnum {
    PIX(1, "Pix"),
    TRANSFERENCIA_BANCARIA(2, "TED"),
    CHEQUE(3, "Cheque");
    
    private int codigo;
    private String descricao;

    private TipoTransacaoEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoTransacaoEnum paraEnum(int codigo){
        TipoTransacaoEnum tipo = null;
        
        for (TipoTransacaoEnum op : TipoTransacaoEnum.values()) {
            
            //Caso seja por index (maioria inseriu fora do padrão de index)
            if (op.getCodigo() == 1 && codigo == 0) 
                tipo = op;
            
            //Caso nao
            else if (op.getCodigo() == codigo) 
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
