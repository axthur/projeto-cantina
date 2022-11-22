package Controle;

import Modelo.ItensVenda;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ctrlItensVenda {
    private final ItensVenda objItensVenda;
    
    public ctrlItensVenda(){
        this.objItensVenda = new ItensVenda();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objItensVenda.setCodigo(Integer.parseInt(pLista.get(1)));
        this.objItensVenda.setCodigoVenda(Integer.parseInt(pLista.get(2)));
        this.objItensVenda.setCodigoProduto(Integer.parseInt(pLista.get(3)));
        this.objItensVenda.setQuantidade(Integer.parseInt(pLista.get(4)));
        this.objItensVenda.setValorUnitario(Double.parseDouble(pLista.get(5)));        
        this.objItensVenda.setValorTotal(Double.parseDouble(pLista.get(6)));        
        
        //this.objItensVenda.setProximoCodigo();
        this.objItensVenda.Salvar();
        return this.objItensVenda.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objItensVenda.getCodigo()));
        vetCampos.add(String.valueOf(this.objItensVenda.getCodigoVenda()));
        vetCampos.add(String.valueOf(this.objItensVenda.getCodigoProduto()));
        vetCampos.add(String.valueOf(this.objItensVenda.getQuantidade()));
        vetCampos.add(String.valueOf(this.objItensVenda.getValorUnitario()));
        vetCampos.add(String.valueOf(this.objItensVenda.getValorTotal()));
                
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objItensVenda.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
    
    /*public ArrayList<String> RecuperaObjetoNavegacao(int Opcao, int Codigo){
        this.objItensVenda.RecuperaObjetoNavegacao(Opcao, Codigo);
        return ConverterObjetoParaArray();
    }*/
    
    public void Atualizar(ArrayList<String> pLista){
        this.objItensVenda.setCodigo(Integer.parseInt(pLista.get(0)));
        this.objItensVenda.setCodigoVenda(Integer.parseInt(pLista.get(1)));
        this.objItensVenda.setCodigoProduto(Integer.parseInt(pLista.get(2)));
        this.objItensVenda.setQuantidade(Integer.parseInt(pLista.get(3)));
        this.objItensVenda.setValorUnitario(Double.parseDouble(pLista.get(4)));
        this.objItensVenda.setValorTotal(Double.parseDouble(pLista.get(5)));
        
        this.objItensVenda.Atualizar();
    }
    
    public void Excluir(int Chave){
        this.objItensVenda.setCodigo(Chave);
        this.objItensVenda.Excluir(Chave);
    }
    
    /*public DefaultTableModel PesquisaObjeto(ArrayList<String> Parametros, DefaultTableModel ModeloTabela){
        String Campo = Parametros.get(0);
        String Valor = Parametros.get(1);
         
        ArrayList<ItensVenda> itens = this.objItensVenda.RecuperaObjetos(Campo, Valor);
        
        Vector<String> vetVetor;
        ItensVenda objItensVendaBuffer;
        
        for(int i = 0; i < itens.size(); i++){
            vetVetor = new Vector<String>();
            objItensVendaBuffer = itens.get(i);
            
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getCodigo()));
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getCodigoVenda()));
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getCodigoProduto()));
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getQuantidade()));
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getValorUnitario()));
            vetVetor.addElement(String.valueOf(objItensVendaBuffer.getValorTotal()));
            
            ModeloTabela.addRow(vetVetor);
        }
        
        return ModeloTabela;
    }*/
}