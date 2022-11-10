package DAO;

import Modelo.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class VendedorDAO {
    //PegaCodigoPelaNavegacao(): RETORNAR O VENDEDOR, O ANTECESSOR, O SUCESSOR E O ÚLTIMO REGISTRO
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
                sql = "select min(ID) as ID from VENDEDOR"; 
                break;
            case cNavAnterior: 
                sql = "select max(ID) as ID from VENDEDOR where ID < " + String.valueOf(icodigoAtual); 
                break;
            case cNavProximo: 
                sql = "select min(ID) as ID from VENDEDOR where ID > " + String.valueOf(icodigoAtual); 
                break;
            case cNavUltimo: 
                sql = "select max(ID) as ID from VENDEDOR"; 
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
