package br.unipar.central.userInterfaces;

import br.unipar.central.models.Estado;
import br.unipar.central.models.Pais;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.EstadoDAO;
import br.unipar.central.services.EstadoService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class EstadoUI {
    
    private static final EstadoService estadoService = new EstadoService(new EstadoDAO());
    
    public static Estado criarEstado() {
        Estado estado = new Estado();

        estado.setId(JOptionPaneService.paneInt("Insira um id", "Criar um país"));
        estado.setNome(JOptionPaneService.paneString("Insira um nome", "Criar um país"));
        estado.setSigla(JOptionPaneService.paneString("Insira uma sigla", "Criar um país"));
        estado.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar um país"));
        
        String[] opcoes = {"Criar um novo", "Procurar no BD"};
        int escolha = JOptionPane.showOptionDialog(null, "Um estado deve ter um pais, como deseja vinculá-lo:",
               "Escolha de instanciação de país",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Pais pais;
        
        if(escolha == 0){
            pais = PaisUI.criarPais();
            PaisUI.insertPais(pais);
        } else {
            pais = PaisUI.findPaisById();
        }
        
        estado.setPais(pais);

        return estado;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteEstado();
                break;
                
            case UPDATE:
                updateEstado();
                break;
                
            case FIND_ALL:
                List<Estado> lista = findAllEstado();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Estado estado = findEstadoById();
                
                if(estado != null)
                    JOptionPane.showMessageDialog(null, "O seguinte estado foi encontrado:\n" + estado.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertEstado(criarEstado());
                break;
        }
    }

    
    public static void insertEstado(Estado estado) {
        try{
            estadoService.insert(estado);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Estado findEstadoById() {
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllEstado().toString());
            JOptionPane.showMessageDialog(null, "Os estados disponiveis no banco de dados foram exibidos no console.", "Estados encontrados", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
            
        int escolha = JOptionPaneService.paneInt("Insira o id do estado:", "Escolha de estado");
        
        Estado estado = null;
        try {
            estado = estadoService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return estado;
    }
    
    public static List<Estado> findAllEstado() {
        List<Estado> estados = null;
        
        try {
            estados = estadoService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return estados;
    }
    
    public static void updateEstado(){
        System.out.println(findAllEstado().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar um estado será necessario um id de um estado que exista e as novas informações dos campos.\n"
                + "É possivel visualizar os estados do banco de dados pelo console.", "Atualizar um estado", 1);
        Estado estado = criarEstado();
        
        try {
            estadoService.update(estado);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteEstado(){
        System.out.println(findAllEstado().toString());
        JOptionPane.showMessageDialog(null, "Para deletar um estado será necessario um id de um país existe.\n"
                + "É possivel visualizar os estados do banco de dados pelo console.", "Deletar um estado", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id do estado:", "Escolha de estado");
        
        try {
            estadoService.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
}
