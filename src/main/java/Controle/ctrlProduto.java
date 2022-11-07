package Controle;

import Modelo.Produto;
import java.util.ArrayList;

public class ctrlProduto {
    private final Produto objProduto;
    
    public ctrlProduto(){
        this.objProduto = new Produto();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objProduto.setNome(pLista.get(1));
        this.objProduto.setCodigo(Integer.valueOf(pLista.get(2)));
        this.objProduto.setTipo(pLista.get(3));
        this.objProduto.setDescricao(pLista.get(4));
        this.objProduto.setPreco(Double.valueOf(pLista.get(5)));  
        this.objProduto.setEstoque(Integer.valueOf(pLista.get(6)));  
        this.objProduto.setProximoCodigo();
        this.objProduto.Salvar();
        return this.objProduto.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objProduto.getCodigo()));
        vetCampos.add(this.objProduto.getNome());
        vetCampos.add(String.valueOf(this.objProduto.getCodigo()));
        vetCampos.add(this.objProduto.getTipo());
        vetCampos.add(this.objProduto.getDescricao());
        vetCampos.add(String.valueOf(this.objProduto.getPreco()));
        vetCampos.add(String.valueOf(this.objProduto.getEstoque()));
                
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objProduto.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
    
    public ArrayList<String> RecuperaObjetoNavegacao(int Opcao, int Codigo){
        this.objProduto.RecuperaObjetoNavegacao(Opcao, Codigo);
        return ConverterObjetoParaArray();
    }
}
