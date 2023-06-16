package br.unipar.central.repositories;

import br.unipar.central.exceptions.BancoDeDadosException;
import br.unipar.central.models.Pessoa;
import br.unipar.central.utils.DataBaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.unipar.central.models.PessoaJuridica;
import br.unipar.central.models.PessoaFisica;

public class PessoaDAO {
    
    private Connection conn = null;
    
    
    private Pessoa PessoaFisicaInstance(ResultSet rs) throws SQLException {
        return new PessoaFisica(
                rs.getTimestamp("datanascimento"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getString("rg"),
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("ra")
        );
    }
	
    private Pessoa pessoaJuridicaInstance(ResultSet rs) throws SQLException {
        return new PessoaJuridica(
                    rs.getString("razaosocial"),
                    rs.getString("cnpj"),
                    rs.getString("cnaeprincipal"),
                    rs.getString("fantasia"),
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("ra")
        );
    }
    
    public List<Pessoa> findAll() {
        ArrayList<Pessoa> retorno = new ArrayList<>();
        retorno.addAll(findAllPessoasFisicas());
        retorno.addAll(findAllPessoasJuridicas());
        
        return retorno;
    }
    
    
    private List<Pessoa> findAllPessoasJuridicas() {

        List<Pessoa> pessoas = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT * " + 
                    "FROM pessoa " + 
                    "INNER JOIN pessoajuridica ON pessoajuridica.pessoa_id = pessoa.id"
            );

            rs = pstmt.executeQuery();

            while (rs.next()) {
                pessoas.add(pessoaJuridicaInstance(rs));
            }
        }catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
        return pessoas;
    }
    
    
    private List<Pessoa> findAllPessoasFisicas() {
            
        List<Pessoa> pessoas = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(
                    "SELECT * " + 
                    "FROM pessoa " + 
                    "INNER JOIN pessoafisica ON pessoafisica.pessoa_id = pessoa.id"
            );

            rs = pstmt.executeQuery();

            while (rs.next()) {
                pessoas.add(PessoaFisicaInstance(rs));
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
        return pessoas;
    }
	
    
    
    public void insert(Pessoa pessoa) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(
                    "INSERT INTO pessoa (email, ra) " + 
                    "VALUES (?, ?)");


            pstmt.setString(1, pessoa.getEmail());
            pstmt.setString(2, pessoa.getRegistroAcademico());

            int rowsAffected = pstmt.executeUpdate();
            conn.commit();

            if (rowsAffected > 0) {

                rs = pstmt.getGeneratedKeys();

                while (rs.next()) {
                    if (pessoa instanceof PessoaFisica fisica) 
                        inserirPessoaFisica(fisica);

                    else if (pessoa instanceof PessoaJuridica juridica) 
                        inserirPessoaJuridica(juridica);
                }
            }

        } catch (SQLException e) {
            
            try {
                conn.rollback();
                throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            }  catch (SQLException e1) {
                throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }
                
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }
        
        
    private void inserirPessoaFisica(PessoaFisica pf) {
        PreparedStatement pstmt = null;
        
        try {
            conn = DataBaseUtils.getConnection();
            
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(
                    "INSERT INTO pessoafisica (nome, cpf, rg, datanascimento, pessoa_id) " + 
                    "VALUES (?, ?, ?, ?, ?)");
            
            pstmt.setString(1, pf.getNome());
            pstmt.setString(2, pf.getCpf());
            pstmt.setString(3, pf.getRg());
            pstmt.setTimestamp(4, pf.getDataNascimento());
            pstmt.setInt(5, pf.getId());
            
            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            } 
            catch (SQLException e1) {
                throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }
            
        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
        
    }
        
    
    private void inserirPessoaJuridica(PessoaJuridica pj) {
        PreparedStatement pstmt = null;

        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(
                    "INSERT INTO pessoajuridica (razaosocial, cnpj, cnaeprincipal, nomefantasia, pessoa_id) " + 
                    "VALUES (?, ?, ?, ?, ?)");

            pstmt.setString(1, pj.getRazaoSocial());
            pstmt.setString(2, pj.getCnpj());
            pstmt.setString(3, pj.getCnaePrincipal());
            pstmt.setString(4, pj.getFantasia());
            pstmt.setInt(5, pj.getId());

            pstmt.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            } 
            catch (SQLException e1) {
                throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }

        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }
    
    
    
    public Pessoa findById(int id) {
        Pessoa pessoa = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataBaseUtils.getConnection();

            pstmt = conn.prepareStatement(
                    "SELECT * " + 
                    "FROM pessoa " + 
                    "INNER JOIN pessoafisica ON pessoafisica.pessoa_id = pessoa.id " +
                    "WHERE pessoa.id = ?");

            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pessoa = PessoaFisicaInstance(rs);
            
            } else {

                DataBaseUtils.closePreparedStatment(pstmt);
                DataBaseUtils.closeConnection();


                pstmt = conn.prepareStatement(
                        "SELECT * " + 
                        "FROM pessoa " + 
                        "INNER JOIN pessoajuridica ON pessoajuridica.pessoa_id = pessoa.id" +
                        "WHERE pessoa.id = ?");

                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    pessoa = pessoaJuridicaInstance(rs);
                }
            }
        }
        catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }

        return pessoa;
    }

    
    
    public void update(Pessoa pessoa) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(
                    "UPDATE "
                        + "pessoa "  
                    + "SET "
                        + "email = ?, "
                        + "ra = ? " 
                    + "WHERE "
                        + "pessoa.id = ?");
            
            pstmt.setString(1, pessoa.getEmail());
            pstmt.setString(2, pessoa.getRegistroAcademico());
            pstmt.setInt(3, pessoa.getId());

            
            int rowsAffected = pstmt.executeUpdate();
            conn.commit();
            
            if (rowsAffected > 0) {

                rs = pstmt.getGeneratedKeys();

                while (rs.next()) {
                    if (pessoa instanceof PessoaFisica fisica) 
                        updatePessoaFisica(fisica);

                    else if (pessoa instanceof PessoaJuridica juridica) 
                        updatePessoaJuridica(juridica);
                }
            }

        } catch (SQLException e) {
            try {
                    conn.rollback();
                    throw new BancoDeDadosException("Transiçao revertida! Causado por: \n" + e.getMessage());
            } catch (SQLException e1) {
                    throw new BancoDeDadosException("Erro ao tentar reverter! Causado por: \n" + e1.getMessage());
            }
        } finally {
            DataBaseUtils.closeResultSet(rs);
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }

    
    private void updatePessoaJuridica(PessoaJuridica juridica) {
        PreparedStatement pstmt = null;

        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(
                        "UPDATE "
                            + "pessoajuridica "  
                        + "SET "
                            + "razaosocial = ?, "
                            + "cnpj = ?, " 
                            + "cnaeprincipal = ?, "
                            + "fantasia = ? "
                        + "WHERE "
                            + "pessoa_id = ?");

            pstmt.setString(1, juridica.getRazaoSocial());
            pstmt.setString(2, juridica.getCnpj());
            pstmt.setString(3, juridica.getCnaePrincipal());
            pstmt.setString(4, juridica.getFantasia());
            pstmt.setInt(5, juridica.getId());

            
            pstmt.executeUpdate();
            conn.commit();
            
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }
    }
    
    private void updatePessoaFisica(PessoaFisica fisica) {
        PreparedStatement pstmt = null;

        try {
            conn = DataBaseUtils.getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(
                        "UPDATE "
                            + "pessoafisica "
                        + "SET "
                            + "datanascimento = ?, " 
                            + "nome = ?, " 
                            + "cpf = ?, " 
                            + "rg = ? " 
                        + "WHERE "
                            + "pessoa_id = ?");

            pstmt.setTimestamp(1, fisica.getDataNascimento());
            pstmt.setString(2, fisica.getNome());
            pstmt.setString(3, fisica.getCpf());
            pstmt.setString(4, fisica.getRg());
            pstmt.setInt(5, fisica.getId());

            
            pstmt.executeUpdate();
            conn.commit();
            
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getMessage());
        } finally {
            DataBaseUtils.closePreparedStatment(pstmt);
            DataBaseUtils.closeConnection();
        }     
    }
    
    
    
    public void delete(int id) {
        PreparedStatement pstmt = null;

        try {
            conn = DataBaseUtils.getConnection();
            pstmt = conn.prepareStatement(
                    "DELETE " + 
                    "FROM pessoa " + 
                    "WHERE pessoa.id = ?" +
                    "CASCADE");

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
