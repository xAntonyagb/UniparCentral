package br.unipar.central.userInterfaces;

import br.unipar.central.models.Cidade;
import br.unipar.central.models.Endereco;
import br.unipar.central.models.Pessoa;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.EnderecoDAO;
import br.unipar.central.services.EnderecoService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class EnderecoUI {
   
    private static final EnderecoService endereçoservice = new EnderecoService(new EnderecoDAO());
    
    public static Endereco criarEndereco() {
            
        Endereco endereco = new Endereco();
        
        endereco.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma endereço"));
        endereco.setLogradouro(JOptionPaneService.paneString("Insira um logradouro", "Criar uma endereço"));
        endereco.setBairro(JOptionPaneService.paneString("Insira um bairro", "Criar uma endereço"));
        endereco.setNumero(JOptionPaneService.paneInt("Insira o número da casa", "Criar uma endereço"));
        endereco.setCep(JOptionPaneService.paneString("Insira um CEP", "Criar uma endereço"));
        endereco.setComplemento(JOptionPaneService.paneString("Insira um complemento", "Criar uma endereço"));
        endereco.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma endereço"));
            
        String[] opcoes = {"Criar uma nova", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Uma endereço deve ter uma cidade, como deseja vinculá-la:",
               "Escolha de instanciação da cidade",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Cidade cidade;
        
        if(escolha == 0){
            cidade = CidadeUI.criarCidade();
            CidadeUI.insertCidade(cidade);
        } else {
            cidade = CidadeUI.findCidadeById();
        }
        
        endereco.setCidade(cidade);
        
        escolha = JOptionPane.showOptionDialog(null, "Uma endereço deve ter uma pessoa, como deseja vinculá-la:",
               "Escolha de instanciação da pessoa",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Pessoa pessoa;
        
        if(escolha == 0){
            pessoa = PessoaUI.criarPessoa();
            PessoaUI.insertPessoa(pessoa);
        } else {
            pessoa = PessoaUI.findPessoaById();
        }
        
        endereco.setPessoa(pessoa);
            
        return endereco;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteEndereco();
                break;
                
            case UPDATE:
                updateEndereco();
                break;
                
            case FIND_ALL:
                List<Endereco> lista = findAllEndereco();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Endereco end = findEnderecoById();
                
                if(end != null)
                    JOptionPane.showMessageDialog(null, "O seguinte endereço foi encontrado:\n" + end.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertEndereco(criarEndereco());
                break;
        }
    }
    
    
    public static void insertEndereco(Endereco endereco) {
        try{
            endereçoservice.insert(endereco);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Endereco findEnderecoById() {
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllEndereco().toString());
            JOptionPane.showMessageDialog(null, "Os endereços disponiveis no banco de dados foram exibidos no console.", "Enderecos encontrados", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
            
        int escolha = JOptionPaneService.paneInt("Insira o id do endereço:", "Escolha endereço");
        
        Endereco endereco = null;
        try {
            endereco = endereçoservice.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return endereco;
    }
    
    public static List<Endereco> findAllEndereco() {
        List<Endereco> endereços = null;
        
        try {
            endereços = endereçoservice.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return endereços;
    }
    
    public static void updateEndereco(){
        System.out.println(findAllEndereco().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar um endereço será necessario um id de um endereço que exista e as novas informações dos campos.\n"
                + "É possivel visualizar os endereços do banco de dados pelo console.", "Atualizar um endereço", 1);
        Endereco endereco = criarEndereco();
        
        try {
            endereçoservice.update(endereco);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteEndereco(){
        System.out.println(findAllEndereco().toString());
        JOptionPane.showMessageDialog(null, "Para deletar um endereço será necessario um id de um endereço que já exista.\n"
                + "É possivel visualizar os enderecos do banco de dados pelo console.", "Deletar um endereco", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id do endereco:", "Escolha endereco");
        
        try {
            endereçoservice.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
