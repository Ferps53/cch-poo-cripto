package cch.view.widgets.botao;

import cch.utils.CoresApp;
import java.awt.*;
import javax.swing.*;

public class BotaoRedondo extends JButton {
  private final int raio;
  private final Color corDeFundo;
  private final Color corDeHover;
  private final Color corPressionado;
  private final boolean animarMudancaDeCor;

  public BotaoRedondo(
      ImageIcon icon, Color corDeFundo, Color corDeTexto, Color corDeHover, Color corPressionado) {
    this(null, icon, corDeFundo, corDeTexto, corDeHover, corPressionado, true);
  }

  public BotaoRedondo(
      String text, Color corDeFundo, Color corDeTexto, Color corDeHover, Color corPressionado) {
    this(text, null, corDeFundo, corDeTexto, corDeHover, corPressionado, false);
  }

  public BotaoRedondo(
      String text,
      Color corDeFundo,
      Color corDeTexto,
      Color corDeHover,
      Color corPressionado,
      boolean animarMudancaDeCor) {
    this(text, null, corDeFundo, corDeTexto, corDeHover, corPressionado, animarMudancaDeCor);
  }

  public BotaoRedondo(
      String text,
      ImageIcon icon,
      Color corDeFundo,
      Color corDeTexto,
      Color corDeHover,
      Color corPressionado,
      boolean animarMudancaDeCor) {
    super(text, icon);
    this.corDeFundo = corDeFundo;
    this.corDeHover = corDeHover;
    this.corPressionado = corPressionado;
    this.animarMudancaDeCor = animarMudancaDeCor;

    setContentAreaFilled(false);
    setFocusPainted(false);
    setBorderPainted(false);
    raio = 32;
    setOpaque(false);
    setBackground(corDeFundo);
    setForeground(corDeTexto);
  }

  @Override
  protected void paintComponent(Graphics g) {

    // Muda as cores e o ponteiro do mouse conforme o estado
    final var model = getModel();
    g.setColor(corDeFundo);
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Isso causava um bug nos botões de remover na tabela
    if (animarMudancaDeCor) {
      if (model.isRollover()) {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.setColor(corDeHover);
      } else if (model.isPressed()) {
        g.setColor(corPressionado);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      } else {
        setCursor(Cursor.getDefaultCursor());
        g.setColor(corDeFundo);
      }
    }

    int largura = getWidth();
    int altura = getHeight();
    final var graphics2D = (Graphics2D) g;

    // Bordas suaves
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    graphics2D.fillRoundRect(0, 0, largura - 1, altura - 1, raio, raio);
    graphics2D.setColor(CoresApp.BORDER);
    graphics2D.drawRoundRect(0, 0, largura - 1, altura - 1, raio, raio);

    super.paintComponent(g);
  }
}
