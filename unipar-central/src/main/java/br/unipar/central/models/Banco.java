package br.unipar.central.models;

public class Banco extends RegistroAcademico {
    private int id;
    private String nome;

    public Banco(int id, String nome, String registroAcademico) {
        super(registroAcademico);
        this.id = id;
        this.nome = nome;
    }

    public Banco() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Banco{" + "id=" + id + ", nome=" + nome + '}';
    }
}
