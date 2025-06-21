package cch.view.frames;

import cch.view.widgets.Frame;
import cch.view.widgets.Tabela;

import javax.swing.*;
import java.awt.*;

public class CriptoFrame extends Frame {

    private Tabela tabela;
    private JScrollPane scroll;

    public CriptoFrame() throws InterruptedException {
        super("Cripto");

        tabela = new Tabela();
        scroll = new JScrollPane(tabela);
        final var panel = new JPanel();
        panel.add(scroll);
        this.add(panel);
        panel.setSize(800, 600);

        final var adicionarMoedas = new JButton("+");
        adicionarMoedas.setAlignmentX(1);
        adicionarMoedas.setAlignmentY(1);
        adicionarMoedas.addActionListener(_ -> {
            new AdicionarCriptoDialog(this.tabela);
        });

        panel.add(adicionarMoedas);

        this.setVisible(true);
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
}
