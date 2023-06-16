package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Estado;
import br.unipar.central.repositories.EstadoDAO;
import br.unipar.central.repositories.PaisDAO;
import java.util.List;
import javax.swing.JOptionPane;

public class EstadoService {
    
    private final EstadoDAO estadoDAO;

    public EstadoService(EstadoDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
    }
    
    public static void validar(Estado estado) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
        
        PaisService paisServ = new PaisService(new PaisDAO());
        
        try{
            paisServ.validar(estado.getPais());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que um estado possua um país válido:\n" + e.getMessage());
        }
                
        if(estado == null){
            throw new EntidadeNaoInformadaException("É obrigatório que um estado seja válido!");
        }
            
        if(
            estado.getNome() == null || 
            estado.getNome().isEmpty() || 
            estado.getNome().isBlank()
        ){
            throw new CampoNaoInformadoException("Nome");
        }
        
        if(
            estado.getSigla() == null || 
            estado.getSigla().isEmpty() || 
            estado.getSigla().isBlank()
        ){
            throw new CampoNaoInformadoException("Sigla");
        }
        
        if(!(estado.getSigla().length() == 2)){
            throw new CampoExcedidoException("sigla", 2);
        }
        
        if(estado.getNome().length() > 60){
            throw new CampoExcedidoException("nome", 60);
        }
        
        if(estado.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
    }
    
    public List<Estado> findAll() throws ColunaNaoEncontradaException{
        List<Estado> resultado = null;
        
        try {
            resultado = estadoDAO.findAll();

            if(resultado == null)
                throw new ColunaNaoEncontradaException("Estado");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
        return resultado;
    }
    
    public Estado findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException {
        Estado retorno = null;
        
         try {
            if(id <= 0)
                throw new IdInvalidoException();

            retorno = estadoDAO.findById(id);

            if(retorno == null)
                throw new ColunaNaoEncontradaException("Estado");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         
        return retorno;
    }
     
    public void insert(Estado estado) {
        try {
            validar(estado);

            estadoDAO.insert(estado);

            JOptionPane.showMessageDialog(null, "Estado Inserido!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }
     
    public void update(Estado estado) {
        try {
            validar(estado);

            estadoDAO.update(estado);

            JOptionPane.showMessageDialog(null, "Estado atualizado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     
    public void delete(int id) {
        try {
            estadoDAO.delete(id);

            JOptionPane.showMessageDialog(null, "Estado deleteado!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
}
