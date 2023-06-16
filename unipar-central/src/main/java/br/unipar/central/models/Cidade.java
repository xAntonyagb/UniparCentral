package br.unipar.central.models;

public class Cidade extends RegistroAcademico {
    private int id;
    private String nome;
    private Estado estado;

    public Cidade() {
    }

    public Cidade(int id, String nome, Estado estado, String registroAcademico) {
        super(registroAcademico);
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }
    

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
        return "Cidade{" + "id=" + id + ", nome=" + nome + ", estado=" + estado + '}';
    }
}
