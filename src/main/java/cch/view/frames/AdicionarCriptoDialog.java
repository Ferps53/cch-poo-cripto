package cch.view.frames;

import cch.http.ClienteHttp;
import cch.model.OpcoesCripto;
import cch.utils.CoresApp;
import cch.view.widgets.Carregamento;
import cch.view.widgets.PainelErro;
import cch.view.widgets.Tabela;
import cch.view.widgets.botao.BotaoRedondo;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class AdicionarCriptoDialog extends JDialog {
  private final Tabela tabela;
  private final JPanel fundo;

  public AdicionarCriptoDialog(Tabela tabela) {

    this.tabela = tabela;
    setResizable(false);
    setAlwaysOnTop(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setLocationRelativeTo(tabela.getRootPane());
    setTitle("Adicionar nova moeda");
    fundo = criarFundo();
    // faz o painel cobrir todo o frame
    setContentPane(fundo);
    setSize(new Dimension(300, 100));
    setVisible(true);
  }

  private JPanel criarFundo() {
    final var painel = new JPanel();
    painel.setLayout(new BorderLayout());
    painel.setBackground(CoresApp.BACKGROUND_SECONDARY);

    painel.add(criarPainelCentro(), BorderLayout.CENTER);
    return painel;
  }

  private JPanel criarPainelCentro() {
    final var painelCentro = new JPanel();
    painelCentro.setBackground(CoresApp.BACKGROUND_SECONDARY);
    painelCentro.setForeground(CoresApp.TEXT_PRIMARY);
    painelCentro.setLayout(new FlowLayout());

    final var comboBox = new JComboBox<OpcoesCripto>();
    comboBox.setForeground(CoresApp.TEXT_PRIMARY);
    comboBox.setBackground(CoresApp.BACKGROUND_PRIMARY);

    for (final var opcao : OpcoesCripto.values()) {
      comboBox.addItem(opcao);
    }

    painelCentro.add(comboBox);

    final var button =
        new BotaoRedondo(
            "Adicionar Criptomoeda",
            CoresApp.BUTTON_SECONDARY,
            CoresApp.TEXT_PRIMARY,
            CoresApp.BUTTON_SECONDARY_HOVER,
            CoresApp.BUTTON_SECONDARY_HOVER);
    button.addActionListener(
        _ -> adicionarNovaCriptoMoeda((OpcoesCripto) comboBox.getSelectedItem(), this, button));

    painelCentro.add(button);
    return painelCentro;
  }

  private void adicionarNovaCriptoMoeda(
      OpcoesCripto opcoesCripto, AdicionarCriptoDialog dialog, BotaoRedondo button) {

    // Verifica se a moeda já foi adicionada na tabela
    for (final var nomeNaTabela : tabela.getNomeTickers()) {
      if (nomeNaTabela.equals(opcoesCripto.getAbreviacao())) {
        final var painelErro =
            new PainelErro(
                "A moeda " + opcoesCripto.getAbreviacao() + " já foi cadastrada!", fundo);
        fundo.add(painelErro);
        // revalidate() e repaint(), fazem a dialog ser recalculado e pintar novamente os
        // componentes.
        // Nesse caso faz o painel de erro aparecer.
        revalidate();
        repaint();
        return;
      }
    }
    final var barra =
        new Carregamento(getWidth() - 16, 16, FlowLayout.CENTER, CoresApp.BACKGROUND_SECONDARY);
    barra.setTextoLabel("Carregando: " + opcoesCripto.getNomeExtenso());

    // Inicia a chamada do endpoint em uma nova thread. Dessa forma usa de paralelismo para não
    // travar a UI!
    Thread.ofVirtual()
        .start(
            () -> {
              fundo.add(barra, BorderLayout.PAGE_END);
              button.setEnabled(false);
              revalidate();
              repaint();
              try {
                final var ticker = ClienteHttp.buscarTikcer(opcoesCripto);
                tabela.inserirNovoTicker(ticker);

                // Caso adicionar fechar dialog
                dialog.setVisible(false);
                dialog.dispose();

              } catch (IOException | InterruptedException e) {
                button.setEnabled(true);
                final var painelErro = new PainelErro(e.getMessage(), fundo);
                fundo.add(painelErro);
                // revalidate() e repaint(), fazem a dialog ser recalculado e pintar novamente os
                // componentes.
                // Nesse caso faz o painel de erro aparecer.
                revalidate();
                repaint();
              }
            });
  }
}
