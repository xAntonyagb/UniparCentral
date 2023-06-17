package br.unipar.central.userInterfaces;

import br.unipar.central.models.Pessoa;
import br.unipar.central.models.PessoaFisica;
import br.unipar.central.models.PessoaJuridica;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.PessoaDAO;
import br.unipar.central.services.JOptionPaneService;
import br.unipar.central.services.PessoaService;
import java.util.List;
import javax.swing.JOptionPane;

public class PessoaUI {
    
    private static final PessoaService pessoaService = new PessoaService(new PessoaDAO());
    
    
    public static Pessoa criarPessoa() {
        
        String[] opcoes = {"Física", "Juridica"};
        int escolha = JOptionPane.showOptionDialog(null, "Escolha que tipo de pessoa deseja Criar:",
               "Menu de operadoras",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        
        if(escolha == 0) {
            PessoaFisica fisica = new PessoaFisica();
            
            fisica.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma pessoa física"));
            fisica.setNome(JOptionPaneService.paneString("Insira um nome", "Criar uma pessoa física"));
            fisica.setDataNascimento(JOptionPaneService.paneTimestamp("Insira uma data de nascimento", "Criar uma pessoa física"));
            fisica.setCpf(JOptionPaneService.paneString("Insira um cpf", "Criar uma pessoa física"));
            fisica.setRg(JOptionPaneService.paneString("Insira um rg", "Criar uma pessoa física"));
            fisica.setEmail(JOptionPaneService.paneString("Insira um email", "Criar uma pessoa física"));
            fisica.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma pessoa física"));
            
            return fisica;
        } else {
            PessoaJuridica juridica = new PessoaJuridica();
            
            juridica.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma pessoa juridica"));
            juridica.setRazaoSocial(JOptionPaneService.paneString("Insira uma Razão social", "Criar uma pessoa juridica"));
            juridica.setFantasia(JOptionPaneService.paneString("Insira uma fantasia", "Criar uma pessoa juridica"));
            juridica.setCnpj(JOptionPaneService.paneString("Insira um CNPJ", "Criar uma pessoa juridica"));
            juridica.setCnaePrincipal(JOptionPaneService.paneString("Insira um Cnae Principal", "Criar uma pessoa juridica"));
            juridica.setEmail(JOptionPaneService.paneString("Insira um email", "Criar uma pessoa juridica"));
            juridica.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma pessoa juridica"));
            
            return juridica;
        }
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deletePessoa();
                break;
                
            case UPDATE:
                updatePessoa();
                break;
                
            case FIND_ALL:
                List<Pessoa> lista = findAllPessoa();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Pessoa pessoa = findPessoaById();
                
                if(pessoa != null)
                    JOptionPane.showMessageDialog(null, "A seguinte pessoa foi encontrada:\n" + pessoa.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertPessoa(criarPessoa());
                break;
        }
    }
    
    
    public static void insertPessoa(Pessoa pessoa) {
        try{
            pessoaService.insert(pessoa);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Pessoa findPessoaById() {
        System.out.println(findAllPessoa().toString());
        JOptionPane.showMessageDialog(null, "As pessoas disponiveis no banco de dados foram exibidas no console.", "Pessoas encontradas", 1);
        
        int escolha = JOptionPaneService.paneInt("Insira o id da pessoa:", "Escolha pessoa");
        
        Pessoa pessoa = null;
        try {
            pessoa = pessoaService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return pessoa;
    }
    
    public static List<Pessoa> findAllPessoa() {
        List<Pessoa> pessoas = null;
        
        try {
            pessoas = pessoaService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return pessoas;
    }
    
    public static void updatePessoa(){
        System.out.println(findAllPessoa().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar uma pessoa será necessario um id de uma pessoa que exista e as novas informações dos campos.\n"
                + "É possivel visualizar as pessoass do banco de dados pelo console.", "Atualizar uma pessoa", 1);
        Pessoa pessoa = criarPessoa();
        
        try {
            pessoaService.update(pessoa);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deletePessoa(){
        System.out.println(findAllPessoa().toString());
        JOptionPane.showMessageDialog(null, "Para deletar uma pessoa será necessario um id de uma pessoa existe.\n"
                + "É possivel visualizar as pessoass do banco de dados pelo console.", "Deletar uma pessoa", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id da pessoa:", "Escolha pessoa");
        
        try {
            pessoaService.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
    
}
