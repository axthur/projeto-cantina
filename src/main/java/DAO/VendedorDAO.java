/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Modelo.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author arthu
 */
public class VendedorDAO {
    
    //ProximoCodigo(): RETORNAR O MAIOR ID DA TABELA VENDEDOR
    public static int ProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int codigo = -1;
        
        String sql = "select max(codigo) as ID from VENDEDOR";
        
        try {
            consulta = (Statement)conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            resultado.next();
            codigo = resultado.getInt("ID");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar SQL de navegação: " + e.getMessage());
        } finally {
            try {
               consulta.close();
               conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função ProximoCodigo(): " + e.getMessage());
            }            
        }      
        
        return codigo + 1;
    }
    
    //Salvar(): SALVAR VENDEDOR NO BANCO DE DADOS
    public static void Salvar(Vendedor vendedor){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement insereSt = null;
        
        String sql = "insert vendedor (ID, NOME, TELEFONE, EMAIL, ENDERECO, CARGA_HORARIA) values (?,?,?,?,?,?)";
        
        try {            
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, vendedor.getCodigo());
            insereSt.setString(2, vendedor.getNome());
            insereSt.setString(3, vendedor.getTelefone());
            insereSt.setString(4, vendedor.getEmail());
            insereSt.setString(4, vendedor.getEndereco());
            insereSt.setInt(4, vendedor.getCargaHoraria());
            insereSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar vendedor: " + e.getMessage());
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Vendedor cadastrado com sucesso.");
                insereSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Salvar(): " + e.getMessage());
            }
        }            
    }
    
    //RecuperarVendedor(): RESGATAR VENDEDOR DO BANCO DE DADOS
    public static Vendedor RecuperarVendedor(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        Vendedor vendedorRecuperado = new Vendedor();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from vendedor where ID = " + iCod;
        
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            resultado.next();
            
            if (resultado.getRow() == 1){
                
                vendedorRecuperado.setCodigo(resultado.getInt("ID"));
                vendedorRecuperado.setNome(resultado.getString("NOME"));
                vendedorRecuperado.setTelefone(resultado.getString("TELEFONE"));
                vendedorRecuperado.setEmail(resultado.getString("EMAIL"));
                vendedorRecuperado.setEndereco(resultado.getString("ENDERECO"));
                vendedorRecuperado.setCargaHoraria(resultado.getInt("CARGA HORÁRIA"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar vendedor: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperarVendedor(): " + e.getMessage());
            }
        }
        
        return vendedorRecuperado;
    }
}
