package cch.view.widgets;

import java.awt.*;
import javax.swing.*;

/// Uma abstração para usar o [JFrame]. Esta classe permite adicionar métodos para facilitar o uso
/// do [JFrame]
/// @author Felipe Brostolin Ribeiro
public class Frame extends JFrame {

  public Frame(String titulo) {

    super(titulo);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setLayout(new FlowLayout());
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);

    // Garante bordas suaves e texto suave em toda a aplicação
    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
  }
}
