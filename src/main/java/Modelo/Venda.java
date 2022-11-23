package Modelo;

import DAO.VendaDAO;
import java.util.ArrayList;

public class Venda {
    private int id;
    private int idCliente;
    private int idVendedor;
    private double valor;
    private String dataCompra;
    private String dataPagamento;
    private String metodoDePagamento;

    public Venda(){
        this.id = -1;
        this.idCliente = -1;
        this.idVendedor = -1;
        this.valor = -1;
        this.dataCompra = "";
        this.dataPagamento = "";
        this.metodoDePagamento = "";
    }

    public int getCodigo() {
        return id;
    }

    public void setCodigo(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(String metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }
    public void setProximoCodigo(){
        this.id = VendaDAO.ProximoCodigo();
    }
    
    public void Salvar(){
        VendaDAO.Salvar(this);
    }
    
    public void Atualizar(){
        VendaDAO.Atualizar(this);
    }
    
    public void Excluir(int Chave){
        VendaDAO.Excluir(Chave);
    }
    
    public void RecuperaObjeto(int Codigo){
        Venda vendaTemp = VendaDAO.RecuperarVenda(Codigo);
        this.setCodigo(vendaTemp.getCodigo());
        this.setIdCliente(vendaTemp.getIdCliente());
        this.setIdVendedor(vendaTemp.getIdVendedor());
        this.setDataCompra(vendaTemp.getDataCompra());
        this.setDataPagamento(vendaTemp.getDataPagamento());
        this.setMetodoDePagamento(vendaTemp.getMetodoDePagamento());
        this.setValor(vendaTemp.getValor());
    }
    
    public void RecuperaObjetoNavegacao(int Opcao, int CodAtual){
        int CodigoNav = VendaDAO.PegaCodigoPelaNavegacao(Opcao, CodAtual);
        RecuperaObjeto(CodigoNav);
    }
    
    public ArrayList<Venda> RecuperaObjetos(String pCampo, String pValor){
        String nomeCampo = "";
        
        if(pCampo.equalsIgnoreCase("Código")){
            nomeCampo = "ID";
        }else if(pCampo.equalsIgnoreCase("Código do Cliente")){
            nomeCampo = "ID_CLIENTE";
        }else if(pCampo.equalsIgnoreCase("Código do Vendedor")){
            nomeCampo = "ID_VENDEDOR";
        }else if(pCampo.equalsIgnoreCase("Valor")){
            nomeCampo = "VALOR";
        }else if(pCampo.equalsIgnoreCase("Método de Pagamento")){
            nomeCampo = "METODO_PAGAMENTO";
        }
        
        return VendaDAO.RecuperaObjetos(nomeCampo, pValor);
    }
}
