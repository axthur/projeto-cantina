package Modelo;

import DAO.VendedorDAO;

public class Vendedor {
    private int id;
    private String nome;
    private String telefone;    
    private String email;
    private String endereco;
    private int cargaHoraria;
    private int senha;

    public Vendedor(){
        this.id = -1;
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.endereco = "";
        this.cargaHoraria = -1;
        this.senha = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    } 
    public void setProximoCodigo(){
        this.id = VendedorDAO.ProximoCodigo();
    }
    
    public void Salvar(){
        VendedorDAO.Salvar(this);
    }
    
    public void Atualizar(){
        VendedorDAO.Atualizar(this);
    }
    
    public void Excluir(int Chave){
        VendedorDAO.Excluir(Chave);
    }
    
    public void RecuperaObjeto(int Codigo){
        Vendedor vendedorTemp = VendedorDAO.RecuperarVendedor(Codigo);
        this.setCodigo(vendedorTemp.getCodigo());
        this.setNome(vendedorTemp.getNome());
        this.setTelefone(vendedorTemp.getTelefone());
        this.setEmail(vendedorTemp.getEmail());
        this.setEndereco(vendedorTemp.getEndereco());
        this.setCargaHoraria(vendedorTemp.getCargaHoraria());
        this.setSenha(vendedorTemp.getSenha());
    }
    
    public void RecuperaObjetoNavegacao(int Opcao, int CodAtual){
        int CodigoNav = VendedorDAO.PegaCodigoPelaNavegacao(Opcao, CodAtual);
        RecuperaObjeto(CodigoNav);
    }
}
