package br.unipar.central;

import br.unipar.central.models.enums.EscolhasUIEnum;
import br.unipar.central.userInterfaces.*;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        int escolhaAcesso = 10;
        int escolhaAcoes = 0;
        
        do {
            
        String[] opcoesAcesso = {"Pais", "Estado", "Cidade", "Endereço", "Pessoa", "Telefone", "Banco", "Agencia", "Conta", "Transação", "Fechar sistema" };
        escolhaAcesso = JOptionPane.showOptionDialog(null, "Oque deseja acessar?",
               "Trabalho 2 Bimestre: Menu de escolhas",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesAcesso, opcoesAcesso[10]);
        
        if(escolhaAcesso != 10){
            String[] opcoesAcoes = {"Find All", "Find By Id", "Insert", "Update", "Delete" };
            escolhaAcoes = JOptionPane.showOptionDialog(null, "Oque deseja acessar?",
                   "Trabalho 2 Bimestre: Menu de ações",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesAcoes, opcoesAcoes[0]);
        }
        
        
        switch(escolhaAcesso){
            case 0 -> PaisUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 1 -> EstadoUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 2 -> CidadeUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 3 -> EnderecoUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 4 -> PessoaUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 5 -> TelefoneUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 6 -> BancoUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 7 -> AgenciaUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 8 -> ContaUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
                
            case 9 -> TransacaoUI.menuExecucoes(EscolhasUIEnum.paraEnum(escolhaAcoes));
        }
           
        } while(escolhaAcesso != 10);
        
    }
}
