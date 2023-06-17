package br.unipar.central.userInterfaces;

import br.unipar.central.models.Agencia;
import br.unipar.central.models.Banco;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.AgenciaDAO;
import br.unipar.central.services.AgenciaService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class AgenciaUI {

    private static final AgenciaService agenciaservice = new AgenciaService(new AgenciaDAO());
    
    
    public static Agencia criarAgencia() {
            
        Agencia agencia = new Agencia();
        
        agencia.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma agencia"));
        agencia.setCodigo(JOptionPaneService.paneString("Insira um código", "Criar uma agencia"));
        agencia.setDigito(JOptionPaneService.paneString("Insira um digito", "Criar uma agencia"));
        agencia.setRazaoSocial(JOptionPaneService.paneString("Insira uma razaosocial", "Criar uma agencia"));
        agencia.setCnpj(JOptionPaneService.paneString("Insira um cnpj", "Criar uma agencia"));
        agencia.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma agencia"));
            
        String[] opcoes = {"Criar um novo", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Uma agencia deve ter um banco, como deseja vinculá-lo:",
               "Escolha de instanciação do banco",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Banco banco;
        
        if(escolha == 0){
            banco = BancoUI.criarBanco();
            BancoUI.insertBanco(banco);
        } else {
            banco = BancoUI.findBancoById();
        }
        
        agencia.setBanco(banco);
            
        return agencia;
    }
    
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteAgencia();
                break;
                
            case UPDATE:
                updateAgencia();
                break;
                
            case FIND_ALL:
                List<Agencia> lista = findAllAgencia();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Agencia agencia = findAgenciaById();
                
                if(agencia != null)
                    JOptionPane.showMessageDialog(null, "A seguinte agencia foi encontrada:\n" + agencia.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertAgencia(criarAgencia());
                break;
        }
    }
    
    
    public static void insertAgencia(Agencia agencia) {
        try{
            agenciaservice.insert(agencia);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Agencia findAgenciaById() {
        System.out.println(findAllAgencia().toString());
        JOptionPane.showMessageDialog(null, "As agencias disponiveis no banco de dados foram exibidas no console.", "Agencias encontradas", 1);
        
        int escolha = JOptionPaneService.paneInt("Insira o id da agencia:", "Escolha agencia");
        
        Agencia agencia = null;
        try {
            agencia = agenciaservice.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return agencia;
    }
    
    public static List<Agencia> findAllAgencia() {
        List<Agencia> agencias = null;
        
        try {
            agencias = agenciaservice.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return agencias;
    }
    
    public static void updateAgencia(){
        System.out.println(findAllAgencia().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar uma agencia será necessario um id de uma agencia que exista e as novas informações dos campos.\n"
                + "É possivel visualizar as agencia do banco de dados pelo console.", "Atualizar uma agencia", 1);
        Agencia agencia = criarAgencia();
        
        try {
            agenciaservice.update(agencia);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteAgencia(){
        System.out.println(findAllAgencia().toString());
        JOptionPane.showMessageDialog(null, "Para deletar uma agencia será necessario um id de uma agencia que já exista.\n"
                + "É possivel visualizar as agencia do banco de dados pelo console.", "Deletar uma agencia", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id da agencia:", "Escolha agencia");
        
        try {
            agenciaservice.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
