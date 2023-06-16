package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Agencia;
import br.unipar.central.services.BancoService;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgenciaDAO {
    private static final String INSERT = 
            "INSERT INTO agencia"
                + "(id, codigo, digito, razasocial, cnpj, ra, banco_id)"
            + " VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "codigo, "
                + "digito, "
                + "razasocial, "
                + "cnpj, "
                + "ra, "
                + "banco_id "
            + "FROM "
                + "agencia;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "id, "
                + "codigo, "
                + "digito, "
                + "razasocial, "
                + "cnpj, "
                + "ra, "
                + "banco_id "
            + "FROM "
                + "agencia "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "agencia "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "agencia "
            + "SET "
                + "codigo = ?, "
                + "digito = ?, "
                + "razasocial = ?, "
                + "cnpj = ?, "
                + "ra = ?, "
                + "banco_id = ? "
            + "WHERE "
                + "id = ?";
    
    
    private Connection conn = null;
    
    private Agencia agenciaInstance(ResultSet rs) throws SQLException  {
        BancoService bServ = new BancoService(new BancoDAO());
        return new Agencia(
                rs.getInt("id"),
                rs.getString("codigo"),
                rs.getString("digito"),
                rs.getString("razasocial"),
                rs.getString("cnpj"),
                bServ.findById(rs.getInt("banco_id")),
                rs.getString("ra")  
        );
    }
    
    public List<Agencia> findAll() {
        
        ArrayList<Agencia> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(agenciaInstance(rs));
            }
            
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
        return retorno;
    }
    
    public Agencia findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Agencia retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = agenciaInstance(rs);
            }
            
            return retorno;
            
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
    }
    
    public void insert(Agencia agencia) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);

            pstmt.setInt(1, agencia.getId());
            pstmt.setString(2, agencia.getCodigo());
            pstmt.setString(3, agencia.getDigito());
            pstmt.setString(4, agencia.getRazaoSocial());
            pstmt.setString(5, agencia.getCnpj());
            pstmt.setString(6, agencia.getRegistroAcademico());
            pstmt.setInt(7, agencia.getBanco().getId());
            
            pstmt.executeUpdate();
            conn.commit();
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            } catch (SQLException e1) {
                throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }
        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }
    
    public void update(Agencia agencia) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, agencia.getCodigo());
            pstmt.setString(2, agencia.getDigito());
            pstmt.setString(3, agencia.getRazaoSocial());
            pstmt.setString(4, agencia.getCnpj());
            pstmt.setString(5, agencia.getRegistroAcademico());
            pstmt.setInt(6, agencia.getBanco().getId());
            pstmt.setInt(7, agencia.getId());
            
            pstmt.executeUpdate();
            conn.commit();
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            } catch (SQLException e1) {
                throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }
        } finally{
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
    }
    
    public void delete(int id) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(DELETE_BY_ID);
            
            pstmt.setInt(1, id);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }
}
