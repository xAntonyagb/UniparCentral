package br.unipar.central.models;

public class Pessoa extends RegistroAcademico {
    private int id;
    private String email;

    public Pessoa() {
    }

    public Pessoa(int id, String email, String ra) {
        super(ra);
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", email=" + email + ", ra=" + super.getRegistroAcademico() +'}';
    }
    
}
