package br.unipar.central.models;

public abstract class RegistroAcademico {
    private String registroAcademico;

    public RegistroAcademico(String registroAcademico) {
        this.registroAcademico = registroAcademico;
    }

    public RegistroAcademico() {
    }

    public String getRegistroAcademico() {
        return registroAcademico;
    }

    public void setRegistroAcademico(String registroAcademico) {
        this.registroAcademico = registroAcademico;
    }

    @Override
    public String toString() {
        return "RegistroAcademico{" + "registroAcademico=" + registroAcademico + '}';
    }
    
    
}
