package cch.view.widgets;

import static cch.utils.CoresApp.*;

import cch.http.ClienteHttp;
import cch.model.OpcoesCripto;
import cch.model.Ticker;
import cch.utils.IconLoader;
import cch.view.widgets.botao.BotaoColuna;
import cch.view.widgets.botao.BotaoRedondo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
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
    model.addColumn("Moeda");
    model.addColumn("Ticker");
    model.addColumn("Valor de Compra");
    model.addColumn("Valor de Venda");
    model.addColumn("");
    model.addColumn("");

    adicionarBotaoDeRemover(this);
    adicionarBotaoAtualizar(this);

    getTableHeader().setForeground(TEXT_PRIMARY);
    getTableHeader().setBackground(BACKGROUND_PRIMARY);
    getTableHeader().setReorderingAllowed(false);
    getTableHeader().setResizingAllowed(false);

    final var columModel = columnModel;

    columModel.getColumn(0).setMaxWidth(32);

    columnModel.getColumn(5).setMaxWidth(128);
    columnModel.getColumn(6).setMaxWidth(128);
    setRowHeight(40);

    setForeground(TEXT_SECONDARY);
    setBackground(BACKGROUND_SECONDARY);

    // Faz a tabela ocupar mais espaço na tela
    setPreferredScrollableViewportSize(new Dimension(600, 600));
    setFillsViewportHeight(true);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return column == 5 || column == 6;
  }

  // Adiciona um novo ticker abaixo dos existentes
  public void inserirNovoTicker(Ticker ticker) {
    model.insertRow(
        getRowCount(),
        new Object[] {
          // Adiciona um espaçamento nos índices
          "  " + (getRowCount() + 1),
          OpcoesCripto.getNomeExtensoPelaAbreviacao(ticker.getNome()),
          ticker.getNome(),
          ticker.getUltimoPrecoCompra(),
          ticker.getUltimoPrecoVenda(),
          IconLoader.getIcon("icons/refresh.png"),
          IconLoader.getIcon("icons/delete.png"),
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
    final var delete =
        new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {

            final int modelRow = Integer.parseInt(e.getActionCommand());
            final String nomeTicker = (String) model.getValueAt(modelRow, 2);

            final var pane = criarConfirmacaoDeletar(nomeTicker);

            final var dialog = pane.createDialog(tabela.getParent(), "Confirmação de remoção");
            dialog.setVisible(true);

            if (pane.getValue().equals(JOptionPane.YES_OPTION)) {
              model.removeRow(modelRow);
              atualizarIndices(modelRow);
            }
          }
        };
    final var button =
        new BotaoRedondo("Remover", ERROR_STRONG, TEXT_PRIMARY, ERROR_MEDIUM, ERROR_SOFT, false);
    button.setToolTipText("Remover essa moeda da listagem");
    new BotaoColuna(this, delete, 6, button);
  }

  private void adicionarBotaoAtualizar(Tabela tabela) {

    final var atualizar =
        new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {

            final int modelRow = Integer.parseInt(e.getActionCommand());
            final String nomeTicker = (String) model.getValueAt(modelRow, 2);

            final var pane = criarConfirmacaoAtualizar(nomeTicker);

            final var dialog = pane.createDialog(tabela.getParent(), "Confirmação de atualização");
            dialog.setVisible(true);

            if (pane.getValue().equals(JOptionPane.YES_OPTION)) {
              try {
                final Ticker ticker =
                    ClienteHttp.buscarTikcer(OpcoesCripto.getOpcaoPelaAbreviacao(nomeTicker));
                if (ticker == null) {
                  return;
                }

                model.setValueAt(ticker.getUltimoPrecoCompra(), modelRow, 3);
                model.setValueAt(ticker.getUltimoPrecoVenda(), modelRow, 4);

              } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
              }
            }
          }
        };
    final var button =
        new BotaoRedondo("Atualizar", BUTTON_SECONDARY, TEXT_PRIMARY, null, null, false);
    button.setToolTipText("Atualizar os preços desse ticker");
    new BotaoColuna(this, atualizar, 5, button);
  }

  private JOptionPane criarConfirmacaoDeletar(String nomeTicker) {
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
            "Você tem certeza que quer deletar o ticker: " + nomeTicker,
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

  private JOptionPane criarConfirmacaoAtualizar(String nomeTicker) {
    final BotaoRedondo cancelar =
        new BotaoRedondo("Cancelar", ERROR_STRONG, TEXT_PRIMARY, ERROR_MEDIUM, ERROR_SOFT);

    final BotaoRedondo atualizar =
        new BotaoRedondo(
            "Atualizar",
            BUTTON_SECONDARY,
            TEXT_SECONDARY,
            BUTTON_SECONDARY_HOVER,
            BUTTON_SECONDARY_HOVER);

    final BotaoRedondo[] botoes = {cancelar, atualizar};

    final JOptionPane confirmacao =
        new JOptionPane(
            "Você tem certeza que quer atualizar o ticker: " + nomeTicker,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION,
            null,
            botoes,
            botoes[1]);

    atualizar.addActionListener(_ -> confirmacao.setValue(JOptionPane.YES_OPTION));
    cancelar.addActionListener(_ -> confirmacao.setValue(JOptionPane.NO_OPTION));

    confirmacao.setBackground(BACKGROUND_PRIMARY);
    confirmacao.setForeground(TEXT_PRIMARY);
    return confirmacao;
  }

  private void atualizarIndices(int deletedIndex) {
    for (int i = deletedIndex; i < getRowCount(); i++) {
      final int indexAtual = Integer.parseInt(((String) model.getValueAt(i, 0)).replace(" ", ""));
      model.setValueAt("  " + (indexAtual - 1), i, 0);
    }
  }
}
