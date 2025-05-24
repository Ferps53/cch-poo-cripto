package cch.view.frames;

import cch.view.widgets.Frame;
import cch.view.widgets.Table;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class CriptoFrame extends Frame {

    private JPanel fundo;
    private Table tabela;
    private JScrollPane scroll;

    public CriptoFrame() {
        super("Cripto");

        fundo = new JPanel();
        tabela = new Table();
        scroll = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);

        fundo.setLayout(new GridLayout(1, 1));
        final var label = new JLabel("Testando a fonte Ubuntu");
        tabela.addColumn(new TableColumn());

        fundo.add(label);
        fundo.add(scroll);
        this.add(fundo);
        this.pack();
    }
}
