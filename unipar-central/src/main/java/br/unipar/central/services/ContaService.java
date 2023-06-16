package br.unipar.central.services;

import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.TransferenciaZeradaException;
import br.unipar.central.models.Conta;
import br.unipar.central.utils.qtdDigitos;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

public class ContaService {
    
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
        
        if(qtdDigitos.qtdDigitosAntesVirgula(conta.getSaldo()) > 15){
            throw new CampoExcedidoException("Número de casas após a virgula do saldo de uma conta", 15);
        }
        
        if(qtdDigitos.qtdDigitosDepoisVirgula(conta.getSaldo()) > 2){
            throw new CampoExcedidoException("Número de casas antes a virgula do saldo de uma conta", 2);
        }
    }
}
