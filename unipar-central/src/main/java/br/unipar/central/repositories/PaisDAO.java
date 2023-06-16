package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Pais;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaisDAO {
    
    private static final String INSERT = "INSERT INTO pais(id, nome, sigla, ra)"
            + " VALUES (?, ?, ?, ?);";
    
    private static final String FIND_ALL = "SELECT id, nome, sigla, ra FROM pais;";
    
    private static final String FIND_BY_ID = "SELECT id, nome, sigla, ra FROM pais "
             + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = "DELETE FROM pais "
            + "WHERE id = ?";
    
    private static final String UPDATE = "UPDATE pais SET nome = ?, sigla = ?, ra = ?  WHERE id = ?";
    
    
    private Connection conn = null;
    
    private Pais paisInstance(ResultSet rs) throws SQLException  {
        return new Pais(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("sigla"),
                rs.getString("ra")
        );
    }
    
    public List<Pais> findAll() {
        
        ArrayList<Pais> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(paisInstance(rs));
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
    
    public Pais findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Pais retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = paisInstance(rs);
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
    
    public void insert(Pais pais) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);
            
            pstmt.setInt(1, pais.getId());
            pstmt.setString(2, pais.getNome());
            pstmt.setString(3, pais.getSigla());
            pstmt.setString(4, pais.getRegistroAcademico());
            
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
    
    public void update(Pais pais) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, pais.getNome());
            pstmt.setString(2, pais.getSigla());
            pstmt.setString(3,pais.getRegistroAcademico());
            pstmt.setInt(4, pais.getId());
            
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
