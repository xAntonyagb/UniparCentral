package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Endereco;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO {
    private static final String INSERT = 
            "INSERT INTO endereco"
                + "(id, logradouro, numero, bairro, cep, complemento, ra, pessoa_id, cidade_id)"
            + " VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "logradouro, "
                + "numero, "
                + "bairro, "
                + "cep, "
                + "complemento, "
                + "ra, "
                + "pessoa_id, "
                + "cidade_id "
            + "FROM "
                + "endereco;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "id, "
                + "logradouro, "
                + "numero, "
                + "bairro, "
                + "cep, "
                + "complemento, "
                + "ra, "
                + "pessoa_id, "
                + "cidade_id "
            + "FROM "
                + "endereco "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "endereco "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "endereco "
            + "SET "
                + "logradouro = ?, "
                + "numero = ?, "
                + "bairro = ?, "
                + "cep = ?, "
                + "complemento = ?, "
                + "ra = ?, "
                + "pessoa_id = ?, "
                + "cidade_id = ? "
            + "WHERE "
                + "id = ?";
    
    
    private Connection conn = null;
    
    private Endereco enderecoInstance(ResultSet rs) throws SQLException  {
        
        PessoaDAO pDAO = new PessoaDAO();
        CidadeDAO cDAO = new CidadeDAO();
        
        return new Endereco(
                rs.getInt("id"),
                rs.getString("logradouro"),
                rs.getInt("numero"),
                rs.getString("bairro"),
                rs.getString("cep"),
                rs.getString("complemento"),
                cDAO.findById(rs.getInt("cidade_id")),
                pDAO.findById(rs.getInt("pessoa_id")),
                rs.getString("ra")
        );
    }
    
    public List<Endereco> findAll() {
        
        ArrayList<Endereco> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(enderecoInstance(rs));
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
    
    public Endereco findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Endereco retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = enderecoInstance(rs);
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
    
    public void insert(Endereco endereco) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);
            
            pstmt.setInt(1, endereco.getId());
            pstmt.setString(2, endereco.getLogradouro());
            pstmt.setInt(3, endereco.getNumero());
            pstmt.setString(4, endereco.getBairro());
            pstmt.setString(5, endereco.getCep());
            pstmt.setString(6, endereco.getComplemento());
            pstmt.setString(7, endereco.getRegistroAcademico());
            pstmt.setInt(8, endereco.getPessoa().getId());
            pstmt.setInt(9, endereco.getCidade().getId());
            
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
    
    public void update(Endereco endereco) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, endereco.getLogradouro());
            pstmt.setInt(2, endereco.getNumero());
            pstmt.setString(3, endereco.getBairro());
            pstmt.setString(4, endereco.getCep());
            pstmt.setString(5, endereco.getComplemento());
            pstmt.setString(6, endereco.getRegistroAcademico());
            pstmt.setInt(7, endereco.getPessoa().getId());
            pstmt.setInt(8, endereco.getCidade().getId());
            pstmt.setInt(9, endereco.getId());
            
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
