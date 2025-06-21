package cch.view.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PainelErro extends PainelBordaArredondada {

    private static final Color COR_DO_TEXTO = new Color(0xFF2222);
    private static final Color COR_DE_FUNDO = new Color(0xAC322C);

    public PainelErro(String mensagem, JComponent pai) {

        super(COR_DE_FUNDO);
        final var texto = new JLabel(mensagem);
        texto.setForeground(COR_DO_TEXTO);
        add(texto);
        final var fechar = new JButton("X");
        fechar.setForeground(Color.white);
        fechar.setBackground(COR_DO_TEXTO);
        fechar.addActionListener(criarEventoDeFechar(pai));
        add(fechar);
    }

    ///  Remove a si mesmo do componente pai
    private ActionListener criarEventoDeFechar(JComponent pai) {
        return _ -> {
            pai.remove(this);
            pai.revalidate();
            pai.repaint();
        };
    }
}
