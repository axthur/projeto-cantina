package Controle;

import Modelo.Produto;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ctrlProduto {
    private final Produto objProduto;
    
    public ctrlProduto(){
        this.objProduto = new Produto();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objProduto.setNome(pLista.get(1));
        this.objProduto.setTipo(pLista.get(2));
        this.objProduto.setDescricao(pLista.get(3));
        this.objProduto.setPreco(Double.parseDouble(pLista.get(4)));  
        this.objProduto.setEstoque(Integer.parseInt(pLista.get(5)));  
        this.objProduto.setProximoCodigo();
        this.objProduto.Salvar();
        return this.objProduto.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objProduto.getCodigo()));
        vetCampos.add(this.objProduto.getNome());
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
    
    public void Atualizar(ArrayList<String> pLista){
        this.objProduto.setCodigo(Integer.parseInt(pLista.get(0)));
        this.objProduto.setNome(pLista.get(1));
        this.objProduto.setTipo(pLista.get(2));
        this.objProduto.setDescricao(pLista.get(3));
        this.objProduto.setPreco(Double.parseDouble(pLista.get(4)));
        this.objProduto.setEstoque(Integer.parseInt(pLista.get(5)));
        
        this.objProduto.Atualizar();
    }
    
    public void Excluir(int Chave){
        this.objProduto.setCodigo(Chave);
        this.objProduto.Excluir(Chave);
    }
    
    public DefaultTableModel PesquisaObjeto(ArrayList<String> Parametros, DefaultTableModel ModeloTabela){
        String Campo = Parametros.get(0);
        String Valor = Parametros.get(1);
         
        ArrayList<Produto> Produtos = this.objProduto.RecuperaObjetos(Campo, Valor);
        
        Vector<String> vetVetor;
        Produto objProdutoBuffer;
        
        for(int i = 0; i < Produtos.size(); i++){
            vetVetor = new Vector<String>();
            objProdutoBuffer = Produtos.get(i);
            
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getCodigo()));
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getNome()));
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getTipo()));
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getDescricao()));
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getPreco()));
            vetVetor.addElement(String.valueOf(objProdutoBuffer.getEstoque()));
            
            ModeloTabela.addRow(vetVetor);
        }
        
        return ModeloTabela;
    }
}