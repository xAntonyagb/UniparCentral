package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Banco;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BancoDAO {
    private static final String INSERT = 
            "INSERT INTO banco"
                + "(id, nome, ra)"
            + " VALUES "
                + "(?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "nome, "
                + "ra "
            + "FROM"
                + "banco;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "nome, "
                + "ra "
            + "FROM "
                + "banco "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "banco "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "banco "
            + "SET "
                + "nome = ?, "
                + "ra = ? "
            + "WHERE "
                + "id = ?";
    
    
    private Connection conn = null;
    
    private Banco bancoInstance(ResultSet rs) throws SQLException  {
        return new Banco(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("ra")  
        );
    }
    
    public List<Banco> findAll() {
        
        ArrayList<Banco> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(bancoInstance(rs));
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
    
    public Banco findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Banco retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = bancoInstance(rs);
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
    
    public void insert(Banco banco) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);

            pstmt.setInt(1, banco.getId());
            pstmt.setString(2, banco.getNome());
            pstmt.setString(3, banco.getRegistroAcademico());
            
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
    
    public void update(Banco banco) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, banco.getNome());
            pstmt.setString(2, banco.getRegistroAcademico());
            pstmt.setInt(3, banco.getId());
            
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