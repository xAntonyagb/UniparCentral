package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Pessoa;
import br.unipar.central.models.PessoaFisica;
import br.unipar.central.models.PessoaJuridica;
import br.unipar.central.repositories.PessoaDAO;
import java.util.List;
import javax.swing.JOptionPane;

public class PessoaService {
    
    private final PessoaDAO pessoaDAO;

    public PessoaService(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }
    
    public static void validar(Pessoa pessoa) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
                        
        if(pessoa == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a pessoa seja válida!");
        }
            
        if(
            pessoa.getEmail() == null || 
            pessoa.getEmail().isEmpty() || 
            pessoa.getEmail().isBlank()
        ){
            throw new CampoNaoInformadoException("E-mail");
        }
        
        if(
            pessoa.getRegistroAcademico() == null || 
            pessoa.getRegistroAcademico().isEmpty() || 
            pessoa.getRegistroAcademico().isBlank()
        ){
            throw new CampoNaoInformadoException("Registro Acadêmico");
        }

        if((pessoa.getEmail().length() > 120)){
            throw new CampoExcedidoException("email", 120);
        }
        
        if(pessoa.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }
        
        
        //Conferindo as instancias da pessoa
        if(pessoa instanceof PessoaFisica pessoaFisica) {
            try{
                PessoaFisicaService.validar(pessoaFisica);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "É obrigatório que uma pessoa física cumpra com as validações:\n" + ex.getMessage());
            }
            
        } else if(pessoa instanceof PessoaJuridica pessoaJuridica) {
            try{
                PessoaJuridicaService.validar(pessoaJuridica);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "É obrigatório que uma pessoa juridica cumpra com as validações:\n" + ex.getMessage());
            }
        }
    }
        
        
    public List<Pessoa> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Pessoa> resultado = pessoaDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Pessoa");

        
        return resultado;
    }
    
    public Pessoa findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Pessoa retorno = pessoaDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Pessoa");

        return retorno;
    }
     
    public void insert(Pessoa pessoa) throws BancoDeDadosException {
        validar(pessoa);

        pessoaDAO.insert(pessoa);

        JOptionPane.showMessageDialog(null, "Pessoa Inserida!");
     }
     
    public void update(Pessoa pessoa) throws BancoDeDadosException {
        validar(pessoa);

        pessoaDAO.update(pessoa);

        JOptionPane.showMessageDialog(null, "Pessoa atualizada!");
    }
     
    public void delete(int id) throws BancoDeDadosException {
        pessoaDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Pessoa deleteada!");
    }
    
}

