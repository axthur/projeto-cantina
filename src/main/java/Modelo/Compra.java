package Modelo;


import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arthu
 */
public class Compra {
    private int id;
    private List<Produto> produtos;
    private Cliente cliente;
    private Vendedor vendedor;
    private double valorTotal;
    private String dataCompra;
    private String metodoDePagamento;
    private String dataPagamento;

    public double getValorTotal() {
        for(int i = 0; i < produtos.size(); i++){
            valorTotal = valorTotal + produtos.get(i).getPreco();
        }
        return valorTotal;
    }
}