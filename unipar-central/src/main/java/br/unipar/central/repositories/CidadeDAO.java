package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Cidade;
import br.unipar.central.services.EstadoService;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CidadeDAO {
    private static final String INSERT = "INSERT INTO cidade(id, nome, ra, estado_id)"
            + " VALUES (?, ?, ?, ?);";
    
    private static final String FIND_ALL = "SELECT id, nome, ra, estado_id FROM cidade;";
    
    private static final String FIND_BY_ID = "SELECT id, nome, ra, estado_id FROM cidade "
             + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = "DELETE FROM cidade "
            + "WHERE id = ?";
    
    private static final String UPDATE = "UPDATE cidade SET nome = ?, ra = ?, estado_id = ? WHERE id = ?";
    
    
    private Connection conn = null;
    
    private Cidade cidadeInstace(ResultSet rs) throws SQLException {
        EstadoService eServ = new EstadoService(new EstadoDAO());
        return new Cidade(
                rs.getInt("id"),
                rs.getString("nome"),
                eServ.findById(rs.getInt("estado_id")),
                rs.getString("ra")
        );
    }
    
    public List<Cidade> findAll() {
        ArrayList<Cidade> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            while (rs.next()){
                retorno.add(cidadeInstace(rs));
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
    
    public Cidade findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cidade retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = cidadeInstace(rs);
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
    
    public void insert(Cidade cidade) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(INSERT);
            
            pstmt.setInt(1, cidade.getId());
            pstmt.setString(2, cidade.getNome());
            pstmt.setString(3, cidade.getRegistroAcademico());
            pstmt.setInt(4, cidade.getEstado().getId());
            
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
    
    public void update(Cidade cidade) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, cidade.getNome());
            pstmt.setString(2, cidade.getRegistroAcademico());
            pstmt.setInt(3, cidade.getEstado().getId());
            pstmt.setInt(4, cidade.getId());
            
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
