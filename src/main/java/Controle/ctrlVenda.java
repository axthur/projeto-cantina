package Controle;

import Modelo.Venda;
import java.util.ArrayList;

public class ctrlVenda {
    private final Venda objVenda;
    
    public ctrlVenda(){
        this.objVenda = new Venda();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objVenda.setCodigo(Integer.valueOf(pLista.get(1)));
        this.objVenda.setIdCliente(Integer.valueOf(pLista.get(2)));
        this.objVenda.setIdVendedor(Integer.valueOf(pLista.get(3)));
        this.objVenda.setValor(Double.valueOf(pLista.get(4)));
        this.objVenda.setDataCompra(pLista.get(5));
        this.objVenda.setDataPagamento(pLista.get(6));    
        this.objVenda.setMetodoDePagamento(pLista.get(7));            
        this.objVenda.setProximoCodigo();
        this.objVenda.Salvar();
        return this.objVenda.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objVenda.getCodigo()));
        vetCampos.add(String.valueOf(this.objVenda.getIdCliente()));
        vetCampos.add(String.valueOf(this.objVenda.getIdVendedor()));
        vetCampos.add(String.valueOf(this.objVenda.getValor()));
        vetCampos.add(this.objVenda.getDataCompra());
        vetCampos.add(this.objVenda.getDataPagamento());
        vetCampos.add(this.objVenda.getMetodoDePagamento());
        
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objVenda.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
    
     public ArrayList<String> RecuperaObjetoNavegacao(int Opcao, int Codigo){
        this.objVenda.RecuperaObjetoNavegacao(Opcao, Codigo);
        return ConverterObjetoParaArray();
    }
}
