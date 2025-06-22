package cch.view.widgets;

import static cch.utils.CoresApp.*;

import cch.model.OpcoesCripto;
import cch.model.Ticker;
import cch.utils.IconLoader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Abstração para poder trabalhar com a JTable de forma mais tranquila
public class Tabela extends JTable {

  // Permite usar esse model sem precisar fazer cast
  final DefaultTableModel model;

  public Tabela() {
    super();
    model = (DefaultTableModel) getModel();

    model.addColumn("#");
    model.addColumn("Nome Extenso");
    model.addColumn("Nome Abreviado");
    model.addColumn("Valor de Compra");
    model.addColumn("Valor de Venda");
    model.addColumn("");

    adicionarBotaoDeRemover(this);

    getTableHeader().setForeground(TEXT_PRIMARY);
    getTableHeader().setBackground(BACKGROUND_PRIMARY);
    getTableHeader().setReorderingAllowed(false);
    getTableHeader().setResizingAllowed(false);

    setRowHeight(32);

    setBorder(BorderFactory.createLineBorder(BORDER, 2, true));

    setForeground(TEXT_SECONDARY);
    setBackground(BACKGROUND_SECONDARY);

    // Faz a tabela ocupar mais espaço na tela
    setPreferredScrollableViewportSize(new Dimension(600, 600));
    setFillsViewportHeight(true);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return column == 5;
  }

  public void inserirNovoTicker(Ticker ticker) {
    model.insertRow(
        0,
        new Object[] {
          getRowCount() + 1,
          OpcoesCripto.getNomeExtensoPelaAbreviacao(ticker.getNome()),
          ticker.getNome(),
          ticker.getUltimoPrecoCompra(),
          ticker.getUltimoPrecoVenda(),
          IconLoader.getIcon("icons/delete.png")
        });
  }

  public List<String> getNomeTickers() {
    final var listNomes = new ArrayList<String>();
    for (int i = 0; i < model.getRowCount(); i++) {
      listNomes.add((String) model.getValueAt(i, 2));
    }
    return listNomes;
  }

  private void adicionarBotaoDeRemover(Tabela tabela) {
    Action delete =
        new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {

            final int modelRow = Integer.parseInt(e.getActionCommand());
            final String nomeTicker = (String) model.getValueAt(modelRow, 2);

            final var pane = criarConfirmacao(nomeTicker);

            final var dialog = pane.createDialog(tabela.getParent(),"Confirmação de remoção");
            dialog.setVisible(true);

            if (pane.getValue().equals(JOptionPane.YES_OPTION)) {
              model.removeRow(modelRow);
            }
          }
        };

    new BotaoColuna(
        this,
        delete,
        5,
        new BotaoRedondo("Remover", ERROR_STRONG, TEXT_PRIMARY, ERROR_MEDIUM, ERROR_SOFT, false));
  }

  private JOptionPane criarConfirmacao(String nomeTicker) {
    final BotaoRedondo deletar =
        new BotaoRedondo("Deletar", ERROR_STRONG, TEXT_PRIMARY, ERROR_MEDIUM, ERROR_SOFT);
    final BotaoRedondo cancelar =
        new BotaoRedondo(
            "Cancelar",
            BACKGROUND_SECONDARY,
            TEXT_SECONDARY,
            BACKGROUND_PRIMARY,
            BACKGROUND_PRIMARY);
    final BotaoRedondo[] botoes = {deletar, cancelar};
    final JOptionPane confirmacao =
        new JOptionPane(
            "Você tem certeza que quer deletar a moeda: " + nomeTicker,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            null,
            botoes,
            botoes[1]);

    deletar.addActionListener(_ -> confirmacao.setValue(JOptionPane.YES_OPTION));

    cancelar.addActionListener(_ -> confirmacao.setValue(JOptionPane.NO_OPTION));

    confirmacao.setBackground(BACKGROUND_PRIMARY);
    confirmacao.setForeground(TEXT_PRIMARY);
    return confirmacao;
  }
}
