package DAO;

import Modelo.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class VendaDAO {
    
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
}
