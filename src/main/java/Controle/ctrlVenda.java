package Controle;

import Modelo.Venda;
import Modelo.Produto;
import Modelo.Cliente;
import Modelo.ItensVenda;
import Modelo.Vendedor;

import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ctrlVenda {

    private final Venda objPedido;
    private final Produto objProduto;
    private final Cliente objCliente;
    private final ItensVenda objItem;
    private final Vendedor objUsuario;

    public ctrlVenda() {
        this.objPedido = new Venda();
        this.objCliente = new Cliente();
        this.objUsuario = new Vendedor();
        this.objProduto = new Produto();
        this.objItem = new ItensVenda();
    }

    public int Salvar(ArrayList<String> pLista) {
        this.objPedido.setIdCliente(Integer.parseInt(pLista.get(1)));
        this.objPedido.setIdVendedor(Integer.parseInt(pLista.get(2)));
        this.objPedido.setValor(Double.parseDouble(pLista.get(3)));
        this.objPedido.setDataCompra(pLista.get(4));
        this.objPedido.setDataPagamento(pLista.get(5));
        this.objPedido.setMetodoDePagamento(pLista.get(6));
        this.objPedido.setProximoCodigo();
        this.objPedido.Salvar();
        return this.objPedido.getCodigo();
    }

    public void AdicionarItem(ArrayList<String> pLista) {
        this.objItem.setCodigo(Integer.parseInt(pLista.get(0)));
        this.objItem.setCodigoVenda(Integer.parseInt(pLista.get(1)));
        this.objItem.setCodigoProduto(Integer.parseInt(pLista.get(2)));
        this.objItem.setNomeProduto(pLista.get(3));
        this.objItem.setQuantidade(Integer.parseInt(pLista.get(4)));
        this.objItem.setValorUnitario(Double.parseDouble(pLista.get(5)));
        this.objItem.setValorTotal(Double.parseDouble(pLista.get(6)));

        this.objItem.Salvar();
    }

    public void Atualizar(ArrayList<String> pLista) {
        this.objPedido.setCodigo(Integer.parseInt(pLista.get(0)));
        this.objPedido.setIdCliente(Integer.parseInt(pLista.get(1)));
        this.objPedido.setIdVendedor(Integer.parseInt(pLista.get(2)));
        this.objPedido.setValor(Double.parseDouble(pLista.get(3)));
        this.objPedido.setDataCompra(String.valueOf(pLista.get(4)));
        this.objPedido.setDataPagamento(String.valueOf(pLista.get(5)));
        this.objPedido.setMetodoDePagamento(String.valueOf(pLista.get(6)));

        this.objPedido.Atualizar();
    }

    public ArrayList<String> ConverterObjetoParaArray() {
        ArrayList<String> vetCampos = new ArrayList<>();
        vetCampos.add(String.valueOf(this.objPedido.getCodigo()));//0
        vetCampos.add(String.valueOf(this.objPedido.getIdCliente()));//1
        vetCampos.add(String.valueOf(this.objPedido.getIdVendedor()));//2
        vetCampos.add(this.objPedido.getDataCompra());//3
        vetCampos.add(this.objPedido.getDataPagamento());//4
        vetCampos.add(this.objPedido.getMetodoDePagamento());//5
        vetCampos.add(String.valueOf(this.objPedido.getValor()));//6

        return vetCampos;
    }

    public String RecuperaNomeCliente(int Codigo) {
        this.objCliente.RecuperaObjeto(Codigo);
        return this.objCliente.getNome();
    }

    public String RecuperaNomeUsuario(int Codigo) {
        this.objUsuario.RecuperaObjeto(Codigo);
        return this.objUsuario.getNome();
    }

    public ArrayList<String> RecuperaDadosProduto(int Codigo) {
        this.objProduto.RecuperaObjeto(Codigo);
        ArrayList<String> vetCampos = new ArrayList<String>();
        vetCampos.add(String.valueOf(this.objProduto.getNome()));
        vetCampos.add(String.valueOf(this.objProduto.getPreco()));
        return vetCampos;
    }

    public ArrayList<String> RecuperaObjeto(int Codigo) {
        this.objPedido.RecuperaObjeto(Codigo);
        return ConverterObjetoParaArray();
    }

    public boolean RecuperaItem(int iCod) {
        return this.objItem.RecuperaObjeto(iCod);
    }

    public void ExcluirItem(int iCod) {
        this.objItem.Excluir(iCod);
    }

    public DefaultTableModel RecuperaItensPedido(DefaultTableModel ModeloTabela, int iCod) {

        ArrayList<ItensVenda> Itens = this.objItem.RecuperaObjetos(iCod);

        Vector<String> vetVetor;
        ItensVenda objItens;
        for (int i = 0; i < Itens.size(); i++) {
            vetVetor = new Vector<String>();
            objItens = Itens.get(i);

            vetVetor.addElement(String.valueOf(objItens.getCodigo()));
            vetVetor.addElement(String.valueOf(objItens.getCodigoVenda()));
            vetVetor.addElement(String.valueOf(objItens.getCodigoProduto()));
            vetVetor.addElement(String.valueOf(objItens.getQuantidade()));
            vetVetor.addElement(String.valueOf(objItens.getValorTotal()));
            vetVetor.addElement(String.valueOf(objItens.getValorUnitario()));
            ModeloTabela.addRow(vetVetor);
        }

        return ModeloTabela;
    }

    public ArrayList<String> RecuperaObjetoNavegacao(int Opcao, int Codigo) {
        this.objPedido.RecuperaObjetoNavegacao(Opcao, Codigo);
        return ConverterObjetoParaArray();
    }

    public void Excluir(int Chave) {
        this.objPedido.setCodigo(Chave);
        this.objPedido.Excluir(Chave);
    }

    public DefaultTableModel PesquisaObjeto(ArrayList<String> Parametros, DefaultTableModel ModeloTabela) {
        String Campo = Parametros.get(0);
        String Valor = Parametros.get(1);

        ArrayList<Venda> Cidades = this.objPedido.RecuperaObjetos(Campo, Valor);

        Vector<String> vetVetor;
        Venda objPedidoBuffer;
        for (int i = 0; i < Cidades.size(); i++) {
            vetVetor = new Vector<String>();
            objPedidoBuffer = Cidades.get(i);

            vetVetor.addElement(String.valueOf(objPedidoBuffer.getCodigo()));
            vetVetor.addElement(String.valueOf(objPedidoBuffer.getIdCliente()));
            vetVetor.addElement(String.valueOf(objPedidoBuffer.getIdVendedor()));
            vetVetor.addElement(String.valueOf(objPedidoBuffer.getValor()));
            vetVetor.addElement(String.valueOf(objPedidoBuffer.getMetodoDePagamento()));

            ModeloTabela.addRow(vetVetor);
        }

        return ModeloTabela;
    }
}
