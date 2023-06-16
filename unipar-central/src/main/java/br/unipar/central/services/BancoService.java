package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Banco;
import br.unipar.central.repositories.BancoDAO;
import java.util.List;
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
    
    public List<Banco> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Banco> resultado = bancoDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Banco");
        
        return resultado;
    }
    
    public Banco findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Banco retorno = bancoDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Banco");

        return retorno;
    }
     
    public void insert(Banco banco) throws BancoDeDadosException {
        validar(banco);

        bancoDAO.insert(banco);

        JOptionPane.showMessageDialog(null, "Banco inserido!");
     }
     
    public void update(Banco banco) throws BancoDeDadosException {
        validar(banco);

        bancoDAO.update(banco);

        JOptionPane.showMessageDialog(null, "Banco atualizado!");
    }
     
    public void delete(int id) throws BancoDeDadosException {
        bancoDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Banco deleteado!");
    }
}
