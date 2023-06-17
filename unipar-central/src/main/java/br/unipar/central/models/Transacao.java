package br.unipar.central.models;

import br.unipar.central.models.enums.TipoTransacaoEnum;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transacao extends RegistroAcademico {
    private int id;
    private Timestamp data_hora;
    private BigDecimal valor;
    private TipoTransacaoEnum tipo;
    private Conta contaOrigem;
    private Conta contaDestino;

    public Transacao() {
    }

    public Transacao(int id, Timestamp data_hora, BigDecimal valor, TipoTransacaoEnum tipo, String registroAcademico, Conta contaOrigem, Conta contaDestino) {
        super(registroAcademico);
        this.id = id;
        this.data_hora = data_hora;
        this.valor = valor;
        this.tipo = tipo;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoTransacaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacaoEnum tipo) {
        this.tipo = tipo;
    }

    public Timestamp getData_hora() {
        return data_hora;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    @Override
    public String toString() {
        return "Transacao{" + "id=" + id + ", data_hora=" + data_hora + ", valor=" + valor + ", tipo=" + tipo + ", contaOrigem=" + contaOrigem + ", contaDestino=" + contaDestino + '}';
    }

    
}
