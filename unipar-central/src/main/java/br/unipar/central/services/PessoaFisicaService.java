package br.unipar.central.services;

import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.models.PessoaFisica;

public class PessoaFisicaService {
    
    public static void validar(PessoaFisica pessoafisica) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
                
        if(pessoafisica == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a pessoa fisica seja válida!");
        }
            
        if(pessoafisica.getDataNascimento() == null){
            throw new CampoNaoInformadoException("Data de nascimento");
        }
        if(
            pessoafisica.getNome() == null || 
            pessoafisica.getNome().isEmpty() || 
            pessoafisica.getNome().isBlank()
        ){
            throw new CampoNaoInformadoException("Nome");
        }
        
        if(
            pessoafisica.getCpf() == null || 
            pessoafisica.getCpf().isEmpty() || 
            pessoafisica.getCpf().isBlank()
        ){
            throw new CampoNaoInformadoException("CPF");
        }
        
        if(
            pessoafisica.getRg() == null || 
            pessoafisica.getRg().isEmpty() || 
            pessoafisica.getRg().isBlank()
        ){
            throw new CampoNaoInformadoException("RG");
        }

        
        if((pessoafisica.getNome().length() > 120)){
            throw new CampoExcedidoException("Nome", 120);
        }
        
        if((pessoafisica.getCpf().length() > 20)){
            throw new CampoExcedidoException("CPF", 20);
        }
        
        if((pessoafisica.getRg().length() > 20)){
            throw new CampoExcedidoException("RG", 20);
        }
    }
}
