package cch.view.frames;

import cch.http.ClienteHttp;
import cch.model.OpcoesCripto;
import cch.model.Ticker;
import cch.utils.CoresApp;
import cch.view.widgets.PainelErro;
import cch.view.widgets.Tabela;
import java.io.IOException;
import javax.swing.*;
import org.json.JSONObject;

public class AdicionarCriptoDialog extends JDialog {
  private static final ClienteHttp CLIENTE = new ClienteHttp();
  private final Tabela tabela;
  private final JPanel painel;

  public AdicionarCriptoDialog(Tabela tabela) {

    this.tabela = tabela;
    setResizable(false);
    setAlwaysOnTop(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setSize(400, 400);
    setLocationRelativeTo(tabela.getRootPane());
    setTitle("Adicionar nova moeda");
    painel = new JPanel();
    painel.setBackground(CoresApp.BACKGROUND_SECONDARY);

    final var button = new JButton("Adicionar Criptomoeda");
    button.addActionListener(
        _ -> {
          for (final var opcao : OpcoesCripto.values()) {
            adicionarNovaCriptoMoeda(opcao);
          }
        });

    painel.add(button);
    // faz o painel cobrir todo o frame
    setContentPane(painel);
    setVisible(true);
  }

  private void adicionarNovaCriptoMoeda(OpcoesCripto opcoesCripto) {

    // Verifica se a moeda já foi adicionada na tabela
    for (final var nomeNaTabela : tabela.getNomeTickers()) {
      if (nomeNaTabela.equals(opcoesCripto.getAbreviacao())) {
        final var painelErro =
            new PainelErro(
                "A moeda " + opcoesCripto.getAbreviacao() + " já foi cadastrada!", painel);
        painel.add(painelErro);
        // revalidate() e repaint(), fazem a dialog ser recalculado e pintar novamente os
        // componentes.
        // Nesse caso faz o painel de erro aparecer.
        revalidate();
        repaint();
        return;
      }
    }

    // Inicia a chamada do endpoint em uma nova thread. Dessa forma usa de paralelismo para não
    // travar a UI!
    Thread.ofVirtual()
        .start(
            () -> {
              try {
                final String body =
                    CLIENTE.buscaDados(
                        "https://www.mercadobitcoin.net/api/"
                            + opcoesCripto.getAbreviacao()
                            + "/ticker");
                final JSONObject json = new JSONObject(body);
                final Ticker ticker = new Ticker(opcoesCripto.getAbreviacao(), json);
                tabela.inserirNovoTicker(ticker);
              } catch (IOException | InterruptedException e) {
                final var painelErro = new PainelErro(e.getMessage(), painel);
                painel.add(painelErro);
                // revalidate() e repaint(), fazem a dialog ser recalculado e pintar novamente os
                // componentes.
                // Nesse caso faz o painel de erro aparecer.
                revalidate();
                repaint();
              }
            });
  }
}
