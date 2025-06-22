package cch;

import cch.utils.FontManager;
import cch.view.frames.CriptoFrame;
import java.util.logging.Logger;

public class Main {

  private Main() {}

  public static void main(String[] args) {

    FontManager.carregarFonte();

    try {
      new CriptoFrame();
    } catch (InterruptedException e) {
      Logger.getLogger("interrupt").severe(e.getMessage());
      // O plugin do Sonar me fez chegar aqui
      Thread.currentThread().interrupt();
    }
  }
}
