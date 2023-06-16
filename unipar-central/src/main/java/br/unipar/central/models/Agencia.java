package br.unipar.central.models;

public class Agencia extends RegistroAcademico{
    private int id;
    private String codigo;
    private String digito;
    private String razaoSocial;
    private String cnpj;
    private Banco banco;

    public Agencia(int id, String codigo, String digito, String razaoSocial, String cnpj, Banco banco, String registroAcademico) {
        super(registroAcademico);
        this.id = id;
        this.codigo = codigo;
        this.digito = digito;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.banco = banco;
    }

    public Agencia() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDigito() {
        return digito;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Override
    public String toString() {
        return "Agencia{" + "id=" + id + ", codigo=" + codigo + ", digito=" + digito + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", banco=" + banco + '}';
    }

    
}
