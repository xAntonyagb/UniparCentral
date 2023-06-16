package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Agencia;
import br.unipar.central.repositories.AgenciaDAO;
import javax.swing.JOptionPane;

public class AgenciaService {
    
    private final AgenciaDAO agenciaDAO;

    public AgenciaService(AgenciaDAO agenciaDAO) {
        this.agenciaDAO = agenciaDAO;
    }
    
    public static void validar(Agencia agencia) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
        
        try{
            BancoService.validar(agencia.getBanco());
        } catch (Exception e){
           JOptionPane.showMessageDialog(null, "É obrigatório que a agencia possua um banco válido:\n" + e.getMessage());
        }
        
        
                        
        if(agencia == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a agencia seja válida!");
        }
            
        if(
            agencia.getCnpj() == null || 
            agencia.getCnpj().isEmpty() || 
            agencia.getCnpj().isBlank()
        ){
            throw new CampoNaoInformadoException("CNPJ");
        }
        
        if(
            agencia.getCodigo() == null || 
            agencia.getCodigo().isEmpty() || 
            agencia.getCodigo().isBlank()
        ){
            throw new CampoNaoInformadoException("Código");
        }
        
        if(
            agencia.getRazaoSocial() == null || 
            agencia.getRazaoSocial().isEmpty() || 
            agencia.getRazaoSocial().isBlank()
        ){
            throw new CampoNaoInformadoException("Razão Social");
        }
        
        if(
            agencia.getDigito() == null || 
            agencia.getDigito().isEmpty() || 
            agencia.getDigito().isBlank()
        ){
            throw new CampoNaoInformadoException("Digito");
        }
    }
        
    public Agencia findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException {
        Agencia retorno = null;
        
         try {
            if(id <= 0)
                throw new IdInvalidoException();

            retorno = agenciaDAO.findById(id);

            if(retorno == null)
                throw new ColunaNaoEncontradaException("Agencia");

        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
         
        return retorno;
    }
     
    public void insert(Agencia agencia) {
        try {
            validar(agencia);

            agenciaDAO.insert(agencia);

            JOptionPane.showMessageDialog(null, "Agencia inserida!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
     }
     
    public void update(Agencia agencia) {
        try {
            validar(agencia);

            agenciaDAO.update(agencia);

            JOptionPane.showMessageDialog(null, "Agencia atualizada!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
     
    public void delete(int id) {
        try {
            agenciaDAO.delete(id);

            JOptionPane.showMessageDialog(null, "Agencia deleteada!");
        } catch (BancoDeDadosException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

