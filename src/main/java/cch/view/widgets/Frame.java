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
}
