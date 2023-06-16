package br.unipar.central.models;

import br.unipar.central.models.enums.TipoTransacaoEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao extends RegistroAcademico {
    private int id;
    private TipoTransacaoEnum tipo;
    private LocalDateTime data_hora;
    private BigDecimal valor;
    private String ra;
    private Conta contaTransferindo;
    private Conta contaRecebendo;

    public Transacao() {
    }

    public Transacao(int id, TipoTransacaoEnum tipo, LocalDateTime data_hora, BigDecimal valor, String ra, Conta contaTransferindo, Conta contaRecebendo) {
        this.id = id;
        this.tipo = tipo;
        this.data_hora = data_hora;
        this.valor = valor;
        this.ra = ra;
        this.contaTransferindo = contaTransferindo;
        this.contaRecebendo = contaRecebendo;
    }
    
    
//    public void RealizarTransferencia(BigDecimal valTransf){
//        BigDecimal saldoRecebendo = this.contaRecebendo.getSaldo();
//        saldoRecebendo = (saldoRecebendo).add(valTransf);
//                
//        BigDecimal saldoTransferindo = this.contaTransferindo.getSaldo();
//        saldoTransferindo = (saldoTransferindo).subtract(valTransf);
//        
//        this.contaRecebendo.setSaldo(saldoRecebendo);
//        this.contaTransferindo.setSaldo(saldoTransferindo);
//    }

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

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public Conta getContaTransferindo() {
        return contaTransferindo;
    }

    public void setContaTransferindo(Conta contaTransferindo) {
        this.contaTransferindo = contaTransferindo;
    }

    public Conta getContaRecebendo() {
        return contaRecebendo;
    }

    public void setContaRecebendo(Conta contaRecebendo) {
        this.contaRecebendo = contaRecebendo;
    }

    @Override
    public String toString() {
        return "Transacao{" + "id=" + id + ", tipo=" + tipo + ", data_hora=" + data_hora + ", valor=" + valor + ", ra=" + ra + ", contaTransferindo=" + contaTransferindo + ", contaRecebendo=" + contaRecebendo + '}';
    }
    
}
