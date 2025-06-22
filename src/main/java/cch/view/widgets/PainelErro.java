package cch.view.widgets;

import static cch.utils.CoresApp.*;

import cch.utils.FontManager;
import cch.utils.IconLoader;
import cch.view.widgets.botao.BotaoErro;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PainelErro extends PainelBordaArredondada {

  private static final ImageIcon X =
      new ImageIcon(
          IconLoader.getIcon("icons/close.png")
              .getImage()
              .getScaledInstance(16, 16, Image.SCALE_SMOOTH));

  public PainelErro(String mensagem, JComponent pai) {

    super(ERROR_SOFT);
    super.setForeground(ERROR_MEDIUM);
    final var texto = new JLabel(mensagem);
    texto.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 14));
    texto.setForeground(TEXT_PRIMARY);
    add(texto);
    final var fechar = new BotaoErro(X);
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
