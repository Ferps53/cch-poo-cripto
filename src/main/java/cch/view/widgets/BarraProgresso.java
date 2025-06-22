package cch.view.widgets;

import cch.utils.CoresApp;
import javax.swing.*;

public class BarraProgresso extends JProgressBar {

  public BarraProgresso() {
    super();
    setIndeterminate(true);
    setBackground(CoresApp.TEXT_SECONDARY);
    setForeground(CoresApp.BUTTON_PRIMARY_HOVER);
  }
}
