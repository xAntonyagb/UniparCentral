package br.unipar.central.utils;

import br.unipar.central.exceptions.BancoDeDadosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseUtils {
    private static Connection conn = null;
    
    public static Connection getConnection() {
        
        if (conn == null) {
            try {

                conn = DriverManager.getConnection(
                "jdbc:postgresql://3.142.131.90:5435/uniparcentral",
                "aluno",
                "aluno"
                );

            } catch (SQLException e) {
                    throw new BancoDeDadosException(e.getMessage());
            }
        }
        
        return conn;
    }
    
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                    throw new BancoDeDadosException(e.getMessage());
            }
        }
    }

    public static void closePreparedStatment(PreparedStatement st) {
        if (st != null) {
            try {
                    st.close();
            } catch (SQLException e) {
                    throw new BancoDeDadosException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                    rs.close();
            } catch (SQLException e) {
                    throw new BancoDeDadosException(e.getMessage());
            }
        }
    }
}
