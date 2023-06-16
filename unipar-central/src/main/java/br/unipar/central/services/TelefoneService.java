package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Telefone;
import br.unipar.central.repositories.TelefoneDAO;
import javax.swing.JOptionPane;

public class TelefoneService {
    
    private final TelefoneDAO telefoneDAO;

    public TelefoneService(TelefoneDAO telefoneDAO) {
        this.telefoneDAO = telefoneDAO;
    }
    
    public static void validar(Telefone telefone) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
            
        if(telefone == null){
            throw new EntidadeNaoInformadaException("É obrigatório que o telefone seja válida!");
        }
            
        if(
            telefone.getNumero() == null || 
            telefone.getNumero().isEmpty() || 
            telefone.getNumero().isBlank()
        ){
            throw new CampoNaoInformadoException("Numero");
        }
        if(
            telefone.getRegistroAcademico() == null || 
            telefone.getRegistroAcademico().isEmpty() || 
            telefone.getRegistroAcademico().isBlank()
        ){
            throw new CampoNaoInformadoException("Registro academico");
        }
        
        if(telefone.getOperadora() == null){
            throw new CampoNaoInformadoException("Operadora");
        }
        
        if((telefone.getNumero().length() > 20)){
            throw new CampoExcedidoException("Numero", 120);
        }

        if(telefone.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    
    public Telefone findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException {
        Telefone retorno = null;
        
         try {
            if(id <= 0)
                throw new IdInvalidoException();

            retorno = telefoneDAO.findById(id);

            if(retorno == null)
                throw new ColunaNaoEncontradaException("Telefone");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         
        return retorno;
    }
     
    public void insert(Telefone telefone) {
        try {
            validar(telefone);

            telefoneDAO.insert(telefone);

            JOptionPane.showMessageDialog(null, "Telefone inserido!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }
     
    public void update(Telefone telefone) {
        try {
            validar(telefone);

            telefoneDAO.update(telefone);

            JOptionPane.showMessageDialog(null, "Telefone atualizado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     
    public void delete(int id) {
        try {
            telefoneDAO.delete(id);

            JOptionPane.showMessageDialog(null, "Telefone deleteado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
