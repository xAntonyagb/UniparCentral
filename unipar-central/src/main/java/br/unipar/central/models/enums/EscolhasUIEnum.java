package br.unipar.central.models.enums;

public enum EscolhasUIEnum {
    FIND_ALL("Find All", 0),
    FIND_BY_ID("Find By Id", 1),
    INSERT("Insert", 2),
    UPDATE("Update", 3),
    DELETE("Delete", 4);
    
    String descricao;
    int num;

    private EscolhasUIEnum(String descricao, int num) {
        this.descricao = descricao;
        this.num = num;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNum() {
        return num;
    }
    
    public static EscolhasUIEnum paraEnum(int num){
        EscolhasUIEnum tipo = null;

        for (EscolhasUIEnum escolhasUI : EscolhasUIEnum.values()) {
            if (escolhasUI.getNum() == num) 
                tipo = escolhasUI;
        }

        return tipo;
    }
}
