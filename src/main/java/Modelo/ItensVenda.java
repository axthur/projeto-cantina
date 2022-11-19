package Modelo;

import DAO.ItensVendaDAO;
import java.util.ArrayList;

public class ItensVenda {
    private int id;
    private int idVenda;
    private int idProduto;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;

    public ItensVenda(){
        this.id = -1;
        this.idVenda = -1;
        this.idProduto = -1;
        this.quantidade = 0;
        this.valorUnitario = 0;
        this.valorTotal = 0;
    }

    public int getCodigo() {
        return id;
    }

    public void setCodigo(int id) {
        this.id = id;
    }

    public int getCodigoVenda() {
        return idVenda;
    }

    public void setCodigoVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getCodigoProduto() {
        return idProduto;
    }

    public void setCodigoProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public void Salvar(){
        ItensVendaDAO.Salvar(this);
    }
    
    public void Excluir(int iCod){
        ItensVendaDAO.Excluir(iCod);
    }
    
    public static ArrayList<ItensVenda> RecuperaObjetos(int iCod){
        return ItensVendaDAO.RecuperaObjetos(iCod);
    }
    
    public static boolean RecuperaObjeto(int iCod){
        return ItensVendaDAO.RecuperaObjeto(iCod);
    }
}