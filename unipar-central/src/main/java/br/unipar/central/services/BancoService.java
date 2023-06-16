package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Banco;
import br.unipar.central.repositories.BancoDAO;
import javax.swing.JOptionPane;

public class BancoService {
    
    private final BancoDAO bancoDAO;

    public BancoService(BancoDAO bancoDAO) {
        this.bancoDAO = bancoDAO;
    }
    
    public static void validar(Banco banco) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
                  
        if(banco == null){
            throw new EntidadeNaoInformadaException("É obrigatório queo banco seja válido!");
        }
            
        if(
            banco.getNome()== null || 
            banco.getNome().isEmpty() || 
            banco.getNome().isBlank()
        ){
            throw new CampoNaoInformadoException("Nome");
        }
        
        if(banco.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    
    public Banco findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException {
        Banco retorno = null;
        
         try {
            if(id <= 0)
                throw new IdInvalidoException();

            retorno = bancoDAO.findById(id);

            if(retorno == null)
                throw new ColunaNaoEncontradaException("Banco");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         
        return retorno;
    }
     
    public void insert(Banco banco) {
        try {
            validar(banco);

            bancoDAO.insert(banco);

            JOptionPane.showMessageDialog(null, "Banco inserido!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }
     
    public void update(Banco banco) {
        try {
            validar(banco);

            bancoDAO.update(banco);

            JOptionPane.showMessageDialog(null, "Banco atualizado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     
    public void delete(int id) {
        try {
            bancoDAO.delete(id);

            JOptionPane.showMessageDialog(null, "Banco deleteado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
