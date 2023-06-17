package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.exceptions.TransferenciaZeradaException;
import br.unipar.central.models.Conta;
import br.unipar.central.models.enums.TipoOperacaoEnum;
import br.unipar.central.repositories.ContaDAO;
import br.unipar.central.utils.QtdDigitosUtils;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;

public class ContaService {
    
    private final ContaDAO contaDAO;

    public ContaService(ContaDAO contaDAO) {
        this.contaDAO = contaDAO;
    }
    
    public static void validar(Conta conta) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException,
            TransferenciaZeradaException
            {
            
        try{
            AgenciaService.validar(conta.getAgencia());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que uma estado possua uma agencia válida:\n" + e.getMessage());
        }

        try{
            PessoaService.validar(conta.getProprietario());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que uma conta possua um proprietário válido:\n" + e.getMessage());
        }
        
                
        if(conta == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a conta seja válida!");
        }
            
        if(
            conta.getNumero()== null || 
            conta.getNumero().isEmpty() || 
            conta.getNumero().isBlank()
        ){
            throw new CampoNaoInformadoException("Numero da conta");
        }
        
        if(
            conta.getDigito()== null || 
            conta.getDigito().isEmpty() || 
            conta.getDigito().isBlank()
        ){
            throw new CampoNaoInformadoException("Digito da conta");
        }
        
        if(conta.getSaldo()== BigDecimal.ZERO){
            throw new TransferenciaZeradaException();
        }
        
        if(conta.getSaldo() == null){
            throw new CampoNaoInformadoException("Saldo");
        }
        
        if(conta.getTipo() == null){
            throw new CampoNaoInformadoException("Tipo da conta");
        }
        
        if(QtdDigitosUtils.qtdDigitosAntesVirgula(conta.getSaldo()) > 15){
            throw new CampoExcedidoException("Número de casas após a virgula do saldo de uma conta", 15);
        }
        
        if(QtdDigitosUtils.qtdDigitosDepoisVirgula(conta.getSaldo()) > 2){
            throw new CampoExcedidoException("Número de casas antes a virgula do saldo de uma conta", 2);
        }
    }
    
    public List<Conta> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Conta> resultado = contaDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Conta");

        return resultado;
    }
    
    public Conta findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Conta retorno = contaDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Conta");
         
        return retorno;
    }
     
    public void insert(Conta conta) throws BancoDeDadosException {
        validar(conta);

        contaDAO.insert(conta);

        JOptionPane.showMessageDialog(null, "Conta Inserida!");

     }
     
    public void update(Conta conta) throws BancoDeDadosException {
        validar(conta);

        contaDAO.update(conta);

        JOptionPane.showMessageDialog(null, "Conta atualizada!");
    }
     
    public void delete(int id) {
        contaDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Conta deleteada!");
    }
    
    public void atualizarSaldo(Conta conta, BigDecimal saldoAtualizar, TipoOperacaoEnum operacao) {
        switch (operacao) {
        case ENTRADA:
                conta.setSaldo(conta.getSaldo().add(saldoAtualizar));
                break;
        case SAIDA:
                conta.setSaldo(conta.getSaldo().subtract(saldoAtualizar));
                break;
        }
        
        update(conta);
    }
    
}
