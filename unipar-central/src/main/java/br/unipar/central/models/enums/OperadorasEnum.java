
package br.unipar.central.models.enums;

public enum OperadorasEnum {
    TIM(41),
    VIVO(15),
    CLARO(21),
    OI(31);
    
    int codigo;

    private OperadorasEnum(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

}
