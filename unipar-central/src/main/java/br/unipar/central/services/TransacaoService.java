package br.unipar.central.services;

import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.TransferenciaZeradaException;
import br.unipar.central.models.Transacao;
import br.unipar.central.utils.qtdDigitos;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class TransacaoService {
    
    public static void validar(Transacao transacao) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException,
            TransferenciaZeradaException
            {
        
        try{
            ContaService.validar(transacao.getContaRecebendo());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a conta que está recebendo seja válida:\n" + e.getMessage());
        }
        
        try{
            ContaService.validar(transacao.getContaTransferindo());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a conta que está transeferindo seja válida:\n" + e.getMessage());
        }
                        
        if(transacao == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a transação seja válida!");
        }
            
        if(transacao.getTipo() == null ){
            throw new CampoNaoInformadoException("Tipo");
        }
        if(
            transacao.getData_hora() == null){
            throw new CampoNaoInformadoException("Data_hora");
        }
        
        if(transacao.getValor() == BigDecimal.ZERO){
            throw new TransferenciaZeradaException();
        }
        
        if(transacao.getValor() == null){
            throw new CampoNaoInformadoException("Valor");
        }
        
        if(
            transacao.getRegistroAcademico() == null || 
            transacao.getRegistroAcademico().isEmpty() || 
            transacao.getRegistroAcademico().isBlank()
        ){
            throw new CampoNaoInformadoException("Registro academico");
        }
        
        if(transacao.getContaTransferindo() == null) {
            throw new CampoNaoInformadoException("Conta Transferindo");
        }
        
        if(
            transacao.getContaRecebendo() == null){
            throw new CampoNaoInformadoException("Conta Recebendo");
        }
        
                
        if(qtdDigitos.qtdDigitosAntesVirgula(transacao.getValor()) > 15){
            throw new CampoExcedidoException("Número de casas após a virgula do valor de uma transferencia", 15);
        }
        
        if(qtdDigitos.qtdDigitosDepoisVirgula(transacao.getValor()) > 2){
            throw new CampoExcedidoException("Número de casas antes a virgula do valor de uma transferencia", 2);
        }

        if(transacao.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
}
