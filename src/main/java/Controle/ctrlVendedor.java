package Controle;

import Modelo.Vendedor;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ctrlVendedor {
    private final Vendedor objVendedor;
    
    public ctrlVendedor(){
        this.objVendedor = new Vendedor();
    }
    
    public int Salvar(ArrayList<String> pLista){
        this.objVendedor.setNome(pLista.get(1));
        this.objVendedor.setTelefone(pLista.get(2));
        this.objVendedor.setEmail(pLista.get(3));
        this.objVendedor.setEndereco(pLista.get(4));
        this.objVendedor.setCargaHoraria(Integer.parseInt(pLista.get(5)));
        this.objVendedor.setSenha(Integer.parseInt(pLista.get(6)));
        this.objVendedor.setProximoCodigo();
        this.objVendedor.Salvar();
        return this.objVendedor.getCodigo();
    }
    
    public ArrayList<String> ConverterObjetoParaArray(){
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objVendedor.getCodigo()));
        vetCampos.add(this.objVendedor.getNome());
        vetCampos.add(this.objVendedor.getTelefone());
        vetCampos.add(this.objVendedor.getEmail());
        vetCampos.add(this.objVendedor.getEndereco());
        vetCampos.add(String.valueOf(this.objVendedor.getCargaHoraria()));
        vetCampos.add(String.valueOf(this.objVendedor.getSenha()));
                
        return vetCampos;
    }
    
    public ArrayList<String> RecuperaObjeto(int Codigo){
        this.objVendedor.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }
    
    public ArrayList<String> RecuperaObjetoNavegacao(int Opcao, int Codigo){
        this.objVendedor.RecuperaObjetoNavegacao(Opcao, Codigo);
        return ConverterObjetoParaArray();
    }
    
    public void Atualizar(ArrayList<String> pLista){
        this.objVendedor.setCodigo(Integer.parseInt(pLista.get(0)));
        this.objVendedor.setNome(pLista.get(1));
        this.objVendedor.setTelefone(pLista.get(2));
        this.objVendedor.setEmail(pLista.get(3));
        this.objVendedor.setEndereco(pLista.get(4));
        this.objVendedor.setCargaHoraria(Integer.parseInt(pLista.get(5)));
        this.objVendedor.setSenha(Integer.parseInt(pLista.get(6)));
        
        this.objVendedor.Atualizar();
    }
    
    public void Excluir(int Chave){
        this.objVendedor.setCodigo(Chave);
        this.objVendedor.Excluir(Chave);
    }
    
    public DefaultTableModel PesquisaObjeto(ArrayList<String> Parametros, DefaultTableModel ModeloTabela){
        String Campo = Parametros.get(0);
        String Valor = Parametros.get(1);
         
        ArrayList<Vendedor> Vendedores = this.objVendedor.RecuperaObjetos(Campo, Valor);
        
        Vector<String> vetVetor;
        Vendedor objVendedorBuffer;
        
        for(int i = 0; i < Vendedores.size(); i++){
            vetVetor = new Vector<String>();
            objVendedorBuffer = Vendedores.get(i);
            
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getCodigo()));
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getNome()));
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getTelefone()));
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getEmail()));
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getEndereco()));
            vetVetor.addElement(String.valueOf(objVendedorBuffer.getCargaHoraria()));
            
            ModeloTabela.addRow(vetVetor);
        }
        
        return ModeloTabela;
    }
}