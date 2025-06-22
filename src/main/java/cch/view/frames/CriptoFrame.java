package cch.view.frames;

import static cch.utils.CoresApp.*;

import cch.view.widgets.BotaoRedondo;
import cch.view.widgets.Frame;
import cch.view.widgets.Tabela;
import java.awt.*;
import javax.swing.*;

public class CriptoFrame extends Frame {

  private final Tabela tabela;

  public CriptoFrame() throws InterruptedException {
    super("Cripto");

    tabela = new Tabela();
    final var scroll = new JScrollPane(tabela);
    final var fundo = new JPanel();
    fundo.setLayout(new BorderLayout());
    fundo.add(scroll);
    fundo.setBackground(BACKGROUND_PRIMARY);

    fundo.add(criarBarraSuperior(), BorderLayout.PAGE_START);

    this.setContentPane(fundo);
    this.setVisible(true);
  }

  // Clean code
  private JPanel criarBarraSuperior() {
    final var sidepanel = new JPanel();
    sidepanel.setSize(getWidth(), 100);
    sidepanel.setBackground(BACKGROUND_PRIMARY);
    sidepanel.setLayout(new GridLayout(1, 0, 64, 0));

    final var titulo = new JLabel("Visualizador de Cripto");
    titulo.setForeground(TEXT_PRIMARY);
    titulo.setHorizontalTextPosition(SwingConstants.CENTER);

    final var atualizarValores =
        new BotaoRedondo(
            "Atualizar valores",
            BUTTON_SECONDARY,
            TEXT_SECONDARY,
            BUTTON_SECONDARY_HOVER,
            BUTTON_SECONDARY_HOVER);
    atualizarValores.setToolTipText("Atualizar os valores das moedas da tabela abaixo");

    final var adicionarMoedas =
        new BotaoRedondo(
            "Adicionar moedas",
            BUTTON_PRIMARY,
            TEXT_PRIMARY,
            BUTTON_PRIMARY_HOVER,
            BUTTON_SECONDARY);
    adicionarMoedas.setToolTipText("Adicionar moedas na listagem da tabela abaixo");
    adicionarMoedas.addActionListener(_ -> new AdicionarCriptoDialog(this.tabela));

    sidepanel.add(titulo);
    sidepanel.add(atualizarValores);
    sidepanel.add(adicionarMoedas);
    return sidepanel;
  }

  @Override
  public void paintComponents(Graphics g) {
    super.paintComponents(g);

    Graphics2D graphics2D = (Graphics2D) g;
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
  }
}
