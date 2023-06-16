package br.unipar.central.services;

import br.unipar.central.exceptions.CampoExcedidoException;
import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.exceptions.EntidadeNaoInformadaException;
import br.unipar.central.models.PessoaJuridica;

public class PessoaJuridicaService {
    
    public static void validar(PessoaJuridica pessoajuridica) throws 
            EntidadeNaoInformadaException,
            CampoNaoInformadoException,
            CampoExcedidoException
            {
                        
        if(pessoajuridica == null){
            throw new EntidadeNaoInformadaException("É obrigatório que a pessoa jurídica seja válida!");
        }
            
        if(
            pessoajuridica.getRazaoSocial() == null || 
            pessoajuridica.getRazaoSocial().isEmpty() || 
            pessoajuridica.getRazaoSocial().isBlank()
        ){
            throw new CampoNaoInformadoException("Razao Social");
        }
        if(
            pessoajuridica.getCnpj() == null || 
            pessoajuridica.getCnpj().isEmpty() || 
            pessoajuridica.getCnpj().isBlank()
        ){
            throw new CampoNaoInformadoException("CNPJ");
        }
        
        if(
            pessoajuridica.getCnaePrincipal() == null || 
            pessoajuridica.getCnaePrincipal().isEmpty() || 
            pessoajuridica.getCnaePrincipal().isBlank()
        ){
            throw new CampoNaoInformadoException("Razao Social");
        }
        
        if(
            pessoajuridica.getFantasia() == null || 
            pessoajuridica.getFantasia().isEmpty() || 
            pessoajuridica.getFantasia().isBlank()
        ){
            throw new CampoNaoInformadoException("Fantasia");
        }
        

        
        if((pessoajuridica.getRazaoSocial().length() > 120)){
            throw new CampoExcedidoException("Razão Social", 120);
        }
        
        if((pessoajuridica.getCnpj().length() > 20)){
            throw new CampoExcedidoException("CNPJ", 20);
        }
        
        if((pessoajuridica.getCnaePrincipal().length() > 9)){
            throw new CampoExcedidoException("Cnae Principal", 9);
        }
        
        if((pessoajuridica.getFantasia().length() >120)){
            throw new CampoExcedidoException("Fantasia", 120);
        }
    }
}
