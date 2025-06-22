package cch.view.widgets.botao;

import cch.utils.CoresApp;
import java.awt.*;
import javax.swing.*;

public class BotaoErro extends BotaoRedondo {

  private static final Dimension DIMENSION = new Dimension(24, 24);

  public BotaoErro(ImageIcon icon) {
    super(
        icon,
        CoresApp.ERROR_STRONG,
        CoresApp.TEXT_PRIMARY,
        CoresApp.ERROR_MEDIUM,
        CoresApp.ERROR_SOFT);
    setPreferredSize(DIMENSION);
    setMaximumSize(DIMENSION);
    setMinimumSize(DIMENSION);
  }
}
