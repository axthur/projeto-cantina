package DAO;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import Modelo.ItensVenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItensVendaDAO {
    
    public static int PegaProximoCodigo(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int ProximoCodigo = -1;
        
        String sql = "select max(ID) as MAIOR_CODIGO from ITENS_VENDA";
        
        try{
            consulta = (Statement)conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            resultado.next();
            ProximoCodigo = resultado.getInt("MAIOR_CODIGO");
            ProximoCodigo++;
        }catch (SQLException e){
            System.out.println("Erro na execução da função ProximoCodigo");
        }finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch (Throwable e){
                System.out.println("Erro ao fechar operações na função ProximoCodigo: "+e.getMessage());
            }
        }
        
        return ProximoCodigo;
    }
    
    public static void Salvar(ItensVenda item){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement insereSt = null;
        
        String sql = "insert into itens_venda values (?,?,?,?,?,?)";
        
        try{
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, PegaProximoCodigo());
            insereSt.setInt(2, item.getCodigoVenda());
            insereSt.setInt(3, item.getCodigoProduto());
            insereSt.setInt(4, item.getQuantidade());
            insereSt.setDouble(5, item.getValorTotal());
            insereSt.setDouble(6, item.getValorUnitario());
            insereSt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao incluir item: " + e);
        }finally{
            try{
                //JOptionPane.showMessageDialog(null, "Item incluído com sucesso.");
                insereSt.close();
                conexao.close();
            }catch(Throwable e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão: " + e);
            }
        }
    }
    
    public static void Excluir(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        PreparedStatement excluiSt = null;
        
        String sql = "delete from itens_venda where id = " + iCod;
        
        try{
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir item: " + e);
        }finally{
            try{
                JOptionPane.showMessageDialog(null, "Item excluído com sucesso.");
                excluiSt.close();
                conexao.close();
            }catch(Throwable e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão: " + e);
            }
        }
    }
    
    public static ArrayList<ItensVenda> RecuperaObjetos(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        ArrayList<ItensVenda> itens = new ArrayList<ItensVenda>();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select i.id, "
                //+ "i.id_venda, "
                + "p.id_produto, i.quantidade, p.valor_total, i.valor_unitario "
                + "from itens_venda i, produto p where i.id_produto = p.id and "
                + "i.id_venda = " + iCod;
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            while(resultado.next()){
                ItensVenda itemTemp = new ItensVenda();
                itemTemp.setCodigo(resultado.getInt("ID"));
                itemTemp.setCodigoVenda(resultado.getInt("ID_VENDA"));
                itemTemp.setCodigoProduto(resultado.getInt("ID_PRODUTO"));
                itemTemp.setQuantidade(resultado.getInt("QUANTIDADE"));
                itemTemp.setValorTotal(resultado.getDouble("VALOR_TOTAL"));
                itemTemp.setValorUnitario(resultado.getDouble("VALOR_UNITARIO"));
                itens.add(itemTemp);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar itens: " + e);
        }finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(Throwable e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexao: " + e);
            }
        }
        return itens;
    }
    
    public static boolean RecuperaObjeto(int iCod){
        Connection conexao = FabricaConexao.getConnection();
        
        ArrayList<ItensVenda> itens = new ArrayList<ItensVenda>();
        Statement consulta = null;
        ResultSet resultado = null;
        boolean retorno = true;
        
        String sql = "select id from itens_venda where id = " + iCod;
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            if(resultado.getInt("ID") == -1){
                retorno = false;
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao encontrar item: " + e);
        }finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(Throwable e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexao: " + e);
            }
        }
        return retorno;
    }
}