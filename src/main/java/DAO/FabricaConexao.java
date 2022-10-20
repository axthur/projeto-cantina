package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
    public static Connection getConnection(){
        Connection Conexao = null;
        try{
            String URL = "jdbc:mysql://127.0.0.1/bd_cantina";
            String Usuario = "root";
            String Senha = "";
            Conexao = DriverManager.getConnection(URL, Usuario, Senha);
        }catch(SQLException e){
            System.out.println("Erro: " + e.getMessage());
        }
        return Conexao;
    }
}
