package br.unipar.central.userInterfaces;

import br.unipar.central.models.Agencia;
import br.unipar.central.models.Conta;
import br.unipar.central.models.Pessoa;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.ContaDAO;
import br.unipar.central.services.ContaService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class ContaUI {
    
    private static final ContaService contaservice = new ContaService(new ContaDAO());
    
    
    public static Conta criarConta() {
            
        Conta conta = new Conta();
        
        conta.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma conta"));
        conta.setNumero(JOptionPaneService.paneString("Insira um número de conta", "Criar uma conta"));
        conta.setDigito(JOptionPaneService.paneString("Insira um digito", "Criar uma conta"));
        conta.setSaldo(JOptionPaneService.paneBigDecimal("Insira um saldo", "Criar uma conta"));
        conta.setTipo(JOptionPaneService.paneTipoContaEnum());
        conta.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma conta"));
          
        
        String[] opcoes = {"Criar uma nova", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Uma conta deve ter uma agencia, como deseja vinculá-la:",
               "Escolha de instanciação da agencia",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Agencia agencia;
        
        if(escolha == 0){
            agencia = AgenciaUI.criarAgencia();
            AgenciaUI.insertAgencia(agencia);
        } else {
            agencia = AgenciaUI.findAgenciaById();
        }
        
        conta.setAgencia(agencia);

        
        escolha = JOptionPane.showOptionDialog(null, "Uma conta deve ter uma pessoa, como deseja vinculá-la:",
               "Escolha de instanciação da pessoa",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Pessoa pessoa;
        
        if(escolha == 0){
            pessoa = PessoaUI.criarPessoa();
            PessoaUI.insertPessoa(pessoa);
        } else {
            pessoa = PessoaUI.findPessoaById();
        }
        
        conta.setProprietario(pessoa);
            
        return conta;
    }
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteConta();
                break;
                
            case UPDATE:
                updateConta();
                break;
                
            case FIND_ALL:
                List<Conta> lista = findAllConta();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Conta conta = findContaById();
                
                if(conta != null)
                    JOptionPane.showMessageDialog(null, "A seguinte conta foi encontrada:\n" + conta.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertConta(criarConta());
                break;
        }
    }
    
    
    public static void insertConta(Conta conta) {
        try{
            contaservice.insert(conta);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Conta findContaById() {
        String[] opcoesPesquisa = {"Sim", "Não" };
        int escolhaPesquisa = JOptionPane.showOptionDialog(null, "Deseja exibir todos as linhas do banco de dados no console?\n"
                + "Será realizada uma busca individual para cada id das linhas da tabela relacionada. A busca é BEM demorada, gostaria de fazer mesmo assim?",
               "Escolher exibir linhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesPesquisa, opcoesPesquisa[1]);
        
        if(escolhaPesquisa == 0) {
        try {
            System.out.println(findAllConta().toString());
            JOptionPane.showMessageDialog(null, "As contas disponiveis no banco de dados foram exibidas no console.", "Contas encontradas", 1);
        } catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o comando Find All e printar no console. \n", "Erro ao realizar Find All", 0);
            }
        }
            
        int escolha = JOptionPaneService.paneInt("Insira o id da conta:", "Escolha conta");
        
        Conta conta = null;
        try {
            conta = contaservice.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return conta;
    }
    
    public static List<Conta> findAllConta() {
        List<Conta> contas = null;
        
        try {
            contas = contaservice.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return contas;
    }
    
    public static void updateConta(){
        System.out.println(findAllConta().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar uma conta será necessario um id de uma conta que exista e as novas informações dos campos.\n"
                + "É possivel visualizar as conta do banco de dados pelo console.", "Atualizar uma conta", 1);
        Conta conta = criarConta();
        
        try {
            contaservice.update(conta);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteConta(){
        System.out.println(findAllConta().toString());
        JOptionPane.showMessageDialog(null, "Para deletar uma conta será necessario um id de uma conta que já exista.\n"
                + "É possivel visualizar as conta do banco de dados pelo console.", "Deletar uma conta", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id da conta:", "Escolha conta");
        
        try {
            contaservice.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
