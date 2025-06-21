package cch.view.widgets;

import cch.model.Ticker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Abstração para poder trabalhar com a JTable de forma mais tranquila
public class Tabela extends JTable {

    final DefaultTableModel model;

    public Tabela() {
        super();
        model = (DefaultTableModel) getModel();

        model.addColumn("Nome");
        model.addColumn("Valor de Compra");
        model.addColumn("Valor de Venda");

        // Faz a tabela ocupar mais espaço na tela
        setPreferredScrollableViewportSize(new Dimension(700, 500));
        setFillsViewportHeight(true);
    }

    public void inserirNovoTicker(Ticker ticker) {
        model.insertRow(0, new Object[]{ticker.getNome(), ticker.getUltimoPrecoCompra(), ticker.getUltimoPrecoVenda()});
    }

    public List<String> getNomeTickers() {
        final var listNomes = new ArrayList<String>();
        for (int i = 0; i < model.getRowCount(); i++) {
            listNomes.add((String) model.getValueAt(i, 0));
        }
        return listNomes;
    }
}
