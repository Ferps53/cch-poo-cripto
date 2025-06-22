package cch.view.frames;

import static cch.utils.CoresApp.*;

import cch.view.widgets.Frame;
import cch.view.widgets.Tabela;
import cch.view.widgets.botao.BotaoRedondo;
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
    // Adiciona a tabela preenchendo a tela
    fundo.add(scroll);
    fundo.setBackground(BACKGROUND_PRIMARY);

    // Adiciona a barra superior na parte superior da tela
    fundo.add(criarBarraSuperior(), BorderLayout.PAGE_START);
    // Adiciona o carregamento na parte inferior da tela

    this.setContentPane(fundo);
    this.setVisible(true);
  }

  // Clean code
  private JPanel criarBarraSuperior() {
    final var barraSuperior = new JPanel();
    barraSuperior.setSize(getWidth(), 100);
    barraSuperior.setBackground(BACKGROUND_PRIMARY);
    barraSuperior.setLayout(new GridLayout(1, 0, 64, 0));

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

    barraSuperior.add(titulo);
    barraSuperior.add(atualizarValores);
    barraSuperior.add(adicionarMoedas);
    return barraSuperior;
  }
}
