
package br.unipar.central.userInterfaces;

import br.unipar.central.models.Pais;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.PaisDAO;
import br.unipar.central.services.JOptionPaneService;
import br.unipar.central.services.PaisService;
import java.util.List;
import javax.swing.JOptionPane;

public class PaisUI {
    
    private static final PaisService paisService = new PaisService(new PaisDAO());
    
    public static Pais criarPais() {
        Pais pais = new Pais();

        pais.setId(JOptionPaneService.paneInt("Insira um id", "Criar um país"));
        pais.setNome(JOptionPaneService.paneString("Insira um nome", "Criar um país"));
        pais.setSigla(JOptionPaneService.paneString("Insira uma sigla", "Criar um país"));
        pais.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar um país"));

        return pais;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deletePais();
                break;
                
            case UPDATE:
                updatePais();
                break;
                
            case FIND_ALL:
                List<Pais> lista = findAllPais();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Pais pais = findPaisById();
                
                if(pais != null)
                    JOptionPane.showMessageDialog(null, "O seguinte país foi encontrado:\n" + pais.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertPais(criarPais());
                break;
        }
    }
    
    public static void insertPais(Pais pais) {
        try{
            paisService.insert(pais);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Pais findPaisById() {
        
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllPais().toString());
            JOptionPane.showMessageDialog(null, "Os paises disponiveis no banco de dados foram exibidos no console.", "Paises encontrados", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
            
        int escolha = JOptionPaneService.paneInt("Insira o id do país:", "Escolha de país");
        
        Pais pais = null;
        try {
            pais = paisService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return pais;
    }
    
    public static List<Pais> findAllPais() {
        List<Pais> paises = null;
        
        try {
            paises = paisService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return paises;
    }
    
    public static void updatePais(){
        System.out.println(findAllPais().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar um país será necessario um id de um país que exista e as novas informações dos campos.\n"
                + "É possivel visualizar os países do banco de dados pelo console.", "Atualizar um país", 1);
        Pais pais = criarPais();
        
        try {
            paisService.update(pais);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deletePais(){
        System.out.println(findAllPais().toString());
        JOptionPane.showMessageDialog(null, "Para deletar um país será necessario um id de um país que exista.\n"
                + "É possivel visualizar os países do banco de dados pelo console.", "Deletar um país", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id do país:", "Escolha de país");
        
        try {
            paisService.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
