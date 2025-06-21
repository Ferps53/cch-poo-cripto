package cch.view.widgets;

import javax.swing.*;
import java.awt.event.ActionListener;

import static cch.utils.CoresApp.*;

public class PainelErro extends PainelBordaArredondada {

    public PainelErro(String mensagem, JComponent pai) {

        super(ERROR_SOFT);
        super.setForeground(ERROR_MEDIUM);
        final var texto = new JLabel(mensagem);
        texto.setForeground(TEXT_PRIMARY);
        add(texto);
        final var fechar = new BotaoErro("X");
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
