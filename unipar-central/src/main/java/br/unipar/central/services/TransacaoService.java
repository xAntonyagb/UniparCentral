package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.exceptions.TransferenciaZeradaException;
import br.unipar.central.models.Transacao;
import br.unipar.central.models.enums.TipoOperacaoEnum;
import br.unipar.central.repositories.ContaDAO;
import br.unipar.central.repositories.TransacaoDAO;
import br.unipar.central.utils.QtdDigitosUtils;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;

public class TransacaoService {
    
    private final TransacaoDAO transacaoDAO;

    public TransacaoService(TransacaoDAO transacaoDAO) {
        this.transacaoDAO = transacaoDAO;
    }
    
    public static void validar(Transacao transacao) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException,
            TransferenciaZeradaException
            {
        
        try{
            ContaService.validar(transacao.getContaDestino());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a conta de destino seja válida:\n" + e.getMessage());
        }
        
        try{
            ContaService.validar(transacao.getContaOrigem());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a conta de origem seja válida:\n" + e.getMessage());
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
        
        if(transacao.getContaOrigem()== null) {
            throw new CampoNaoInformadoException("Conta de Origem");
        }
        
        if(
            transacao.getContaDestino()== null){
            throw new CampoNaoInformadoException("Conta de Destino");
        }
        
                
        if(QtdDigitosUtils.qtdDigitosAntesVirgula(transacao.getValor()) > 15){
            throw new CampoExcedidoException("Número de casas após a virgula do valor de uma transferencia", 15);
        }
        
        if(QtdDigitosUtils.qtdDigitosDepoisVirgula(transacao.getValor()) > 2){
            throw new CampoExcedidoException("Número de casas antes a virgula do valor de uma transferencia", 2);
        }

        if(transacao.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    
    public List<Transacao> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Transacao> resultado = transacaoDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Transacao");

        return resultado;
    }
    
    public Transacao findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Transacao retorno = transacaoDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Transacao");
         
        return retorno;
    }
     
    public void insert(Transacao transacao) throws BancoDeDadosException {
        validar(transacao);

        transacaoDAO.insert(transacao);
        
        ContaService contaService = new ContaService(new ContaDAO());
        contaService.atualizarSaldo(transacao.getContaOrigem(), transacao.getValor(), TipoOperacaoEnum.SAIDA);
        contaService.atualizarSaldo(transacao.getContaDestino(), transacao.getValor(), TipoOperacaoEnum.ENTRADA);

        JOptionPane.showMessageDialog(null, "Transacao Inserida!");

     }
     
    public void update(Transacao transacao) throws BancoDeDadosException {
        validar(transacao);

        transacaoDAO.update(transacao);

        JOptionPane.showMessageDialog(null, "Transacao atualizada!");
    }
     
    public void delete(int id) {
        transacaoDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Transacao deleteada!");
    }
}
