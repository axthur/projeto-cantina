/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author arthu
 */
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
