package DAO;

import Modelo.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ProdutoDAO {
    
    //ProximoCodigo(): RETORNAR O MAIOR ID DA TABELA Produto
    public static int ProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int codigo = -1;
        
        String sql = "select max(codigo) as ID from PRODUTO";
        
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
    
    //Salvar(): SALVAR Produto NO BANCO DE DADOS
    public static void Salvar(Produto produto){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement insereSt = null;
        
        String sql = "insert produto (CODIGO, NOME, TIPO, DESCRICAO, PREÇO, ESTOQUE  values (?,?,?,?,?,?,?)";
        
        try {            
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, produto.getCodigo());
            insereSt.setString(2, produto.getNome());
            insereSt.setString(4, produto.getTipo());
            insereSt.setString(5, produto.getDescricao());
            insereSt.setDouble(6, produto.getPreco());
            insereSt.setInt(7, produto.getEstoque());
            insereSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso.");
                insereSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Salvar(): " + e.getMessage());
            }
        }            
    }
    
    //RecuperarProduto(): RESGATAR PRODUTO DO BANCO DE DADOS
    public static Produto RecuperarProduto(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        Produto produtoRecuperado = new Produto();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from produto where ID = " + iCod;
        
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            resultado.next();
            
            if (resultado.getRow() == 1){
                
                produtoRecuperado.setCodigo(resultado.getInt("ID"));
                produtoRecuperado.setNome(resultado.getString("NOME"));
                produtoRecuperado.setTipo(resultado.getString("TIPO"));
                produtoRecuperado.setDescricao(resultado.getString("DESCRICAO"));
                produtoRecuperado.setPreco(resultado.getDouble("PRECO"));
                produtoRecuperado.setEstoque(resultado.getInt("ESTOQUE"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar produto: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperarProduto(): " + e.getMessage());
            }
        }
        
        return produtoRecuperado;
    }
}
