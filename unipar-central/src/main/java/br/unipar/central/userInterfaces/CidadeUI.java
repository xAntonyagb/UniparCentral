package br.unipar.central.userInterfaces;

import br.unipar.central.models.Cidade;
import br.unipar.central.models.Estado;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.CidadeDAO;
import br.unipar.central.services.CidadeService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class CidadeUI {
    
    private static final CidadeService cidadeservice = new CidadeService(new CidadeDAO());
    
    public static Cidade criarCidade() {
            
        Cidade cidade = new Cidade();
        
        cidade.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma cidade"));
        cidade.setNome(JOptionPaneService.paneString("Insira um nome", "Criar uma cidade"));
        cidade.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma cidade"));
            
        String[] opcoes = {"Criar um novo", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Uma cidade deve ter um estado, como deseja vinculá-lo:",
               "Escolha de instanciação do estado",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Estado estado;
        
        if(escolha == 0){
            estado = EstadoUI.criarEstado();
            EstadoUI.insertEstado(estado);
        } else {
            estado = EstadoUI.findEstadoById();
        }
        
        cidade.setEstado(estado);
            
        return cidade;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteCidade();
                break;
                
            case UPDATE:
                updateCidade();
                break;
                
            case FIND_ALL:
                List<Cidade> lista = findAllCidade();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Cidade cidade = findCidadeById();
                
                if(cidade != null)
                    JOptionPane.showMessageDialog(null, "A seguinte cidade foi encontrada:\n" + cidade.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertCidade(criarCidade());
                break;
        }
    }
    
    public static void insertCidade(Cidade cidade) {
        try{
            cidadeservice.insert(cidade);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Cidade findCidadeById() {
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllCidade().toString());
            JOptionPane.showMessageDialog(null, "As cidades disponiveis no banco de dados foram exibidas no console.", "Cidades encontradas", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
        
        int escolha = JOptionPaneService.paneInt("Insira o id da cidade:", "Escolha cidade");
        
        Cidade cidade = null;
        try {
            cidade = cidadeservice.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return cidade;
    }
    
    public static List<Cidade> findAllCidade() {
        List<Cidade> cidades = null;
        
        try {
            cidades = cidadeservice.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return cidades;
    }
    
    public static void updateCidade(){
        System.out.println(findAllCidade().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar uma cidade será necessario um id de uma cidade que exista e as novas informações dos campos.\n"
                + "É possivel visualizar as cidade do banco de dados pelo console.", "Atualizar uma cidade", 1);
        Cidade cidade = criarCidade();
        
        try {
            cidadeservice.update(cidade);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteCidade(){
        System.out.println(findAllCidade().toString());
        JOptionPane.showMessageDialog(null, "Para deletar uma cidade será necessario um id de uma cidade que já exista.\n"
                + "É possivel visualizar as cidade do banco de dados pelo console.", "Deletar uma cidade", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id da cidade:", "Escolha cidade");
        
        try {
            cidadeservice.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
