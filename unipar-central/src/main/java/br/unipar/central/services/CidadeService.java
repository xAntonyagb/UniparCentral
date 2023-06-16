package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Cidade;
import br.unipar.central.repositories.CidadeDAO;
import java.util.List;
import javax.swing.JOptionPane;

public class CidadeService {
    
    private final CidadeDAO cidadeDAO;

    public CidadeService(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    
    public static void validar(Cidade cidade) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
        
        try{
            EstadoService.validar(cidade.getEstado());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que uma cidade possua um estado válido:\n" + e.getMessage());
        }
                
        if(cidade == null){
            throw new EntidadeNaoInformadaException("É obrigatório que uma cidade seja válido!");
        }
            
        if(
            cidade.getNome() == null || 
            cidade.getNome().isEmpty() || 
            cidade.getNome().isBlank()
        ){
            throw new CampoNaoInformadoException("Nome");
        }
        
        if(cidade.getNome().length() > 60){
            throw new CampoExcedidoException("nome", 60);
        }
        
        if(cidade.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    public List<Cidade> findAll() throws ColunaNaoEncontradaException{
        List<Cidade> resultado = null;
        
        try {
            resultado = cidadeDAO.findAll();

            if(resultado == null)
                throw new ColunaNaoEncontradaException("Cidade");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return resultado;
    }
    
    public Cidade findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException {
        Cidade retorno = null;
        
         try {
            if(id <= 0)
                throw new IdInvalidoException();

            retorno = cidadeDAO.findById(id);

            if(retorno == null)
                throw new ColunaNaoEncontradaException("Cidade");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         
        return retorno;
    }
     
    public void insert(Cidade cidade) {
        try {
            validar(cidade);

            cidadeDAO.insert(cidade);

            JOptionPane.showMessageDialog(null, "Cidade Inserida!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }
     
    public void update(Cidade cidade) {
        try {
            validar(cidade);

            cidadeDAO.update(cidade);

            JOptionPane.showMessageDialog(null, "Cidade atualizada!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     
    public void delete(int id) {
        try {
            cidadeDAO.delete(id);

            JOptionPane.showMessageDialog(null, "Cidade deleteada!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
