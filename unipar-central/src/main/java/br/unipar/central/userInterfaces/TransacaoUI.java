package br.unipar.central.userInterfaces;

import br.unipar.central.models.Conta;
import br.unipar.central.models.Transacao;
import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.repositories.TransacaoDAO;
import br.unipar.central.services.TransacaoService;
import br.unipar.central.services.JOptionPaneService;
import java.util.List;
import javax.swing.JOptionPane;

public class TransacaoUI {
    
    private static final TransacaoService transaçõeservice = new TransacaoService(new TransacaoDAO());
    
    
    public static Transacao criarTransacao() {
            
        Transacao transacao = new Transacao();
        
        transacao.setId(JOptionPaneService.paneInt("Insira um id", "Criar uma transacao"));
        transacao.setData_hora(JOptionPaneService.paneTimestamp("Insira a data da transação", "Criar uma transacao"));
        transacao.setValor(JOptionPaneService.paneBigDecimal("Insira um valor", "Criar uma transacao"));
        transacao.setTipo(JOptionPaneService.paneTipoTransacaoEnum());
        transacao.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar uma transacao"));
          
        
        String[] opcoes = {"Criar uma nova", "Procurar no BD"};
        
        int escolha = JOptionPane.showOptionDialog(null, "Uma transacao deve ter uma conta de origem, como deseja vinculá-la:",
               "Escolha de instanciação da conta",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        Conta conta;
        
        if(escolha == 0){
            conta = ContaUI.criarConta();
            ContaUI.insertConta(conta);
        } else {
            conta = ContaUI.findContaById();
        }
        
        transacao.setContaOrigem(conta);
        
        escolha = JOptionPane.showOptionDialog(null, "Uma transacao deve ter uma conta de destino, como deseja vinculá-la:",
               "Escolha de instanciação da conta",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[1]);
        
        if(escolha == 0){
            conta = ContaUI.criarConta();
            ContaUI.insertConta(conta);
        } else {
            conta = ContaUI.findContaById();
        }
        
        transacao.setContaDestino(conta);

        return transacao;
    }
    
    
    public static void menuExecucoes(EscolhasUIEnum escolha) {
        switch (escolha) {
            case DELETE:
                deleteTransacao();
                break;
                
            case UPDATE:
                updateTransacao();
                break;
                
            case FIND_ALL:
                List<Transacao> lista = findAllTransacao();
                
                if(lista != null){
                    System.out.println("\n\nResultado Find All:\n" + lista.toString());
                    JOptionPane.showMessageDialog(null, "O resultado da pesquisa foi printado no console", "Resultado Find All", 1);
                }
                break;
            
            case FIND_BY_ID:
                Transacao transacao = findTransacaoById();
                
                if(transacao != null)
                    JOptionPane.showMessageDialog(null, "A seguinte transação foi encontrada:\n" + transacao.toString(), "Resultado Find By Id", 1);
                break;
                
            case INSERT:
                insertTransacao(criarTransacao());
                break;
        }
    }
    
    
    public static void insertTransacao(Transacao transacao) {
        try{
            transaçõeservice.insert(transacao);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a adição no banco de dados: \n" + ex.getMessage(), "Erro ao adicionar", 0);
        }
    }
    
    public static Transacao findTransacaoById() {
        System.out.println(findAllTransacao().toString());
        JOptionPane.showMessageDialog(null, "As transações disponiveis no banco de dados foram exibidas no console.", "Transações encontradas", 1);
        
        int escolha = JOptionPaneService.paneInt("Insira o id da transacao:", "Escolha transacao");
        
        Transacao transacao = null;
        try {
            transacao = transaçõeservice.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return transacao;
    }
    
    public static List<Transacao> findAllTransacao() {
        List<Transacao> transações = null;
        
        try {
            transações = transaçõeservice.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a pesquisa no banco de dados: \n" + ex.getMessage(), "Erro ao pesquisar", 0);
        }
        
        return transações;
    }
    
    public static void updateTransacao(){
        System.out.println(findAllTransacao().toString());
        JOptionPane.showMessageDialog(null, "Para atualizar uma transacao será necessario um id de uma transacao que exista e as novas informações dos campos.\n"
                + "É possivel visualizar as transacao do banco de dados pelo console.", "Atualizar uma transacao", 1);
        Transacao transacao = criarTransacao();
        
        try {
            transaçõeservice.update(transacao);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização no banco de dados: \n" + ex.getMessage(), "Erro ao atualizar", 0);
        }
    }
    
    public static void deleteTransacao(){
        System.out.println(findAllTransacao().toString());
        JOptionPane.showMessageDialog(null, "Para deletar uma transacao será necessario um id de uma transacao que já exista.\n"
                + "É possivel visualizar as transacao do banco de dados pelo console.", "Deletar uma transacao", 1);
        int escolha = JOptionPaneService.paneInt("Insira o id da transacao:", "Escolha transacao");
        
        try {
            transaçõeservice.delete(escolha);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a deleção no banco de dados: \n" + ex.getMessage(), "Erro ao deletar", 0);
        }
    }
    
}
