package br.unipar.central.models;

import java.sql.Timestamp;

public class PessoaFisica extends Pessoa {
    private Timestamp dataNascimento;
    private String nome;
    private String cpf;
    private String rg;

    public PessoaFisica() {
    }

    public PessoaFisica(Timestamp dataNascimento, String nome, String cpf, String rg, int id, String email, String ra) {
        super(id, email, ra);
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
    }

    

    public Timestamp getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Timestamp dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Override
    public String toString() {
        return super.toString() + "PessoaFisica{" + "dataNascimento=" + dataNascimento + ", nome=" + nome + ", cpf=" + cpf + ", rg=" + rg + '}';
    }

}
