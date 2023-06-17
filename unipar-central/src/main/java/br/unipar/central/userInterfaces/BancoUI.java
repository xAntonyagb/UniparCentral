package br.unipar.central.userInterfaces;

import br.unipar.central.models.Banco;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.BancoDAO;
import br.unipar.central.services.BancoService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class BancoUI {
    
    private static final BancoService bancoService = new BancoService(new BancoDAO());
    
    public static Banco criarBanco() {
        Banco banco = new Banco();

        banco.setId(JOptionPaneService.paneInt("Insira um id", "Criar um banco"));
        banco.setNome(JOptionPaneService.paneString("Insira um nome", "Criar um banco"));
        banco.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar um banco"));

        return banco;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteBanco();
                break;
                
            case UPDATE:
                updateBanco();
                break;
                
            case FIND_ALL:
                List<Banco> lista = findAllBanco();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Banco banco = findBancoById();
                
                if(banco != null)
                    JOptionPane.showMessageDialog(null, "O seguinte banco foi encontrado:\n" + banco.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertBanco(criarBanco());
                break;
        }
    }
    
    public static void insertBanco(Banco banco) {
        try{
            bancoService.insert(banco);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Banco findBancoById() {
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllBanco().toString());
            JOptionPane.showMessageDialog(null, "Os bancos disponiveis no banco de dados foram exibidos no console.", "Bancoes encontrados", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
            
        int escolha = JOptionPaneService.paneInt("Insira o id do banco:", "Escolha de banco");
        
        Banco banco = null;
        try {
            banco = bancoService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return banco;
    }
    
    public static List<Banco> findAllBanco() {
        List<Banco> bancos = null;
        
        try {
            bancos = bancoService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return bancos;
    }
    
    public static void updateBanco(){
        System.out.println(findAllBanco().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar um banco será necessario um id de um banco que exista e as novas informações dos campos.\n"
                + "É possivel visualizar os bancos no banco de dados pelo console.", "Atualizar um banco", 1);
        Banco banco = criarBanco();
        
        try {
            bancoService.update(banco);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteBanco(){
        System.out.println(findAllBanco().toString());
        JOptionPane.showMessageDialog(null, "Para deletar um banco será necessario um id de um banco que exista.\n"
                + "É possivel visualizar os bancos no banco de dados pelo console.", "Deletar um banco", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id do banco:", "Escolha de banco");
        
        try {
            bancoService.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
}
