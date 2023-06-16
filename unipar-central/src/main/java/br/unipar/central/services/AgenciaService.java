package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Agencia;
import br.unipar.central.repositories.AgenciaDAO;
import java.util.List;
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
    
    public List<Agencia> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Agencia> resultado = agenciaDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Agencia");
        
        return resultado;
    }
        
    public Agencia findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Agencia retorno = agenciaDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Agencia");

        return retorno;
    }
     
    public void insert(Agencia agencia) throws BancoDeDadosException {
        validar(agencia);

        agenciaDAO.insert(agencia);

        JOptionPane.showMessageDialog(null, "Agencia inserida!");
     }
     
    public void update(Agencia agencia) throws BancoDeDadosException {
        validar(agencia);

        agenciaDAO.update(agencia);

        JOptionPane.showMessageDialog(null, "Agencia atualizada!");
    }
     
    public void delete(int id) throws BancoDeDadosException {
        agenciaDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Agencia deleteada!");
    }
}

