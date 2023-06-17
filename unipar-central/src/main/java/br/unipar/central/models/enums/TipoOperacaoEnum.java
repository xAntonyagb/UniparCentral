package br.unipar.central.models.enums;

public enum TipoOperacaoEnum {
    ENTRADA(1, "Operação de entrada"),
    SAIDA(2, "Operação de saída");
	
    private Integer codigo;
    private String descricao;

    private TipoOperacaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
