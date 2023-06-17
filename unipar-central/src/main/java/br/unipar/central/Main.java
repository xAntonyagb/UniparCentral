package br.unipar.central;

import br.unipar.central.models.enums.OperadorasEnum;

public class Main {

    public static void main(String[] args) {
        
        
        
        
        System.out.println(OperadorasEnum.values()[1].ordinal());
        
//        
//        
//        //Executar testes do service
//        
//        
//        PaisService paisService = new PaisService(new PaisDAO());
//        Pais pais = new Pais(0, "Brasil", "BR", "00230848");
//        
//        //Pais
//        try {
//            paisService.validar(pais);
//        } catch(EntidadeNaoInformadaException | 
//                CampoExcedidoException | 
//                CampoNaoInformadoException | 
//                Error e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e){
//            System.out.println("Exceção não esperada: " + e.getMessage());
//        }
//        
//        
//        EstadoService estadoService = new EstadoService();
//        Estado estado = new Estado(0, "Parana", "PR", pais, "00230848");
//        
//        //Estado
//        try {
//            estadoService.validar(estado);
//        } catch(EntidadeNaoInformadaException | 
//                CampoExcedidoException | 
//                CampoNaoInformadoException | 
//                Error e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e){
//            System.out.println("Exceção não esperada: " + e.getMessage());
//        }
//        
//        
//        CidadeService cidadeService = new CidadeService();
//        Cidade cidade = new Cidade(0, "Toledo", "TOO", estado);
//        
//        //Cidade
//        try {
//            cidadeService.validar(cidade);
//        } catch(EntidadeNaoInformadaException | 
//                CampoExcedidoException | 
//                CampoNaoInformadoException | 
//                Error e) {
//            System.out.println(e.getMessage());
//        } catch (Exception e){
//            System.out.println("Exceção não esperada: " + e.getMessage());
//        }
//        
//        
//        
//        
//        
//        
//        
//        
////Metodos do service para db        
//
//        
//        
////Metodo findAll na db dentro de paisService
//        try{
//            List<Pais> paises = paisService.findAll();
//            
//            System.out.println(paises.toString());
//        } catch (Exception ex){
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        
//        
//        
////Metodo findByID na db dentro de paisService
//        try {
//            Pais resultado = paisService.FindById(1058);
//            System.out.println(resultado.toString());
//            
//        } catch(Exception ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        
//        
////Metodo Insert na db dentro de paisService
//        try {
//            Pais pais2 = new Pais();
//            pais.setId(525257);
//            pais.setNome("Pais Novo");
//            pais.setSigla("PN");
//            pais.setRegistroAcademico("17221");
//            
//            paisService.insert(pais2);
//        } catch (Exception ex) {
//            System.out.println("insert");
//            System.out.println(ex.getMessage());
//        }
//        
//        
////Metodo update na db dentro de paisService
//
//        try {
//            Pais pais3 = new Pais();
//            pais.setId(525257);
//            pais.setNome("Pais Novo2");
//            pais.setSigla("PN");
//            pais.setRegistroAcademico("17221");
//            
//            paisService.update(pais3);
//        } catch (Exception ex) {
//            System.out.println("update");
//            System.out.println(ex.getMessage());
//        }
//        
    }
}
