package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Telefone;
import br.unipar.central.repositories.TelefoneDAO;
import java.util.List;
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
    
    public List<Telefone> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Telefone> resultado = telefoneDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Pessoa");
        
        return resultado;
    }
    
    public Telefone findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Telefone retorno = telefoneDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Telefone");

        return retorno;
    }
     
    public void insert(Telefone telefone) throws BancoDeDadosException {
        validar(telefone);

        telefoneDAO.insert(telefone);

        JOptionPane.showMessageDialog(null, "Telefone inserido!");
     }
     
    public void update(Telefone telefone) throws BancoDeDadosException {
        validar(telefone);

        telefoneDAO.update(telefone);

        JOptionPane.showMessageDialog(null, "Telefone atualizado!");

    }
     
    public void delete(int id) throws BancoDeDadosException {
        telefoneDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Telefone deleteado!");
    }
}
