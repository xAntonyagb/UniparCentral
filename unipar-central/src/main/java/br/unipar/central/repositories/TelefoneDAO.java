package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Telefone;
import br.unipar.central.models.enums.OperadorasEnum;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {
    
    private static final String INSERT = 
            "INSERT INTO telefone"
                + "(id, numero, operadora, ra, agencia_id, pessoa_id)"
            + " VALUES "
                + "(?, ?, ?, ?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "numero, "
                + "operadora, "
                + "ra, "
                + "agencia_id, "
                + "pessoa_id "
            + "FROM "
                + "telefone;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "id, "
                + "numero, "
                + "operadora, "
                + "ra, "
                + "agencia_id, "
                + "pessoa_id "
            + "FROM "
                + "telefone "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "telefone "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "telefone "
            + "SET "
                + "numero = ?, "
                + "operadora = ?, "
                + "ra = ?, "
                + "agencia_id = ?, "
                + "pessoa_id = ? "
            + "WHERE "
                + "id = ?";
    
    
    private Connection conn = null;
    
    private Telefone telefoneInstance(ResultSet rs) throws SQLException  {
        
        PessoaDAO pDAO = new PessoaDAO();
        AgenciaDAO aDAO = new AgenciaDAO();
        return new Telefone(
                rs.getInt("id"),
                rs.getString("numero"),
                OperadorasEnum.paraEnum(rs.getInt("operadora")),
                rs.getString("ra"),
                aDAO.findById(rs.getInt("agencia_id")),
                pDAO.findById(rs.getInt("pessoa_id"))
        );
    }
    
    public List<Telefone> findAll() {
        
        ArrayList<Telefone> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(telefoneInstance(rs));
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
    
    public Telefone findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Telefone retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = telefoneInstance(rs);
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
    
    public void insert(Telefone telefone) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);
            
            pstmt.setInt(1, telefone.getId());
            pstmt.setString(2, telefone.getNumero());
            pstmt.setInt(3, telefone.getOperadora().getCodigo());
            pstmt.setString(4, telefone.getRegistroAcademico());
            pstmt.setInt(5, telefone.getAgencia().getId());
            pstmt.setInt(6, telefone.getPessoa().getId());
            
            
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
    
    public void update(Telefone telefone) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, telefone.getNumero());
            pstmt.setInt(2, telefone.getOperadora().getCodigo());
            pstmt.setString(3, telefone.getRegistroAcademico());
            pstmt.setInt(4, telefone.getAgencia().getId());
            pstmt.setInt(5, telefone.getPessoa().getId());
            pstmt.setInt(6, telefone.getId());
            
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
