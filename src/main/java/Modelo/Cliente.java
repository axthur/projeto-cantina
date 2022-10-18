package Modelo;

import DAO.ClienteDAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arthu
 */
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
    
    public void RecuperaObjeto(int Codigo){
        Cliente clienteTemp = ClienteDAO.RecuperarCliente(Codigo);
        this.setCodigo(clienteTemp.getCodigo());
        this.setNome(clienteTemp.getNome());
        this.setTelefone(clienteTemp.getTelefone());
        this.setEmail(clienteTemp.getEmail());
        this.setEndereco(clienteTemp.getEndereco());
        this.setCurso(clienteTemp.getCurso());
    }
    
}
