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
    private String nomeProduto;

    public ItensVenda() {
        this.id = -1;
        this.idVenda = -1;
        this.idProduto = -1;
        this.quantidade = 0;
        this.valorUnitario = 0;
        this.valorTotal = 0;
        this.nomeProduto = "";
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
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

    public void setProximoCodigo() {
        this.id = ItensVendaDAO.PegaProximoCodigo();
    }

    public void Salvar() {
        ItensVendaDAO.Salvar(this);
    }

    public void Excluir(int iCod) {
        ItensVendaDAO.Excluir(iCod);
    }

    public static ArrayList<ItensVenda> RecuperaObjetos(int iCod) {
        return ItensVendaDAO.RecuperaObjetos(iCod);
    }

    public static ItensVenda RecuperaObjetoParaExcluir(int iCod) {
        return ItensVendaDAO.RecuperaObjetoParaExcluir(iCod);
    }

    public static boolean RecuperaObjeto(int iCod) {
        return ItensVendaDAO.RecuperaObjeto(iCod);
    }

    /*public static ItensVenda RecuperaObjetoNavegacao(int Opcao, int CodAtual) {
        int CodigoNav = ItensVendaDAO.PegaCodigoPelaNavegacao(Opcao, CodAtual);
        RecuperaObjeto(CodigoNav);
    }*/

    public void Atualizar() {
        ItensVendaDAO.Atualizar(this);
    }

    /*public ArrayList<ItensVenda> RecuperaObjetos(String pCampo, String pValor){
        String nomeCampo = "";
        
        if(pCampo.equalsIgnoreCase("Código do Produto")){
            nomeCampo = "ID_PRODUTO";
        }else if(pCampo.equalsIgnoreCase("Quantidade")){
            nomeCampo = "QUANTIDADE";
        }else if(pCampo.equalsIgnoreCase("Valor unitário")){
            nomeCampo = "VALOR_UNITARIO";
        }else if(pCampo.equalsIgnoreCase("Valor total")){
            nomeCampo = "VALOR_TOTAL";
        }
        
        return ItensVendaDAO.RecuperaObjetos(pValor);
    }*/
}
