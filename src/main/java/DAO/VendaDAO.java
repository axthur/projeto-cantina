package DAO;

import Modelo.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            case cNavPrimeiro -> sql = "select min(ID) as ID from venda";
            case cNavAnterior -> sql = "select max(ID) as ID from venda where ID < " + String.valueOf(icodigoAtual);
            case cNavProximo -> sql = "select min(ID) as ID from venda where ID > " + String.valueOf(icodigoAtual);
            case cNavUltimo -> sql = "select max(ID) as ID from venda";
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
        
        String sql = "select max(ID) as ID from venda";
        
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
                vendaRecuperada.setDataCompra(resultado.getString("DATA_COMPRA"));
                vendaRecuperada.setDataPagamento(resultado.getString("DATA_PAGAMENTO"));
                vendaRecuperada.setMetodoDePagamento(resultado.getString("METODO_PAGAMENTO"));
                vendaRecuperada.setValor(resultado.getDouble("VALOR"));
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
    
    public static ArrayList<Venda> RecuperaObjetos(String pSql){
        Connection conexao = FabricaConexao.getConnection();
        
        ArrayList<Venda> Vendas = new ArrayList<Venda>();
        Statement consulta = null;
        ResultSet resultado = null;
        
        try{
            consulta = (Statement) conexao.createStatement();
            resultado = consulta.executeQuery(pSql);
            
            while(resultado.next()){
                Venda VendaTemp = new Venda();
                VendaTemp.setCodigo(resultado.getInt("ID"));
                VendaTemp.setIdCliente(resultado.getInt("ID_CLIENTE"));
                VendaTemp.setIdVendedor(resultado.getInt("ID_VENDEDOR"));
                VendaTemp.setValor(resultado.getDouble("VALOR"));
                VendaTemp.setDataCompra(resultado.getString("DATA_COMPRA"));
                VendaTemp.setDataPagamento(resultado.getString("DATA_PAGAMENTO"));
                VendaTemp.setMetodoDePagamento(resultado.getString("METODO_PAGAMENTO"));
                
                Vendas.add(VendaTemp);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar vendas: " + e);
        }finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(Throwable e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexao: " + e);
            }
        }
        return Vendas;
    }
    
    public static ArrayList<Venda> PesquisaObjeto(String pCampo, String pValor, boolean pTodaParte){
        String sql = "select p.*, c.ID_CLIENTE from venda p, CLIENTE c where p.ID_CLIENTE = c.ID and " + pCampo + " like '";
        if (pTodaParte)
            sql = sql + "%";
        sql = sql + pValor + "%'";
        
        return RecuperaObjetos(sql);
    }
    
    //RecuperaObjetos(): RECUPERA UMA TABELA DO BANCO DE DADOS
    public static ArrayList<Venda> RecuperaObjetos(String pCampo, String pValor){
        Connection conexao = FabricaConexao.getConnection();
        
        ArrayList<Venda> vendas = new ArrayList<>();
        Statement consulta = null;
        ResultSet resultado = null;
        
        String sql = "select * from venda where " + pCampo + " like '%" + pValor + "%'";
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);
            
            while(resultado.next()){
                Venda vendaTemp = new Venda();
                vendaTemp.setCodigo(resultado.getInt("ID"));
                vendaTemp.setIdCliente(resultado.getInt("ID_CLIENTE"));
                vendaTemp.setIdVendedor(resultado.getInt("ID_VENDEDOR"));
                vendaTemp.setValor(resultado.getDouble("VALOR"));
                vendaTemp.setDataCompra(resultado.getString("DATA_COMPRA"));
                vendaTemp.setDataPagamento(resultado.getString("DATA_PAGAMENTO"));
                vendaTemp.setMetodoDePagamento(resultado.getString("METODO_PAGAMENTO"));
                vendas.add(vendaTemp);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao recuperar venda: " + e.getMessage());
        } finally{
            try{
                consulta.close();
                resultado.close();
                conexao.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função RecuperaObjetos(): " + e.getMessage());
            }
        }
        return vendas;
    }
    public static Venda RecuperaObjetoParaExcluir(int iCod) {
        Connection conexao = FabricaConexao.getConnection();

        Statement consulta = null;
        ResultSet resultado = null;

        String sql = "select * from venda where ID = " + iCod;
        Venda itemTemp = new Venda();

        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                itemTemp.setCodigo(resultado.getInt("ID"));
                itemTemp.setIdCliente(resultado.getInt("ID_CLIENTE"));
                itemTemp.setIdVendedor(resultado.getInt("ID_VENDEDOR"));
                itemTemp.setValor(resultado.getInt("VALOR"));
                itemTemp.setDataCompra(resultado.getString("DATA_COMPRA"));
                itemTemp.setDataPagamento(resultado.getString("DATA_PAGAMENTO"));
                itemTemp.setMetodoDePagamento(resultado.getString("METODO_PAGAMENTO"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar venda a excluir:\n" + e);
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão:\n" + e);
            }
        }
        return itemTemp;
    }
    
    public static int Count(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int count = -1;
        
        String sql = "select count(*) from venda";
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            if(resultado.next() && resultado != null)
                count = resultado.getInt(1);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar quantidade de vendas: " + e);
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão: " + e);
            }
        }
        return count;
    }
}