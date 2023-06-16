package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Pais;
import br.unipar.central.repositories.PaisDAO;
import java.util.List;
import javax.swing.JOptionPane;

public class PaisService {
    
    private final PaisDAO paisDAO;
    
    public PaisService(PaisDAO paisDAO) {
        this.paisDAO = paisDAO;
    }
    
    public static void validar(Pais pais) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
        
        if(pais == null){
            throw new EntidadeNaoInformadaException("É obrigatório que um país seja válido!");
        }
            
        if(
            pais.getNome() == null || 
            pais.getNome().isEmpty() || 
            pais.getNome().isBlank()
        ){
            throw new CampoNaoInformadoException("Nome");
        }
        
        if(
            pais.getSigla() == null || 
            pais.getSigla().isEmpty() || 
            pais.getSigla().isBlank()
        ){
            throw new CampoNaoInformadoException("Sigla");
        }
        
        if(!(pais.getSigla().length() == 2)){
            throw new CampoExcedidoException("sigla", 2);
        }
        
        if(pais.getNome().length() > 60){
            throw new CampoExcedidoException("nome", 120);
        }
        
        if(pais.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    public List<Pais> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Pais> resultado = paisDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Pais");

        
        return resultado;
    }
    
    public Pais findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Pais retorno = paisDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Pais");
         
        return retorno;
    }
     
    public void insert(Pais pais) throws BancoDeDadosException {
        validar(pais);

        paisDAO.insert(pais);

        JOptionPane.showMessageDialog(null, "Pais Inserido!");
     }
     
    public void update(Pais pais) throws BancoDeDadosException {
        validar(pais);

        paisDAO.update(pais);

        JOptionPane.showMessageDialog(null, "Pais atualizado!");
    }
     
    public void delete(int id) throws BancoDeDadosException {
        paisDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Pais deleteado!");
    }
}
