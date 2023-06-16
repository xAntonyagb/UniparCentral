package br.unipar.central.models;

import br.unipar.central.models.enums.OperadorasEnum;

public class Telefone extends RegistroAcademico {
    private int id;
    private String numero;
    private OperadorasEnum operadora;
    private Agencia agencia;
    private Pessoa pessoa;

    public Telefone(int id, String numero, OperadorasEnum operadora, String registroAcademico, Agencia agencia, Pessoa pessoa) {
        super(registroAcademico);
        this.id = id;
        this.numero = numero;
        this.operadora = operadora;
        this.agencia = agencia;
        this.pessoa = pessoa;
    }

    public Telefone() {
    }

    
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public OperadorasEnum getOperadora() {
        return operadora;
    }

    public void setOperadora(OperadorasEnum operadora) {
        this.operadora = operadora;
    }

    @Override
    public String toString() {
        return "Telefone{" + "id=" + id + ", numero=" + numero + ", operadora=" + operadora + ", agencia=" + agencia + '}';
    }
    
}
