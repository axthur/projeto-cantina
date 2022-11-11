package DAO;

import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ClienteDAO {
    //PegaCodigoPelaNavegacao(): RETORNAR O CLIENTE, O ANTECESSOR, O SUCESSOR E O ÚLTIMO REGISTRO
    public static final int cNavPrimeiro = 0;
    public static final int cNavAnterior = 1;
    public static final int cNavProximo = 2;
    public static final int cNavUltimo = 3;
    
    public static int PegaCodigoPelaNavegacao(int iOpcao, int icodigoAtual){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int CodigoEncontrado = -1;
        
        String sql = "";
                
        switch(iOpcao){
            case cNavPrimeiro: 
                sql = "select min(ID) as ID from CLIENTE"; 
                break;
            case cNavAnterior: 
                sql = "select max(ID) as ID from CLIENTE where ID < " + String.valueOf(icodigoAtual); 
                break;
            case cNavProximo: 
                sql = "select min(ID) as ID from CLIENTE where ID > " + String.valueOf(icodigoAtual); 
                break;
            case cNavUltimo: 
                sql = "select max(ID) as ID from CLIENTE"; 
                break;
        }
        
        try {
            consulta = (Statement)conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            resultado.next();
            CodigoEncontrado = resultado.getInt("ID");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao executar SQL de navegação: " + e.getMessage());
        } finally {
            try {
               consulta.close();
               conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função PegaCodigoPelaNavegacao(): " + e.getMessage());
            }  
        }
        
        return CodigoEncontrado;
    }
    
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
    
    //Atualizar(): ALTERA O REGISTRO NO BANCO DE DADOS
    public static void Atualizar(Cliente cliente){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement atualizaSt = null;
        
        String sql = "update CLIENTE set NOME = ?, TELEFONE = ?, EMAIL = ?, ENDERECO = ?, CURSO = ? where ID = ?";
        
        try {
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setString(1, cliente.getNome());
            atualizaSt.setString(2, cliente.getTelefone());
            atualizaSt.setString(3, cidade.getEmail());
            atualizaSt.setString(4, cidade.getEndereco());
            atualizaSt.setString(5, cidade.getCurso());
            atualizaSt.setInt(6, cidade.getCodigo());
            atualizaSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage());
        } finally {
            try {
                atualizaSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Atualizar(): " + e.getMessage());
            }
        }
    }
    
    //Excluir(): EXCLUI UM REGISTRO DO BANCO DE DADOS
    public static void Excluir(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement excluiSt = null;
        
        String sql = "delete from CLIENTE where codigo = " + iCod;
        
        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + e.getMessage());
        } finally {
            try {
                excluiSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Excluir(): " + e.getMessage());
            }
        }
    }
}
