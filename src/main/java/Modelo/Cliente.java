package Modelo;

import DAO.ClienteDAO;
import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private String curso;

    public Cliente(){
        this.id = -1;
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.endereco = "";
        this.curso = "";
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

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    
    public void setProximoCodigo(){
        this.id = ClienteDAO.ProximoCodigo();
    }
    
    public void Salvar(){
        ClienteDAO.Salvar(this);
    }
    
    public void Atualizar(){
        ClienteDAO.Atualizar(this);
    }
    
    public void Excluir(int Chave){
        ClienteDAO.Excluir(Chave);
    }
    
    
    public void RecuperaObjeto(int Codigo){
        Cliente clienteTemp = ClienteDAO.RecuperarCliente(Codigo);
        this.setCodigo(clienteTemp.getCodigo());
        this.setNome(clienteTemp.getNome());
        this.setTelefone(clienteTemp.getTelefone());
        this.setEmail(clienteTemp.getEmail());
        this.setEndereco(clienteTemp.getEndereco());
        this.setCurso(clienteTemp.getCurso());
    }
    
    public void RecuperaObjetoNavegacao(int Opcao, int CodAtual){
        int CodigoNav = ClienteDAO.PegaCodigoPelaNavegacao(Opcao, CodAtual);
        RecuperaObjeto(CodigoNav);
    }
    
    public ArrayList<Cliente> RecuperaObjetos(String pCampo, String pValor){
        String nomeCampo = "";
        
        if(pCampo.equalsIgnoreCase("C??digo")){
            nomeCampo = "ID";
        }else if(pCampo.equalsIgnoreCase("Nome")){
            nomeCampo = "NOME";
        }else if(pCampo.equalsIgnoreCase("Telefone")){
            nomeCampo = "TELEFONE";
        }else if(pCampo.equalsIgnoreCase("E-mail")){
            nomeCampo = "EMAIL";
        }else if(pCampo.equalsIgnoreCase("Endere??o")){
            nomeCampo = "ENDERECO";
        }else if(pCampo.equalsIgnoreCase("Curso")){
            nomeCampo = "CURSO";
        }
        
        return ClienteDAO.RecuperaObjetos(nomeCampo, pValor);
    }
}