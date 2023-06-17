package br.unipar.central.userInterfaces;

import br.unipar.central.models.Agencia;
import br.unipar.central.models.Pessoa;
import br.unipar.central.models.Telefone;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.TelefoneDAO;
import br.unipar.central.services.JOptionPaneService;
import br.unipar.central.services.TelefoneService;
import java.util.List;
import javax.swing.JOptionPane;

public class TelefoneUI {
    
    private static final TelefoneService telefoneService = new TelefoneService(new TelefoneDAO());
    
    public static Telefone criarTelefone() {
        Telefone telefone = new Telefone();

        telefone.setId(JOptionPaneService.paneInt("Insira um id", "Criar um telefone"));
        telefone.setNumero(JOptionPaneService.paneString("Insira um número de telefone", "Criar um telefone"));
        telefone.setOperadora(JOptionPaneService.paneOperadorasEnum());
        telefone.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar um telefone"));

        
        String[] opcoes = {"Criar uma nova", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Um telefone deve ter uma agencia, como deseja vinculá-la:",
               "Escolha de instanciação da agencia",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Agencia agencia;
        
        if(escolha == 0){
            agencia = AgenciaUI.criarAgencia();
            AgenciaUI.insertAgencia(agencia);
        } else {
            agencia = AgenciaUI.findAgenciaById();
        }
        
        telefone.setAgencia(agencia);

        
        escolha = JOptionPane.showOptionDialog(null, "Um telefone deve ter uma pessoa, como deseja vinculá-la:",
               "Escolha de instanciação da pessoa",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Pessoa pessoa;
        
        if(escolha == 0){
            pessoa = PessoaUI.criarPessoa();
            PessoaUI.insertPessoa(pessoa);
        } else {
            pessoa = PessoaUI.findPessoaById();
        }
        
        telefone.setPessoa(pessoa);

        
        return telefone;
    }
    
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteTelefone();
                break;
                
            case UPDATE:
                updateTelefone();
                break;
                
            case FIND_ALL:
                List<Telefone> lista = findAllTelefone();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Telefone telefone = findTelefoneById();
                
                if(telefone != null)
                    JOptionPane.showMessageDialog(null, "O seguinte telefone foi encontrado:\n" + telefone.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertTelefone(criarTelefone());
                break;
        }
    }
    
    
    public static void insertTelefone(Telefone telefone) {
        try{
            telefoneService.insert(telefone);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Telefone findTelefoneById() {
        System.out.println(findAllTelefone().toString());
        JOptionPane.showMessageDialog(null, "Os telefones disponiveis no banco de dados foram exibidos no console.", "telefones encontrados", 1);
        
        int escolha = JOptionPaneService.paneInt("Insira o id do telefone:", "Escolha de telefone");
        
        Telefone telefone = null;
        try {
            telefone = telefoneService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return telefone;
    }
    
    public static List<Telefone> findAllTelefone() {
        List<Telefone> telefones = null;
        
        try {
            telefones = telefoneService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return telefones;
    }
    
    public static void updateTelefone(){
        System.out.println(findAllTelefone().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar um telefone será necessario um id de um telefone que exista e as novas informações dos campos.\n"
                + "É possivel visualizar os telefones do banco de dados pelo console.", "Atualizar um telefone", 1);
        Telefone telefone = criarTelefone();
        
        try {
            telefoneService.update(telefone);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteTelefone(){
        System.out.println(findAllTelefone().toString());
        JOptionPane.showMessageDialog(null, "Para deletar um telefone será necessario um id de um telefone que exista.\n"
                + "É possivel visualizar os telefones do banco de dados pelo console.", "Deletar um telefones", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id do telefones:", "Escolha de telefones");
        
        try {
            telefoneService.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
}
