/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author arthu
 */
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
        this.quantidade = -1;
        this.valorUnitario = -1;
        this.valorTotal = -1;
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
}
