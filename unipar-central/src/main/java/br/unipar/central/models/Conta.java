package br.unipar.central.models;

import br.unipar.central.models.enums.TipoContaEnum;
import java.math.BigDecimal;

public class Conta extends RegistroAcademico{
    
    private String numero;
    private String digito;
    private BigDecimal saldo;
    private TipoContaEnum tipo;
    private Agencia agencia;
    private Pessoa proprietario;

    public Conta(String numero, String digito, BigDecimal saldo, TipoContaEnum tipo, Agencia agencia, Pessoa proprietario, String registroAcademico) {
        super(registroAcademico);
        this.numero = numero;
        this.digito = digito;
        this.saldo = saldo;
        this.tipo = tipo;
        this.agencia = agencia;
        this.proprietario = proprietario;
    }

    public Conta() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDigito() {
        return digito;
    }

    public void setDigito(String digito) {
        this.digito = digito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public TipoContaEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoContaEnum tipo) {
        this.tipo = tipo;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Pessoa getProprietario() {
        return proprietario;
    }

    public void setProprietario(Pessoa proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Conta{" + "numero=" + numero + ", digito=" + digito + ", saldo=" + saldo + ", tipo=" + tipo + ", agencia=" + agencia + ", proprietario=" + proprietario + '}';
    }
}
