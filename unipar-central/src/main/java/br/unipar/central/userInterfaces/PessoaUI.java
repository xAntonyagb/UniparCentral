package br.unipar.central.userInterfaces;

import br.unipar.central.models.Pessoa;
import br.unipar.central.models.PessoaFisica;
import br.unipar.central.models.PessoaJuridica;
import br.unipar.central.services.JOptionPaneService;
import javax.swing.JOptionPane;

public class PessoaUI {
    
    public static Pessoa criarPessoa() {
        
        String[] opcoes = {"Física", "Juridica"};
        int escolha = JOptionPane.showOptionDialog(null, "Escolha que tipo de pessoa deseja Criar:",
               "Menu de operadoras",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        
        if(escolha == 0) {
            PessoaFisica fisica = new PessoaFisica();

            fisica.setNome(JOptionPaneService.paneString("Insira um nome", "Criar uma pessoa física"));
            fisica.setDataNascimento(JOptionPaneService.paneTimestamp("Insira uma data de nascimento", "Criar uma pessoa física"));
            fisica.setCpf(JOptionPaneService.paneString("Insira um cpf", "Criar uma pessoa física"));
            fisica.setRg(JOptionPaneService.paneString("Insira um rg", "Criar uma pessoa física"));
            fisica.setEmail(JOptionPaneService.paneString("Insira um email", "Criar uma pessoa física"));
            fisica.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma pessoa física"));
            fisica.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma pessoa física"));
            
            return fisica;
        } else {
            PessoaJuridica juridica = new PessoaJuridica();
            
            juridica.setRazaoSocial(JOptionPaneService.paneString("Insira uma Razão social", "Criar uma pessoa juridica"));
            juridica.setFantasia(JOptionPaneService.paneString("Insira uma fantasia", "Criar uma pessoa juridica"));
            juridica.setCnpj(JOptionPaneService.paneString("Insira um CNPJ", "Criar uma pessoa juridica"));
            juridica.setCnaePrincipal(JOptionPaneService.paneString("Insira um Cnae Principal", "Criar uma pessoa juridica"));
            juridica.setEmail(JOptionPaneService.paneString("Insira um email", "Criar uma pessoa juridica"));
            juridica.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma pessoa juridica"));
            juridica.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma pessoa juridica"));
            
            return juridica;
        }
    }
    
    
}
