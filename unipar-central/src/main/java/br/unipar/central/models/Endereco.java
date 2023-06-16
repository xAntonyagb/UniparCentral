package br.unipar.central.models;

public class Endereco extends RegistroAcademico {
    private int id;
    private String longradouro;
    private int numero;
    private String bairro;
    private String cep;
    private String complemento;
    private Cidade cidade;
    private Pessoa pessoa;

    public Endereco() {
    }

    public Endereco(int id, String longradouro, int numero, String bairro, String cep, String complemento, Cidade cidade, Pessoa pessoa, String registroAcademico) {
        super(registroAcademico);
        this.id = id;
        this.longradouro = longradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.cidade = cidade;
        this.pessoa = pessoa;
    }

    

    public Pessoa getPessoa() {
        return pessoa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongradouro() {
        return longradouro;
    }

    public void setLongradouro(String longradouro) {
        this.longradouro = longradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Endereco{" + "id=" + id + ", longradouro=" + longradouro + ", numero=" + numero + ", bairro=" + bairro + ", cep=" + cep + ", complemento=" + complemento + ", cidade=" + cidade + ", pessoa=" + pessoa + '}';
    }
    
}
