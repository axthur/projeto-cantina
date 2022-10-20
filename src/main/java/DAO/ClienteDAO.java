package DAO;

import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ClienteDAO {
    
    //ProximoCodigo(): RETORNAR O MAIOR ID DA TABELA CLIENTE
    public static int ProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int codigo = -1;
        
        String sql = "select max(codigo) as ID from CLIENTE";
        
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
    
    //Salvar(): SALVAR CLIENTE NO BANCO DE DADOS
    public static void Salvar(Cliente cliente){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement insereSt = null;
        
        String sql = "insert cliente (ID, NOME, TELEFONE, EMAIL, ENDERECO, CURSO) values (?,?,?,?,?,?)";
        
        try {            
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, cliente.getCodigo());
            insereSt.setString(2, cliente.getNome());
            insereSt.setString(3, cliente.getTelefone());
            insereSt.setString(4, cliente.getEmail());
            insereSt.setString(4, cliente.getEndereco());
            insereSt.setString(4, cliente.getCurso());
            insereSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + e.getMessage());
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.");
                insereSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Salvar(): " + e.getMessage());
            }
        }            
    }
    
    //RecuperarCliente(): RESGATAR CLIENTE DO BANCO DE DADOS
    public static Cliente RecuperarCliente(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        Cliente clienteRecuperado = new Cliente();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from cliente where codigo = " + iCod;
        
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            resultado.next();
            
            if (resultado.getRow() == 1){
                
                clienteRecuperado.setCodigo(resultado.getInt("ID"));
                clienteRecuperado.setNome(resultado.getString("NOME"));
                clienteRecuperado.setTelefone(resultado.getString("TELEFONE"));
                clienteRecuperado.setEmail(resultado.getString("EMAIL"));
                clienteRecuperado.setEndereco(resultado.getString("ENDERECO"));
                clienteRecuperado.setCurso(resultado.getString("CURSO"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar cliente: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperarCliente(): " + e.getMessage());
            }
        }
        
        return clienteRecuperado;
    }
}
