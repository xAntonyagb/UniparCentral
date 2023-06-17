package br.unipar.central.services;

import br.unipar.central.exceptions.CampoNaoInformadoException;
import br.unipar.central.models.enums.OperadorasEnum;
import br.unipar.central.models.enums.TipoContaEnum;
import br.unipar.central.models.enums.TipoTransacaoEnum;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class JOptionPaneService {
    
    public static int paneInt(String msg, String titulo){
        int outputI;
        
        try{
            outputI = Integer.parseInt(JOptionPane.showInputDialog(null, msg, titulo, 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Input inválido, tente inserir outro número.", titulo, 0);
            outputI = paneInt(msg, titulo);
        }
        
        return outputI;
    }
    
    public static String paneString(String msg, String titulo) {
        String output;
        
        try{
            output = JOptionPane.showInputDialog(null, msg, titulo, 1);
            
            if(
                output == null || 
                output.isEmpty() || 
                output.isBlank()
            ){
                throw new CampoNaoInformadoException("JOptionPane");
            }
            
            
        } catch (CampoNaoInformadoException ex) {
            JOptionPane.showMessageDialog(null, "Input inválido, insira novamente.", titulo, 0);
            output = paneString(msg, titulo);
        }
        
        return output;
    }
    
    public static BigDecimal paneBigDecimal(String msg, String titulo) {
        BigDecimal output;
        
        try{
            output = new BigDecimal(JOptionPane.showInputDialog(null, msg, titulo, 1));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Input inválido, tente inserir outro número.", titulo, 0);
            output = paneBigDecimal(msg, titulo);
        }
        
        return output;
    }
    
    
    public static Timestamp paneTimestamp(String msg, String titulo) {
        Timestamp output = null;
        
        try{
            String outputString = paneString(msg+"\nInsira nesse formato somente: dd-MM-yyyy", titulo);
            
            DateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date stringFormatada = formatoData.parse(outputString);
            
            output = new Timestamp(stringFormatada.getTime());
           
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Data inválida, tente inserir novamente conforme a sintaxe.", titulo, 0);
            output = paneTimestamp(msg, titulo);
        }
        
        return output; 
    } 
    
    
     public static OperadorasEnum paneOperadorasEnum() {
        int tamnhoEnum = OperadorasEnum.values().length;
        String[] opcoes = new String[tamnhoEnum];

        for(int i = 0; i < tamnhoEnum; i++) {
            opcoes[i] = OperadorasEnum.values()[i].getDescricao();
        }

        int escolha = JOptionPane.showOptionDialog(null, "Escolha a operadora desejada:",
                "Menu de operadoras",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        return OperadorasEnum.values()[escolha];
     }
     
     
     
     public static TipoContaEnum paneTipoContaEnum() {
        int tamnhoEnum = TipoContaEnum.values().length;
        String[] opcoes = new String[tamnhoEnum];

        for(int i = 0; i < tamnhoEnum; i++) {
            opcoes[i] = TipoContaEnum.values()[i].getDescricao();
        }

        int escolha = JOptionPane.showOptionDialog(null, "Escolha o tipo de conta desejada:",
                "Menu de tipos de conta",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        return TipoContaEnum.values()[escolha];
     }
     
     
     
     public static TipoTransacaoEnum paneTipoTransacaoEnum() {
        int tamnhoEnum = TipoTransacaoEnum.values().length;
        String[] opcoes = new String[tamnhoEnum];

        for(int i = 0; i < tamnhoEnum; i++) {
            opcoes[i] = TipoTransacaoEnum.values()[i].getDescricao();
        }

        int escolha = JOptionPane.showOptionDialog(null, "Escolha como deseja transferir:",
                "Menu transferencias",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        return TipoTransacaoEnum.values()[escolha];
     }
}
