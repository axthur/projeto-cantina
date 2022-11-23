package Visao;

import Controle.ctrlVenda;
import Controle.ctrlCliente;
import Controle.ctrlItensVenda;
import Controle.ctrlProduto;
import DAO.ItensVendaDAO;
import static DAO.ItensVendaDAO.PegaProximoCodigo;
import Modelo.ItensVenda;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class vCadVenda extends javax.swing.JDialog {

    DefaultTableModel objTabela;
    boolean Flag;
    private int RetornoConsulta;
    Vector<String> vColunas = new Vector<String>();
    private double valorTotalPedido = 0;

    private static final int FECHADO = -1;
    private static final int ABERTO = 0;
    private static final int INSERCAO = 1;
    private static final int EDICAO = 2;

    private static int statusRegistro;

    public int getRetornoConsulta() {
        return this.RetornoConsulta;
    }

    public void setRetornoConsulta(int RetornoConsulta) {
        this.RetornoConsulta = RetornoConsulta;
        this.txtCodigo.setText(String.valueOf(RetornoConsulta));
        txtCodigoFocusLost(null);
    }

    /**
     * Creates new form vCadVenda
     */
    public vCadVenda(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        setStatusRegistro(FECHADO);
        Flag = true;

        vColunas = PegaColunasDaGrade();
        objTabela = new DefaultTableModel(vColunas, 0);
        tblItens.setModel(objTabela);
        tblItens.getColumnModel().getColumn(0).setMinWidth(0);
        tblItens.getColumnModel().getColumn(0).setMaxWidth(0);

        TrocaEstadoBotao(1);
    }

    private void habilitarBotoes() {

        boolean habilitar = (statusRegistro == ABERTO || statusRegistro == FECHADO);

        btnIncluir.setEnabled(habilitar);
        btnExcluir.setEnabled(habilitar);
        btnSalvar.setEnabled(!habilitar);
        btnCancelar.setEnabled(!habilitar);
        btnPesquisar.setEnabled(habilitar);
        txtCodigo.setEnabled(habilitar);

        Component[] c = PnNavegacao.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(habilitar);
        }
    }

    private void limparCampos() {
        txtCodigo.setText("0");
        txtCodigoCliente.setText("");
        txtCodigoVendedor.setText("");
        txtDataCompra.setText("");
        txtDataPagamento.setText("");
        labTotal.setText("0");
        txtMetodoDePagamento.setText("");
    }

    private void setStatusRegistro(int status) {
        statusRegistro = status;
        habilitarBotoes();

        if (status == INSERCAO || status == FECHADO) {
            limparCampos();
        }
    }

    private void PreencherTelaComObjetoRecuperado(ArrayList<String> Registro) {
        if (!Registro.get(0).equals("-1")) {
            txtCodigo.setText(Registro.get(0));
            txtCodigoCliente.setText(Registro.get(1));
            txtCodigoVendedor.setText(Registro.get(2));
            txtDataCompra.setText(Registro.get(3));
            txtDataPagamento.setText(Registro.get(4));
            txtMetodoDePagamento.setText(Registro.get(5));
            labTotal.setText(Registro.get(6));
        }
    }
    
    private void PreencherTelaComItensRecuperados(ArrayList<ItensVenda> Itens){
        if(!Itens.isEmpty()){
            for(int i = 0; i < Itens.size(); i++){
                Vector<String> vetVetor = new Vector<String>();
                vetVetor.addElement("");
                vetVetor.addElement(String.valueOf(Itens.get(i).getCodigoProduto()));
                vetVetor.addElement(Itens.get(i).getNomeProduto());
                vetVetor.addElement(String.valueOf(Itens.get(i).getQuantidade()));
                vetVetor.addElement(String.valueOf(Itens.get(i).getValorUnitario()));
                vetVetor.addElement(String.valueOf(Itens.get(i).getValorTotal()));
                objTabela.addRow(vetVetor);
            }
            tblItens.setModel(objTabela);
        }
    }

    private void navegarEntreRegistros(int opcao) {
        int codigoAtual = Integer.parseInt(txtCodigo.getText());
        if(codigoAtual == 0)
            codigoAtual++;

        // Armazenar informações da Venda em um vetor de string
        ctrlVenda controllerVenda = new ctrlVenda();        
        ArrayList<String> Registro = controllerVenda.RecuperaObjetoNavegacao(opcao, codigoAtual);
        
        // Armazenar todos os Itens Venda da Venda em um vetor
        ctrlItensVenda controllerItens = new ctrlItensVenda();
        ArrayList<ItensVenda> Itens = new ArrayList<>();
        
        int x;
        if(!Registro.isEmpty()){
            x = Integer.parseInt(Registro.get(0));
        } else {
            x = codigoAtual;
        }
        
        // Verificar em todos os registros de Itens Venda
        for(int idItens = 1; idItens <= controllerItens.Count(); idItens++){
            // Armazenar o objeto referente ao ID 
            ItensVenda objetoRecuperado = controllerItens.RecuperaObjetoParaExcluir(idItens);
            
            // Se o código da venda do Itens Venda do ID recebido pela variável idItens 
            if(objetoRecuperado.getCodigoVenda()
            // for igual ao ID da Venda
                        == x){
                // Adiciona o Itens Venda no vetor
                Itens.add(objetoRecuperado);
            }
        }
        // Limpar registros de vendas anteriores
        objTabela.setRowCount(0);
        tblItens.setModel(objTabela);
        
        // Preencher tabela com o vetor ItensVenda
        PreencherTelaComItensRecuperados(Itens);
        
        // Preencher tela com o vetor de informações da Venda
        PreencherTelaComObjetoRecuperado(Registro);
        
        txtCodigoCliente.requestFocus();
        setStatusRegistro(ABERTO);
    }

    private Vector PegaColunasDaGrade() {
        Vector<String> ColunasTabela = new Vector<>();
        ColunasTabela.add("");

        ColunasTabela.add("CÓD. PRODUTO");
        ColunasTabela.add("NOME");
        ColunasTabela.add("QUANTIDADE");
        ColunasTabela.add("VALOR UNITÁRIO");
        ColunasTabela.add("TOTAL");

        return ColunasTabela;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button2 = new java.awt.Button();
        button7 = new java.awt.Button();
        lbID2 = new javax.swing.JLabel();
        textField4 = new java.awt.TextField();
        txttel2 = new java.awt.TextField();
        tbltel2 = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        panel2 = new java.awt.Panel();
        panel3 = new java.awt.Panel();
        PnNavegacao = new java.awt.Panel();
        btnPrimeiro = new java.awt.Button();
        btnProximo = new java.awt.Button();
        btnAnterior = new java.awt.Button();
        btnUltimo = new java.awt.Button();
        lbID = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        conteudo = new java.awt.Panel();
        textField2 = new java.awt.TextField();
        textField3 = new java.awt.TextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        tblcurso = new javax.swing.JLabel();
        txtCodigoCliente = new java.awt.TextField();
        tblcurso2 = new javax.swing.JLabel();
        tbltel1 = new javax.swing.JLabel();
        tbltel3 = new javax.swing.JLabel();
        txtMetodoDePagamento = new java.awt.TextField();
        txtDataPagamento = new java.awt.TextField();
        txtCodigoVendedor = new java.awt.TextField();
        txtDataCompra = new java.awt.TextField();
        tbltel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtNomeProduto = new javax.swing.JTextField();
        btnPesquisarProduto = new javax.swing.JButton();
        txtCodProduto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtQuantidadeProduto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtValorUnitario = new javax.swing.JTextField();
        btnRemover = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        labTotal = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItens = new javax.swing.JTable();
        botao = new java.awt.Panel();
        btnIncluir = new java.awt.Button();
        btnSalvar = new java.awt.Button();
        btnCancelar = new java.awt.Button();
        btnExcluir = new java.awt.Button();
        btnPesquisar = new java.awt.Button();

        button2.setLabel("button2");

        button7.setLabel("Cancelar");

        lbID2.setText("ID da Venda:");
        lbID2.setToolTipText("");

        tbltel2.setText("Data do pagamento:");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de cliente");

        panel1.setLayout(new java.awt.BorderLayout());

        panel2.setLayout(new java.awt.BorderLayout());

        panel3.setLayout(new java.awt.BorderLayout());

        PnNavegacao.setBackground(new java.awt.Color(0, 200, 0));
        PnNavegacao.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnPrimeiro.setActionCommand("<<");
        btnPrimeiro.setLabel("<<");
        btnPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeiroActionPerformed(evt);
            }
        });

        btnProximo.setLabel(">");
        btnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoActionPerformed(evt);
            }
        });

        btnAnterior.setLabel("<");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnUltimo.setLabel(">>");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });

        lbID.setText("ID:");
        lbID.setToolTipText("");

        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PnNavegacaoLayout = new javax.swing.GroupLayout(PnNavegacao);
        PnNavegacao.setLayout(PnNavegacaoLayout);
        PnNavegacaoLayout.setHorizontalGroup(
            PnNavegacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNavegacaoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnPrimeiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        PnNavegacaoLayout.setVerticalGroup(
            PnNavegacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnNavegacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnNavegacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrimeiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PnNavegacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbID)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnProximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textField2.setText("textField2");

        textField3.setText("textField3");

        tblcurso.setText("ID do Cliente:");

        txtCodigoCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoClienteFocusLost(evt);
            }
        });
        txtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyPressed(evt);
            }
        });

        tblcurso2.setText("ID do Vendedor:");

        tbltel1.setText("Data do pagamento:");

        tbltel3.setText("Método de pagamento:");

        txtMetodoDePagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMetodoDePagamentoKeyPressed(evt);
            }
        });

        txtDataPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataPagamentoKeyPressed(evt);
            }
        });

        txtCodigoVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVendedorKeyPressed(evt);
            }
        });

        txtDataCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataCompraKeyPressed(evt);
            }
        });

        tbltel.setText("Data da compra:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblcurso)
                    .addComponent(tbltel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataCompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbltel1)
                    .addComponent(tblcurso2)
                    .addComponent(tbltel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMetodoDePagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(txtDataPagamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tblcurso)
                                    .addComponent(tblcurso2))
                                .addGap(16, 16, 16))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(txtCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tbltel)
                            .addComponent(tbltel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tbltel3)
                            .addComponent(txtMetodoDePagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informações da Venda", jPanel4);

        jPanel3.setFocusable(false);
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel3.setName(""); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(596, 260));

        btnPesquisarProduto.setText("jButton3");
        btnPesquisarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarProdutoActionPerformed(evt);
            }
        });

        txtCodProduto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodProdutoFocusLost(evt);
            }
        });
        txtCodProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodProdutoKeyPressed(evt);
            }
        });

        jLabel9.setText("Produto:");

        jLabel10.setText("Quantidade:");

        jLabel5.setText("Valor Unitário:");

        txtValorUnitario.setEnabled(false);

        btnRemover.setText("- REMOVER");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        btnAdicionar.setText("+ ADICIONAR");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        labTotal.setText("Total");
        labTotal.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                labTotalAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel11.setText("Total do Pedido:");

        tblItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblItens);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labTotal)
                        .addGap(86, 86, 86))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtCodProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                                            .addComponent(txtQuantidadeProduto))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addComponent(btnPesquisarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNomeProduto))))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnAdicionar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnRemover)))
                                .addGap(31, 31, 31))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisarProduto)
                    .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtQuantidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtValorUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionar)
                    .addComponent(btnRemover))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(labTotal))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Informações dos Produtos", jPanel3);

        javax.swing.GroupLayout conteudoLayout = new javax.swing.GroupLayout(conteudo);
        conteudo.setLayout(conteudoLayout);
        conteudoLayout.setHorizontalGroup(
            conteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(conteudoLayout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        conteudoLayout.setVerticalGroup(
            conteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
        );

        botao.setBackground(new java.awt.Color(0, 200, 0));

        btnIncluir.setLabel("Incluir");
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnSalvar.setLabel("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setLabel("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluir.setBackground(new java.awt.Color(255, 0, 0));
        btnExcluir.setLabel("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnPesquisar.setLabel("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botaoLayout = new javax.swing.GroupLayout(botao);
        botao.setLayout(botaoLayout);
        botaoLayout.setHorizontalGroup(
            botaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botaoLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        botaoLayout.setVerticalGroup(
            botaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(botaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(PnNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(botao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(conteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PnNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(conteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        new vPesqVenda(null, true, this).setVisible(true);
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        setStatusRegistro(INSERCAO);
        objTabela.setRowCount(0);
        tblItens.setModel(objTabela);
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (statusRegistro == INSERCAO) {
            setStatusRegistro(FECHADO);
        } else if (statusRegistro == EDICAO) {
            // Recarregar o registro
            int codigoAtual = Integer.parseInt(txtCodigo.getText());

            ctrlVenda controllerVenda = new ctrlVenda();
            ArrayList<String> Registro = controllerVenda.RecuperaObjeto(codigoAtual);

            PreencherTelaComObjetoRecuperado(Registro);

            setStatusRegistro(ABERTO);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (txtCodigoCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o cliente!");
        } else if (txtCodigoVendedor.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o vendedor!");
        } else {
            int codigoAtual = Integer.parseInt(txtCodigo.getText());

            ArrayList<String> Registro = new ArrayList<>();
            Registro.add(txtCodigo.getText());
            Registro.add(txtCodigoCliente.getText());
            Registro.add(txtCodigoVendedor.getText());
            Registro.add(labTotal.getText());
            Registro.add(txtDataCompra.getText());
            Registro.add(txtDataPagamento.getText());
            Registro.add(txtMetodoDePagamento.getText());

            int codigoNovo = 0;
            ctrlVenda controlePedido = new ctrlVenda();
            if (codigoAtual > 0) {
                controlePedido.Atualizar(Registro);
            } else {
                codigoNovo = controlePedido.Salvar(Registro);
                txtCodigo.setText(String.valueOf(codigoNovo));

                for (int i = 0; i < objTabela.getRowCount(); i++) {
                    ArrayList<String> RegistroItem = new ArrayList<String>();
                    //0 - ID (ItensVenda) 
                    RegistroItem.add(String.valueOf(PegaProximoCodigo()));
                    //1 - ID_VENDA
                    RegistroItem.add(txtCodigo.getText());
                    //2 - ID_PRODUTO
                    RegistroItem.add(objTabela.getValueAt(i, 1).toString());
                    //3 - NOME_PRODUTO
                    RegistroItem.add(objTabela.getValueAt(i, 2).toString());
                    //4 - QUANTIDADE
                    RegistroItem.add(objTabela.getValueAt(i, 3).toString());
                    //5 - VALOR_UNITARIO
                    RegistroItem.add(objTabela.getValueAt(i, 4).toString());
                    //6 - VALOR_TOTAL
                    RegistroItem.add(objTabela.getValueAt(i, 5).toString());

                    controlePedido.AdicionarItem(RegistroItem);
                }
            }

            TrocaEstadoBotao(1);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void PreencherTelaComObjRecuperado(int codigoAtual, int Opcao) {
        ctrlVenda controlePedido = new ctrlVenda();
        ArrayList<String> Registro = new ArrayList<String>();
        this.valorTotalPedido = 0;

        objTabela = new DefaultTableModel(vColunas, 0);

        if (Opcao == -1) {
            Registro = controlePedido.RecuperaObjeto(codigoAtual);
            objTabela = controlePedido.RecuperaItensPedido(objTabela, codigoAtual);
        } else {
            Registro = controlePedido.RecuperaObjetoNavegacao(Opcao, codigoAtual);
            objTabela = controlePedido.RecuperaItensPedido(objTabela, Integer.parseInt(Registro.get(0)));
        }

        if (!Registro.get(0).equals("-1")) {

            if (Opcao != -1) {
                txtCodigo.setText(Registro.get(0));
            }

            txtCodigoCliente.setText(Registro.get(1));
            txtDataCompra.setText(Registro.get(2));
            labTotal.setText(Registro.get(3));
            txtCodigoVendedor.setText(Registro.get(4));
            tblItens.setModel(objTabela);
            tblItens.getColumnModel().getColumn(0).setMinWidth(0);
            tblItens.getColumnModel().getColumn(0).setMaxWidth(0);
        } else {
            if (Opcao == -1) {
                JOptionPane.showMessageDialog(rootPane, "Registro não encontrado");
                limparCampos();
            }
        }
    }

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        int codigoAtual = 0;
        try {
            codigoAtual = Integer.parseInt(txtCodigo.getText());
        } catch (Exception e) {
            codigoAtual = 0;
        }

        if (codigoAtual > 0) {
            PreencherTelaComObjRecuperado(codigoAtual, -1);
        }

        TrocaEstadoBotao(1);
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            labTotal.requestFocus();
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        navegarEntreRegistros(0);
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        navegarEntreRegistros(1);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        navegarEntreRegistros(2);
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        navegarEntreRegistros(3);
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (statusRegistro == ABERTO) {
            int podeExcluir = JOptionPane.showConfirmDialog(rootPane, "Tem certeza que deseja excluir o registro?", "Meu Programa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (podeExcluir == 0) {
                ctrlItensVenda controllerItens = new ctrlItensVenda();
                int idVenda = 1; // Primeiro ID do ItensVenda

                // Enquanto o ID_VENDA do ItensVenda for igual ao ID da Venda a ser excluida
                while (controllerItens.RecuperaObjetoParaExcluir(idVenda).getCodigoVenda()
                        == Integer.parseInt(txtCodigo.getText())) {
                    //Exclui o ItensVenda
                    controllerItens.Excluir(idVenda);
                    idVenda++;
                }

                //Excluir Venda
                ctrlVenda controllerVenda = new ctrlVenda();
                controllerVenda.Excluir(Integer.parseInt(txtCodigo.getText()));
                setStatusRegistro(FECHADO);
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        double valorTotal = 0;

        if (txtCodProduto.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe o código do produto!");
        } else if (txtQuantidadeProduto.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Informe a quantidade do produto!");
        } else {
            Vector<String> vetVetor = new Vector<String>();
            ItensVendaDAO controllerItens = new ItensVendaDAO();

            vetVetor.addElement(String.valueOf(controllerItens.PegaProximoCodigo()));
            vetVetor.addElement(txtCodProduto.getText());
            vetVetor.addElement(txtNomeProduto.getText());
            vetVetor.addElement(txtQuantidadeProduto.getText());
            vetVetor.addElement(txtValorUnitario.getText());
            valorTotal = (Double.parseDouble(txtValorUnitario.getText()) * Integer.parseInt(txtQuantidadeProduto.getText()));
            vetVetor.addElement(String.valueOf(valorTotal));
            objTabela.addRow(vetVetor);

            tblItens.setModel(objTabela);
        }

        if (!txtCodigo.getText().equals("0")) {
            ctrlItensVenda controleItem = new ctrlItensVenda();
            ArrayList<String> registro = new ArrayList<String>();
            registro.add(txtCodigo.getText());
            registro.add(txtCodProduto.getText());
            registro.add(txtQuantidadeProduto.getText());
            registro.add(String.valueOf(valorTotal));
            registro.add(txtNomeProduto.getText());
            controleItem.AdicionarItem(registro);
        }

        labTotal.setText(String.valueOf(calculaTotal()));

        txtCodProduto.setText("");
        txtNomeProduto.setText("");
        txtQuantidadeProduto.setText("");
        txtValorUnitario.setText("");

        if (!btnCancelar.isEnabled()) {
            TrocaEstadoBotao(0);
        }

        /*Vector<String> vColunas = new Vector<>();
        vColunas = PegaColunasDaGrade();
        
        DefaultTableModel objTabela = new DefaultTableModel(vColunas, 0);
        ctrlItensVenda controller = new ctrlItensVenda();
        objTabela = controller.PesquisaObjeto(Parametros, objTabela);*/

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        int Linha = tblItens.getSelectedRow();

        if (Linha == -1) {
            JOptionPane.showMessageDialog(rootPane, "Selecione o registro desejado!");
        } else {
            if (!txtCodigo.getText().equals("0")) {
                ctrlVenda controleVenda = new ctrlVenda();
                controleVenda.ExcluirItem(Integer.parseInt(objTabela.getValueAt(Linha, 0).toString()));
            }

            objTabela.removeRow(Linha);

            tblItens.setModel(objTabela);

            if (!btnCancelar.isEnabled()) {
                TrocaEstadoBotao(0);
            }
        }

        labTotal.setText(String.valueOf(calculaTotal()));
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtCodProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodProdutoKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            txtNomeProduto.requestFocus();
        }

        if (!btnCancelar.isEnabled())
            TrocaEstadoBotao(0);
    }//GEN-LAST:event_txtCodProdutoKeyPressed

    private void txtCodProdutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodProdutoFocusLost
        if (!txtCodProduto.getText().equals("")) {
            ctrlVenda controleProduto = new ctrlVenda();
            ArrayList<String> registro = new ArrayList<String>();
            registro = controleProduto.RecuperaDadosProduto(Integer.parseInt(txtCodProduto.getText()));
            txtNomeProduto.setText(registro.get(0));
            txtValorUnitario.setText(registro.get(1));
        }
    }//GEN-LAST:event_txtCodProdutoFocusLost

    private void btnPesquisarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarProdutoActionPerformed
        new vPesqProduto(null, true, null, this).setVisible(true);
    }//GEN-LAST:event_btnPesquisarProdutoActionPerformed

    private void txtDataCompraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataCompraKeyPressed
        if (statusRegistro == ABERTO) {
            setStatusRegistro(EDICAO);
        }
    }//GEN-LAST:event_txtDataCompraKeyPressed

    private void txtCodigoVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVendedorKeyPressed
        if (statusRegistro == ABERTO) {
            setStatusRegistro(EDICAO);
        }
    }//GEN-LAST:event_txtCodigoVendedorKeyPressed

    private void txtDataPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataPagamentoKeyPressed
        if (statusRegistro == ABERTO) {
            setStatusRegistro(EDICAO);
        }
    }//GEN-LAST:event_txtDataPagamentoKeyPressed

    private void txtMetodoDePagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMetodoDePagamentoKeyPressed
        if (statusRegistro == ABERTO) {
            setStatusRegistro(EDICAO);
        }
    }//GEN-LAST:event_txtMetodoDePagamentoKeyPressed

    private void txtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyPressed
        if (statusRegistro == ABERTO) {
            setStatusRegistro(EDICAO);
        }
    }//GEN-LAST:event_txtCodigoClienteKeyPressed

    private void txtCodigoClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoClienteFocusLost

    }//GEN-LAST:event_txtCodigoClienteFocusLost

    private void labTotalAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_labTotalAncestorAdded

    }//GEN-LAST:event_labTotalAncestorAdded

    private void TrocaEstadoBotao(int iTagBotao) {
        Component[] c;
        switch (iTagBotao) {
            case 0:
                btnIncluir.setEnabled(false);
                btnExcluir.setEnabled(false);
                btnSalvar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnPesquisar.setEnabled(false);
                txtCodigo.setEnabled(false);
                c = PnNavegacao.getComponents();
                for (int i = 0; i < c.length; i++) {
                    c[i].setEnabled(false);
                }
                break;
            case 1:
                btnIncluir.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnSalvar.setEnabled(false);
                btnCancelar.setEnabled(false);
                btnPesquisar.setEnabled(true);
                txtCodigo.setEnabled(true);
                c = PnNavegacao.getComponents();
                for (int i = 0; i < c.length; i++) {
                    c[i].setEnabled(true);
                }
        }
    }

    private double calculaTotal() {
        double valorTotal = 0;

        for (int i = 0; i < objTabela.getRowCount(); i++) {
            valorTotal = valorTotal + Double.parseDouble(objTabela.getValueAt(i, 5).toString());
        }

        return valorTotal;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vCadVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vCadVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vCadVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vCadVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vCadVenda dialog = new vCadVenda(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Panel PnNavegacao;
    private java.awt.Panel botao;
    private javax.swing.JButton btnAdicionar;
    private java.awt.Button btnAnterior;
    private java.awt.Button btnCancelar;
    private java.awt.Button btnExcluir;
    private java.awt.Button btnIncluir;
    private java.awt.Button btnPesquisar;
    private javax.swing.JButton btnPesquisarProduto;
    private java.awt.Button btnPrimeiro;
    private java.awt.Button btnProximo;
    private javax.swing.JButton btnRemover;
    private java.awt.Button btnSalvar;
    private java.awt.Button btnUltimo;
    private java.awt.Button button2;
    private java.awt.Button button7;
    private java.awt.Panel conteudo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labTotal;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbID2;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    private javax.swing.JTable tblItens;
    private javax.swing.JLabel tblcurso;
    private javax.swing.JLabel tblcurso2;
    private javax.swing.JLabel tbltel;
    private javax.swing.JLabel tbltel1;
    private javax.swing.JLabel tbltel2;
    private javax.swing.JLabel tbltel3;
    private java.awt.TextField textField2;
    private java.awt.TextField textField3;
    private java.awt.TextField textField4;
    private javax.swing.JTextField txtCodProduto;
    private javax.swing.JTextField txtCodigo;
    private java.awt.TextField txtCodigoCliente;
    private java.awt.TextField txtCodigoVendedor;
    private java.awt.TextField txtDataCompra;
    private java.awt.TextField txtDataPagamento;
    private java.awt.TextField txtMetodoDePagamento;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtQuantidadeProduto;
    private javax.swing.JTextField txtValorUnitario;
    private java.awt.TextField txttel2;
    // End of variables declaration//GEN-END:variables
}
