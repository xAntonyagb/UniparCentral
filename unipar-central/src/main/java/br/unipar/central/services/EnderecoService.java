package br.unipar.central.services;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.ColunaNaoEncontradaException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.exceptions.IdInvalidoException;
import br.unipar.central.models.Endereco;
import br.unipar.central.repositories.EnderecoDAO;
import static br.unipar.central.utils.qtdDigitos.qtdDigitos;
import java.util.List;
import javax.swing.JOptionPane;

public class EnderecoService {
    
    private final EnderecoDAO enderecoDAO;

    public EnderecoService(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }
    
    public static void validar(Endereco end) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {

        try{
            CidadeService.validar(end.getCidade());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a cidade seja válida!");
        }
                
        if(end == null){
            throw new EntidadeNaoInformadaException("É obrigatório que o endereço seja válido!");
        }
        
                try{
            PessoaService.validar(end.getPessoa());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "É obrigatório que a pessoa seja válida!");
        }
                
        if(end == null){
            throw new EntidadeNaoInformadaException("É obrigatório que o endereço seja válido!");
        }
            
        if(
            end.getLongradouro() == null || 
            end.getLongradouro().isEmpty() || 
            end.getLongradouro().isBlank()
        ){
            throw new CampoNaoInformadoException("Logradouro");
        }
        
        if(end.getNumero() == 0){ 
            throw new CampoNaoInformadoException("Número");
        }
        
        if(
            end.getBairro() == null || 
            end.getBairro().isEmpty() || 
            end.getBairro().isBlank()
        ){
            throw new CampoNaoInformadoException("Bairro");
        }    
        
        if(
            end.getCep() == null || 
            end.getCep().isEmpty() || 
            end.getCep().isBlank()
        ){
            throw new CampoNaoInformadoException("CEP");
        } 
         
        if(
            end.getComplemento() == null || 
            end.getComplemento().isEmpty() || 
            end.getComplemento().isBlank()
        ){
            throw new CampoNaoInformadoException("Complemento");
        }
        
        if(
            end.getRegistroAcademico() == null || 
            end.getRegistroAcademico().isEmpty() || 
            end.getRegistroAcademico().isBlank()
        ){
            throw new CampoNaoInformadoException("Registro Acadêmico");
        }
        
        if(end.getRegistroAcademico().length() > 8){
            throw new CampoExcedidoException("Registro Acadêmico", 8);
        }        
                
        if((end.getLongradouro().length() > 120)){
            throw new CampoExcedidoException("Logradouro", 120);
        }
        
        if(qtdDigitos(end.getNumero()) > 10){
            throw new CampoExcedidoException("Numero", 10);
        }
        
        if((end.getBairro().length() > 120)){
            throw new CampoExcedidoException("Bairro", 120);
        }
                
        if((end.getCep().length() > 10)){
            throw new CampoExcedidoException("CEP", 10);
        }
        if((end.getComplemento().length() > 20)){
            throw new CampoExcedidoException("Complemento", 20);
        }        
    }
    
    public List<Endereco> findAll() throws ColunaNaoEncontradaException, BancoDeDadosException {
        List<Endereco> resultado = enderecoDAO.findAll();

        if(resultado == null)
            throw new ColunaNaoEncontradaException("Endereco");

        return resultado;
    }
    
    
    public Endereco findById(int id) throws IdInvalidoException, ColunaNaoEncontradaException, BancoDeDadosException {
        if(id <= 0)
            throw new IdInvalidoException();

        Endereco retorno = enderecoDAO.findById(id);

        if(retorno == null)
            throw new ColunaNaoEncontradaException("Endereco");
         
        return retorno;
    }
     
    public void insert(Endereco endereco) throws BancoDeDadosException {
        validar(endereco);

        enderecoDAO.insert(endereco);

        JOptionPane.showMessageDialog(null, "Endereco Inserido!");
     }
     
    public void update(Endereco endereco) throws BancoDeDadosException {
        validar(endereco);

        enderecoDAO.update(endereco);

        JOptionPane.showMessageDialog(null, "Endereco atualizado!");
    }
     
    public void delete(int id) throws BancoDeDadosException {
        enderecoDAO.delete(id);

        JOptionPane.showMessageDialog(null, "Endereco deleteado!");
    }
}
