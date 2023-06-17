
package br.unipar.central.userInterfaces;

import br.unipar.central.models.Pais;
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
    
    public static void insertPais(Pais pais) {
        try{
            paisService.insert(pais);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel inserir o país no banco de dados: \n" + ex.getMessage(), "Erro na inserção", 0);
        }
    }
    
    public static Pais findPaisById() {
        System.out.println(findAllPaises().toString());
        JOptionPane.showMessageDialog(null, "Os paises disponiveis no banco de dados foram exibidos no console.", "Paises encontrados", 1);
        
        int escolha = JOptionPaneService.paneInt("Insira o id do país:", "Escolha de país");
        
        Pais pais = null;
        try {
            pais = paisService.findById(escolha);
            
            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel procurar pelo país no banco de dados: \n" + ex.getMessage(), "Erro ao procurar", 0);
        }
        
        return pais;
    }
    
    public static List<Pais> findAllPaises() {
        List<Pais> paises = null;
        
        try {
            paises = paisService.findAll();
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel procurar pelos países no banco de dados: \n" + ex.getMessage(), "Erro ao procurar", 0);
        }
        
        return paises;
    }
    
    
}
