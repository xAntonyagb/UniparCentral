package br.unipar.central.userInterfaces;

import br.unipar.central.models.Estado;
import br.unipar.central.models.Pais;
import br.unipar.central.services.JOptionPaneService;
import javax.swing.JOptionPane;

public class EstadoUI {
    
    public static Estado criarEstado() {
        Estado estado = new Estado();

        estado.setId(JOptionPaneService.paneInt("Insira um id", "Criar um país"));
        estado.setNome(JOptionPaneService.paneString("Insira um nome", "Criar um país"));
        estado.setSigla(JOptionPaneService.paneString("Insira uma sigla", "Criar um país"));
        estado.setRegistroAcademico(JOptionPaneService.paneString("Insira um ra", "Criar um país"));
        
        String[] opcoes = {"Criar um novo", "Procurar no BD"};
        int escolha = JOptionPane.showOptionDialog(null, "Um estado deve ter um pais, como deseja vinculalo:",
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

}
