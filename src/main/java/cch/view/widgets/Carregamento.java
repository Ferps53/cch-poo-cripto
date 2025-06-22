package cch.view.widgets;

import cch.utils.CoresApp;
import java.awt.*;
import javax.swing.*;

public class Carregamento extends JPanel {

  private final BarraProgresso barraProgresso;

  public Carregamento(int largura, int altura, int posicao) {
    this(largura, altura, posicao, CoresApp.BACKGROUND_PRIMARY);
  }

  public Carregamento(int largura, int altura, int posicao, Color corDeFundo) {
    super();
    setBackground(corDeFundo);
    setLayout(new FlowLayout(posicao));
    this.barraProgresso = new BarraProgresso();
    this.barraProgresso.setPreferredSize(new Dimension(largura, altura));
    add(barraProgresso);
    barraProgresso.setString("Carregando...");
    barraProgresso.setStringPainted(true);
  }

  public void setTextoLabel(String text) {
    barraProgresso.setString(text);

    revalidate();
    repaint();
  }
}
