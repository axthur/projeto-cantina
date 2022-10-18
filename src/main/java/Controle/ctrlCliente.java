package Controle;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.ctrlCliente to edit this template
 */


/**
 *
 * @author arthu
 */
public class ctrlCliente {
    private final Cliente objCliente;
    
    public ctrlCliente(){
        this.objCliente = new Cliente();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objCliente.setNome(pLista.get(1));
        this.objCliente.setTelefone(pLista.get(2));
        this.objCliente.setEmail(pLista.get(3));
        this.objCliente.setEndereco(pLista.get(3));
        this.objCliente.setCurso(pLista.get(3));        
        this.objCliente.setProximoCodigo();
        this.objCliente.Salvar();
        return this.objCliente.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objCliente.getCodigo()));
        vetCampos.add(this.objCliente.getNome());
        vetCampos.add(this.objCliente.getTelefone());
        vetCampos.add(this.objCliente.getEmail());
        vetCampos.add(this.objCliente.getEndereco());
        vetCampos.add(this.objCliente.getCurso());
                
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objCliente.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
}