package cch.view.widgets;

import cch.utils.CoresApp;
import cch.utils.FontManager;
import java.awt.*;

public class BotaoErro extends BotaoRedondo {

  private static final Font FONT = FontManager.getFont().deriveFont(Font.BOLD, 6);
  private static final Dimension DIMENSION = new Dimension(24, 24);

  public BotaoErro(String text) {
    super(
        text,
        CoresApp.ERROR_STRONG,
        CoresApp.TEXT_PRIMARY,
        CoresApp.ERROR_MEDIUM,
        CoresApp.ERROR_SOFT);
    setPreferredSize(DIMENSION);
    setMaximumSize(DIMENSION);
    setMinimumSize(DIMENSION);

    setFont(FONT);
  }
}
