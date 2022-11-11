package DAO;

import Modelo.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class VendaDAO {
    //PegaCodigoPelaNavegacao(): RETORNAR A VENDA, O ANTECESSOR, O SUCESSOR E O ÚLTIMO REGISTRO
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
                sql = "select min(ID) as ID from VENDA"; 
                break;
            case cNavAnterior: 
                sql = "select max(ID) as ID from VENDA where ID < " + String.valueOf(icodigoAtual); 
                break;
            case cNavProximo: 
                sql = "select min(ID) as ID from VENDA where ID > " + String.valueOf(icodigoAtual); 
                break;
            case cNavUltimo: 
                sql = "select max(ID) as ID from VENDA"; 
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
    //ProximoCodigo(): RETORNAR O MAIOR ID DA TABELA VENDA
    public static int ProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int codigo = -1;
        
        String sql = "select max(codigo) as ID from VENDA";
        
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
    
    //Salvar(): SALVAR VENDA NO BANCO DE DADOS
    public static void Salvar(Venda venda){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement insereSt = null;
        
        String sql = "insert venda (ID, ID_CLIENTE, ID_VENDEDOR, VALOR, DATA_COMPRA, DATA_PAGAMENTO, METODO_PAGAMENTO) values (?,?,?,?,?,?,?)";
        
        try {            
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, venda.getCodigo());
            insereSt.setInt(2, venda.getIdCliente());
            insereSt.setInt(3, venda.getIdVendedor());
            insereSt.setDouble(4, venda.getValor());
            insereSt.setString(5, venda.getDataCompra());
            insereSt.setString(6, venda.getDataPagamento());
            insereSt.setString(7, venda.getMetodoDePagamento());
            insereSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + e.getMessage());
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Venda cadastrado com sucesso.");
                insereSt.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Salvar(): " + e.getMessage());
            }
        }            
    }
    
    //RecuperarVenda(): RESGATAR VENDA DO BANCO DE DADOS
    public static Venda RecuperarVenda(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        Venda vendaRecuperada = new Venda();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from venda where ID = " + iCod;
        
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            resultado.next();
            
            if (resultado.getRow() == 1){
                
                vendaRecuperada.setCodigo(resultado.getInt("ID"));
                vendaRecuperada.setIdCliente(resultado.getInt("ID_CLIENTE"));
                vendaRecuperada.setIdVendedor(resultado.getInt("ID_VENDEDOR"));
                vendaRecuperada.setValor(resultado.getDouble("VALOR"));
                vendaRecuperada.setDataCompra(resultado.getString("DATA_COMPRA"));
                vendaRecuperada.setDataPagamento(resultado.getString("DATA_PAGAMENTO"));
                vendaRecuperada.setMetodoDePagamento(resultado.getString("METODO_PAGAMENTO"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar venda: " + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close(); 
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperarVenda(): " + e.getMessage());
            }
        }
        
        return vendaRecuperada;
    }
    
    //Atualizar(): ALTERA O REGISTRO NO BANCO DE DADOS
    public static void Atualizar(Venda venda){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement atualizaSt = null;
        
        String sql = "update venda set ID_CLIENTE = ?, ID_VENDEDOR = ?, VALOR = ?, DATA_COMPRA = ?, DATA_PAGAMENTO = ?, METODO_PAGAMENTO = ? where ID = ?";
        
        try {
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setInt(1, venda.getIdCliente());
            atualizaSt.setInt(2, venda.getIdVendedor());
            atualizaSt.setDouble(3, venda.getValor());
            atualizaSt.setString(4, venda.getDataCompra());
            atualizaSt.setString(5, venda.getDataPagamento());
            atualizaSt.setString(6, venda.getMetodoDePagamento());
            atualizaSt.setInt(7, venda.getCodigo());
            atualizaSt.executeUpdate();            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar venda: " + e.getMessage());
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
        
        String sql = "delete from venda where ID = " + iCod;
        
        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir venda: " + e.getMessage());
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
