package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Conta;
import br.unipar.central.models.enums.TipoContaEnum;
import br.unipar.central.services.AgenciaService;
import br.unipar.central.services.PessoaService;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
    
    private static final String INSERT = 
            "INSERT INTO conta"
                + "(id, numero, digito, saldo, tipo, ra, agencia_id, pessoa_id)"
            + " VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "numero, "
                + "digito, "
                + "saldo, "
                + "tipo, "
                + "ra, "
                + "agencia_id, "
                + "pessoa_id "
            + "FROM "
                + "conta;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "id, "
                + "numero, "
                + "digito, "
                + "saldo, "
                + "tipo, "
                + "ra, "
                + "agencia_id, "
                + "pessoa_id "
            + "FROM "
                + "conta "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "conta "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "conta "
            + "SET "
                + "numero = ?, "
                + "digito = ?, "
                + "saldo = ?, "
                + "tipo = ?, "
                + "ra = ?, "
                + "agencia_id = ?, "
                + "pessoa_id = ? "
            + "WHERE "
                + "id = ?";
    
    
    
    private Connection conn = null;
    
    private Conta contaInstance(ResultSet rs) throws SQLException  {
        AgenciaService aServ = new AgenciaService(new AgenciaDAO());
        PessoaService pServ = new PessoaService(new PessoaDAO());
        
        return new Conta(
                rs.getInt("id"),
                rs.getString("numero"),
                rs.getString("digito"),
                rs.getBigDecimal("saldo"),
                TipoContaEnum.paraEnum(rs.getInt("tipo")),
                rs.getString("ra"),
                aServ.findById(rs.getInt("agencia_id")),
                pServ.findById(rs.getInt("pessoa_id"))
                 
        );
    }
    
    public List<Conta> findAll() {
        
        ArrayList<Conta> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(contaInstance(rs));
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
    
    public Conta findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Conta retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = contaInstance(rs);
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
    
    public void insert(Conta conta) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);

            pstmt.setInt(1, conta.getId());
            pstmt.setString(2, conta.getNumero());
            pstmt.setString(3, conta.getDigito());
            pstmt.setBigDecimal(4, conta.getSaldo());
            pstmt.setInt(5, conta.getTipo().getCodigo());
            pstmt.setString(6, conta.getRegistroAcademico());
            pstmt.setInt(7, conta.getAgencia().getId());
            pstmt.setInt(8, conta.getProprietario().getId());
            
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
    
    public void update(Conta conta) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            pstmt.setString(1, conta.getNumero());
            pstmt.setString(2, conta.getDigito());
            pstmt.setBigDecimal(3, conta.getSaldo());
            pstmt.setInt(4, conta.getTipo().getCodigo());
            pstmt.setString(5, conta.getRegistroAcademico());
            pstmt.setInt(6, conta.getAgencia().getId());
            pstmt.setInt(7, conta.getProprietario().getId());
            pstmt.setInt(8, conta.getId());
            
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
