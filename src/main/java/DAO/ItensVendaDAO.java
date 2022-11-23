package DAO;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import Modelo.ItensVenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItensVendaDAO {

    //PegaCodigoPelaNavegacao(): RETORNAR O CLIENTE, O ANTECESSOR, O SUCESSOR E O ÚLTIMO REGISTRO
    public static final int cNavPrimeiro = 0;
    public static final int cNavAnterior = 1;
    public static final int cNavProximo = 2;
    public static final int cNavUltimo = 3;

    public static int PegaCodigoPelaNavegacao(int iOpcao, int icodigoAtual) {
        Connection conexao = FabricaConexao.getConnection();

        Statement consulta = null;
        ResultSet resultado = null;
        int CodigoEncontrado = -1;

        String sql = "";

        switch (iOpcao) {
            case cNavPrimeiro ->
                sql = "select min(ID) as ID_VENDA from itens_venda";
            case cNavAnterior ->
                sql = "select max(ID) as ID_VENDA from itens_venda where ID_VENDA < " + String.valueOf(icodigoAtual);
            case cNavProximo ->
                sql = "select min(ID) as ID_VENDA from itens_venda where ID_VENDA > " + String.valueOf(icodigoAtual);
            case cNavUltimo ->
                sql = "select max(ID) as ID_VENDA from itens_venda";
        }

        try {
            consulta = (Statement) conexao.createStatement();
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

    public static int PegaProximoCodigo() {
        Connection conexao = FabricaConexao.getConnection();

        Statement consulta = null;
        ResultSet resultado = null;
        int ProximoCodigo = -1;

        String sql = "select max(ID) as MAIOR_CODIGO from ITENS_VENDA";

        try {
            consulta = (Statement) conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            resultado.next();
            ProximoCodigo = resultado.getInt("MAIOR_CODIGO");
            ProximoCodigo++;
        } catch (SQLException e) {
            System.out.println("Erro na execução da função ProximoCodigo()");
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações na função ProximoCodigo: " + e.getMessage());
            }
        }

        return ProximoCodigo;
    }

    public static void Salvar(ItensVenda item) {
        Connection conexao = FabricaConexao.getConnection();

        PreparedStatement insereSt = null;

        String sql = "insert into itens_venda (ID, ID_VENDA, ID_PRODUTO, NOME_PRODUTO, QUANTIDADE, VALOR_UNITARIO, VALOR_TOTAL) values (?,?,?,?,?,?,?)";

        try {
            insereSt = conexao.prepareStatement(sql);
            insereSt.setInt(1, item.getCodigo());
            insereSt.setInt(2, item.getCodigoVenda());
            insereSt.setInt(3, item.getCodigoProduto());
            insereSt.setString(4, item.getNomeProduto());
            insereSt.setInt(5, item.getQuantidade());
            insereSt.setDouble(6, item.getValorUnitario());
            insereSt.setDouble(7, item.getValorTotal());

            insereSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao incluir item: " + e);
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Item incluído com sucesso.");
                insereSt.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão: " + e);
            }
        }
    }

    public static void Excluir(int iCod) {
        Connection conexao = FabricaConexao.getConnection();

        PreparedStatement excluiSt = null;

        String sql = "delete from itens_venda where ID = " + iCod;

        try {
            excluiSt = conexao.prepareStatement(sql);
            excluiSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir item: " + e);
        } finally {
            try {
                JOptionPane.showMessageDialog(null, "Item excluído com sucesso.");
                excluiSt.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão: " + e);
            }
        }
    }

    //Atualizar(): ALTERA O REGISTRO NO BANCO DE DADOS
    public static void Atualizar(ItensVenda itensVenda) {
        Connection conexao = FabricaConexao.getConnection();

        PreparedStatement atualizaSt = null;

        String sql = "update itens_venda set ID = ?, ID_VENDA = ?, ID_PRODUTO = ?, QUANTIDADE = ?, VALOR_UNITARIO = ?, NOME_PRODUTO = ? where VALOR_TOTAL = ?";

        try {
            atualizaSt = conexao.prepareStatement(sql);
            atualizaSt.setInt(1, itensVenda.getCodigo());
            atualizaSt.setInt(2, itensVenda.getCodigoVenda());
            atualizaSt.setInt(3, itensVenda.getCodigoProduto());
            atualizaSt.setInt(4, itensVenda.getQuantidade());
            atualizaSt.setDouble(5, itensVenda.getValorUnitario());
            atualizaSt.setDouble(6, itensVenda.getValorTotal());
            atualizaSt.setString(7, itensVenda.getNomeProduto());
            atualizaSt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar itensVenda: " + e.getMessage());
        } finally {
            try {
                atualizaSt.close();
                conexao.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexão na função Atualizar(): " + e.getMessage());
            }
        }
    }

    public static ArrayList<ItensVenda> RecuperaObjetos(int iCod) {
        Connection conexao = FabricaConexao.getConnection();

        ArrayList<ItensVenda> itens = new ArrayList<ItensVenda>();
        Statement consulta = null;
        ResultSet resultado = null;

        String sql = "select ID from itens_venda where ID = " + iCod;

        /*String sql = "select i.id, "
                //+ "i.id_venda, "
                + "p.id_produto, i.quantidade, p.valor_total, i.valor_unitario "
                + "from itens_venda i, produto p where i.id_produto = p.id and "
                + "i.id_venda = " + iCod;*/
        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                ItensVenda itemTemp = new ItensVenda();
                itemTemp.setCodigo(resultado.getInt("ID"));
                itemTemp.setCodigoVenda(resultado.getInt("ID_VENDA"));
                itemTemp.setCodigoProduto(resultado.getInt("ID_PRODUTO"));
                itemTemp.setQuantidade(resultado.getInt("QUANTIDADE"));
                itemTemp.setValorTotal(resultado.getDouble("VALOR_TOTAL"));
                itemTemp.setValorUnitario(resultado.getDouble("VALOR_UNITARIO"));
                itemTemp.setNomeProduto(resultado.getString("NOME_PRODUTO"));
                itens.add(itemTemp);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar itens: " + e);
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexao: " + e);
            }
        }
        return itens;
    }

    public static ItensVenda RecuperaObjetoParaExcluir(int iCod) {
        Connection conexao = FabricaConexao.getConnection();

        //ArrayList<ItensVenda> itens = new ArrayList<ItensVenda>();
        Statement consulta = null;
        ResultSet resultado = null;

        String sql = "select * from itens_venda where ID = " + iCod;
        ItensVenda itemTemp = new ItensVenda();

        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            while (resultado.next()) {
                itemTemp.setCodigo(resultado.getInt("ID"));
                itemTemp.setCodigoVenda(resultado.getInt("ID_VENDA"));
                itemTemp.setCodigoProduto(resultado.getInt("ID_PRODUTO"));
                itemTemp.setQuantidade(resultado.getInt("QUANTIDADE"));
                itemTemp.setValorTotal(resultado.getDouble("VALOR_TOTAL"));
                itemTemp.setValorUnitario(resultado.getDouble("VALOR_UNITARIO"));
                itemTemp.setNomeProduto(resultado.getString("NOME_PRODUTO"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao recuperar itens a excluir:\n" + e);
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

    public static boolean RecuperaObjeto(int iCod) {
        Connection conexao = FabricaConexao.getConnection();

        ArrayList<ItensVenda> itens = new ArrayList<ItensVenda>();
        Statement consulta = null;
        ResultSet resultado = null;
        boolean retorno = true;

        String sql = "select ID from itens_venda where ID = " + iCod;

        try {
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            if (resultado.getInt("ID") == -1) {
                retorno = false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao encontrar item: " + e);
        } finally {
            try {
                consulta.close();
                resultado.close();
                conexao.close();
            } catch (Throwable e) {
                JOptionPane.showMessageDialog(null, "Erro ao encerrar conexao: " + e);
            }
        }
        return retorno;
    }
    
    public static int Count(){
        Connection conexao = FabricaConexao.getConnection();
        
        Statement consulta = null;
        ResultSet resultado = null;
        int count = -1;
        
        String sql = "select count(*) from itens_venda";
        
        try{
            consulta = conexao.createStatement();
            resultado = consulta.executeQuery(sql);

            if(resultado.next() && resultado != null)
                count = resultado.getInt(1);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar quantidade de registros: " + e);
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
