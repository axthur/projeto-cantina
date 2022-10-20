package Controle;

import Modelo.Produto;
import java.util.ArrayList;

public class ctrlProduto {
    private final Produto objCliente;
    
    public ctrlProduto(){
        this.objCliente = new Produto();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objCliente.setNome(pLista.get(1));
        this.objCliente.setCodigo(Integer.valueOf(pLista.get(2)));
        this.objCliente.setTipo(pLista.get(3));
        this.objCliente.setDescricao(pLista.get(4));
        this.objCliente.setPreco(Double.valueOf(pLista.get(5)));  
         this.objCliente.setEstoque(Integer.valueOf(pLista.get(6)));  
        this.objCliente.setProximoCodigo();
        this.objCliente.Salvar();
        return this.objCliente.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objCliente.getCodigo()));
        vetCampos.add(this.objCliente.getNome());
        vetCampos.add(String.valueOf(this.objCliente.getCodigo()));
        vetCampos.add(this.objCliente.getTipo());
        vetCampos.add(this.objCliente.getDescricao());
        vetCampos.add(String.valueOf(this.objCliente.getPreco()));
        vetCampos.add(String.valueOf(this.objCliente.getEstoque()));
                
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objCliente.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
}
