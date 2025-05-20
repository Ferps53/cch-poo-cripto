package cch;
import javax.swing.*;

public class Main {

  public static void main(String[] args) {

    final var janela = new JFrame("Cripto");
    janela.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    janela.setSize(800, 600);
    janela.setLocationRelativeTo(null);
    janela.setResizable(false);
    janela.setVisible(true);
  }
}