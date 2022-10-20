package Modelo;

import DAO.VendedorDAO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author arthu
 */
public class Vendedor {
    private int id;
    private String nome;
    private String telefone;    
    private String email;
    private String endereco;
    private int cargaHoraria;

    public Vendedor(){
        this.id = -1;
        this.nome = "";
        this.telefone = "";
        this.email = "";
        this.endereco = "";
        this.cargaHoraria = -1;
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
    
    public void RecuperaObjeto(int Codigo){
        Vendedor vendedorTemp = VendedorDAO.RecuperarVendedor(Codigo);
        this.setCodigo(vendedorTemp.getCodigo());
        this.setNome(vendedorTemp.getNome());
        this.setTelefone(vendedorTemp.getTelefone());
        this.setEmail(vendedorTemp.getEmail());
        this.setEndereco(vendedorTemp.getEndereco());
        this.setCargaHoraria(vendedorTemp.getCargaHoraria());
    }
}
