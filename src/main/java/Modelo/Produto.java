package Modelo;

import DAO.ProdutoDAO;

public class Produto {
    private int id;
    private String nome;
    private String tipo;
    private String descricao;
    private double preco;
    private int estoque;

    public Produto(){
        this.id = -1;
        this.nome = "";
        this.tipo = "";
        this.descricao = "";
        this.preco = -1;
        this.estoque = -1;
    }

    public int getCodigo() {
        return id;
    }

    public void setCodigo(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

 public void setProximoCodigo(){
        this.id = ProdutoDAO.ProximoCodigo();
    }
    
    public void Salvar(){
        ProdutoDAO.Salvar(this);
    }
    
    public void RecuperaObjeto(int Codigo){
        Produto clienteTemp = ProdutoDAO.RecuperarProduto(Codigo);
        this.setCodigo(clienteTemp.getCodigo());
        this.setNome(clienteTemp.getNome());
        this.setTipo(clienteTemp.getTipo());
        this.setDescricao(clienteTemp.getDescricao());
        this.setPreco(clienteTemp.getPreco());
        this.setEstoque(clienteTemp.getEstoque());
    }
    public void RecuperaObjetoNavegacao(int Opcao, int CodAtual){
        int CodigoNav = ProdutoDAO.PegaCodigoPelaNavegacao(Opcao, CodAtual);
        RecuperaObjeto(CodigoNav);
    }
}
