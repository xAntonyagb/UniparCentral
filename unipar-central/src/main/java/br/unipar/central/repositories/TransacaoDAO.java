package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Transacao;
import br.unipar.central.models.enums.TipoTransacaoEnum;
import br.unipar.central.services.ContaService;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    
    private static final String INSERT = 
            "INSERT INTO transacao"
                + "(id, data_hora, valor, tipo, ra, conta_origem, conta_destino)"
            + " VALUES "
                + "(?, ?, ?, ?, ?, ?, ?);";
    
    private static final String FIND_ALL = 
            "SELECT "
                + "id, "
                + "data_hora, "
                + "valor, "
                + "tipo, "
                + "ra, "
                + "conta_origem, "
                + "conta_destino "
            + "FROM "
                + "transacao;";
    
    private static final String FIND_BY_ID = 
            "SELECT "
                + "id, "
                + "data_hora, "
                + "valor, "
                + "tipo, "
                + "ra, "
                + "conta_origem, "
                + "conta_destino "
            + "FROM "
                + "transacao "
            + "WHERE id = ?;";
    
    private static final String DELETE_BY_ID = 
            "DELETE FROM "
                + "transacao "
            + "WHERE "
                + "id = ?";
    
    private static final String UPDATE = 
            "UPDATE "
                + "transacao "
            + "SET "
                + "data_hora = ?, "
                + "valor = ?, "
                + "tipo = ?, "
                + "ra = ?, "
                + "conta_origem = ?, "
                + "conta_destino = ? "
            + "WHERE "
                + "id = ?";
    
    
    
    private Connection conn = null;
    
    private Transacao transacaoInstance(ResultSet rs) throws SQLException  {
        ContaService cServ = new ContaService(new ContaDAO());
        return new Transacao(
                rs.getInt("id"),
                rs.getTimestamp("data_hora"),
                rs.getBigDecimal("valor"),
                TipoTransacaoEnum.paraEnum(rs.getInt("tipo")),
                rs.getString("ra"),
                cServ.findById(rs.getInt("conta_origem")),
                cServ.findById(rs.getInt("conta_destino"))
        );
    }
    
    
    public List<Transacao> findAll() {
        
        ArrayList<Transacao> retorno = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DataBaseUtils.getConnection();
            
            pstmt = conn.prepareStatement(FIND_ALL);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()){
                retorno.add(transacaoInstance(rs));
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
    
    public Transacao findById(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Transacao retorno = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(FIND_BY_ID);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                retorno = transacaoInstance(rs);
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
    
    public void insert(Transacao transacao) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(INSERT);

            pstmt.setInt(1, transacao.getId());
            pstmt.setTimestamp(2, transacao.getData_hora());
            pstmt.setBigDecimal(3, transacao.getValor());
            pstmt.setInt(4, transacao.getTipo().getCodigo());
            pstmt.setString(5, transacao.getRegistroAcademico());
            pstmt.setInt(6, transacao.getContaOrigem().getId());
            pstmt.setInt(7, transacao.getContaDestino().getId());
            
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
    
    public void update(Transacao transacao) {
        PreparedStatement pstmt = null;
        
        try {
            
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(UPDATE);
            
            
            pstmt.setTimestamp(1, transacao.getData_hora());
            pstmt.setBigDecimal(2, transacao.getValor());
            pstmt.setInt(3, transacao.getTipo().getCodigo());
            pstmt.setString(4, transacao.getRegistroAcademico());
            pstmt.setInt(5, transacao.getContaOrigem().getId());
            pstmt.setInt(6, transacao.getContaDestino().getId());
            pstmt.setInt(7, transacao.getId());
            
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
