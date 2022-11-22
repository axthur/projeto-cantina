package DAO;

import Modelo.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutoDAO {
    //PegaCodigoPelaNavegacao(): RETORNAR O PRODUTO, O ANTECESSOR, O SUCESSOR E O ÚLTIMO REGISTRO
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
            case cNavPrimeiro -> sql = "select min(ID) as ID from produto";
            case cNavAnterior -> sql = "select max(ID) as ID from produto where ID < " + String.valueOf(icodigoAtual);
            case cNavProximo -> sql = "select min(ID) as ID from produto where ID > " + String.valueOf(icodigoAtual);
            case cNavUltimo -> sql = "select max(ID) as ID from produto";
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
    
    //ProximoCodigo(): RETORNAR O MAIOR ID DA TABELA Produto
    public static int ProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int codigo = -1;
        
        String sql = "select max(ID) as ID from produto";
        
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
        
        String sql = "insert produto (ID, NOME, TIPO, DESCRICAO, PRECO, ESTOQUE)  values (?,?,?,?,?,?)";
        
        try {            
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, produto.getCodigo());
            insereSt.setString(2, produto.getNome());
            insereSt.setString(3, produto.getTipo());
            insereSt.setString(4, produto.getDescricao());
            insereSt.setDouble(5, produto.getPreco());
            insereSt.setInt(6, produto.getEstoque());
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
    //Atualizar(): ALTERA O REGISTRO NO BANCO DE DADOS
    public static void Atualizar(Produto produto){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement atualizaSt = null;
        
        String sql = "update produto set NOME = ?, TIPO = ?, DESCRICAO = ?, PRECO = ?, ESTOQUE = ? where ID = ?";
        
        try {
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setString(1, produto.getNome());
            atualizaSt.setString(2, produto.getTipo());
            atualizaSt.setString(3, produto.getDescricao());
            atualizaSt.setDouble(4, produto.getPreco());
            atualizaSt.setInt(5, produto.getEstoque());
            atualizaSt.setInt(6, produto.getCodigo());
            atualizaSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
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
        
        String sql = "delete from produto where ID = " + iCod;
        
        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir produto: " + e.getMessage());
        } finally {
            try {
                excluiSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Excluir(): " + e.getMessage());
            }
        }
    }
    
    //RecuperaObjetos(): RECUPERA UMA TABELA DO BANCO DE DADOS
    public static ArrayList<Produto> RecuperaObjetos(String pCampo, String pValor){
        Connection conexao = FabricaConexao.getConnection();
        
        ArrayList<Produto> produtos = new ArrayList<>();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from produto where " + pCampo + " like '%" + pValor + "%'";
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            while(resultado.next()){
                Produto produtoTemp = new Produto();
                produtoTemp.setCodigo(resultado.getInt("ID"));
                produtoTemp.setNome(resultado.getString("NOME"));
                produtoTemp.setTipo(resultado.getString("TIPO"));
                produtoTemp.setDescricao(resultado.getString("DESCRICAO"));
                produtoTemp.setPreco(resultado.getDouble("PRECO"));
                produtoTemp.setEstoque(resultado.getInt("ESTOQUE"));
                produtos.add(produtoTemp);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao recuperar produto: " + e.getMessage());
        } finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperaObjetos(): " + e.getMessage());
            }
        }
        return produtos;
    }
}