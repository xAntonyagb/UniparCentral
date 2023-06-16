package br.unipar.central.models;

public class Pais extends RegistroAcademico {
    private int id;
    private String nome;
    private String sigla;

    public Pais() {
    }

    public Pais(int id, String nome, String sigla, String ra) {
        super(ra);
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "Pais{" + "id=" + id + ", nome=" + nome + ", sigla=" + sigla + ", ra=" + super.getRegistroAcademico() + '}';
    }
    
    
}
