package cch.view.frames;

import static cch.utils.CoresApp.*;

import cch.http.ClienteHttp;
import cch.model.OpcoesCripto;
import cch.model.Ticker;
import cch.utils.FontManager;
import cch.view.widgets.Carregamento;
import cch.view.widgets.Tabela;
import cch.view.widgets.botao.BotaoRedondo;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class AtualizarCriptoDialog extends JDialog {
  private final Tabela tabela;
  private final JPanel fundo;

  public AtualizarCriptoDialog(Tabela tabela) {
    this.tabela = tabela;
    fundo = new JPanel();
    fundo.setBackground(BACKGROUND_SECONDARY);
    fundo.setLayout(new BorderLayout());
    setResizable(false);
    setSize(new Dimension(300, 100));
    setAlwaysOnTop(true);
    setModalityType(ModalityType.APPLICATION_MODAL);
    setLocationRelativeTo(tabela);
    setTitle("Atualizar todos os Tickers");
    setContentPane(fundo);
    criarLayoutConfirmacao();
    setVisible(true);
  }

  private void criarLayoutConfirmacao() {
    final var texto =
        new JLabel("<html>Você tem certeza que quer atualizar todos os Tickers?</html>");
    texto.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 16));
    texto.setForeground(TEXT_PRIMARY);

    final JPanel painelBotoes = new JPanel();
    painelBotoes.setLayout(new GridLayout(1, 0, 64, 0));
    painelBotoes.setBackground(BACKGROUND_SECONDARY);

    final var sim =
        new BotaoRedondo(
            "Sim", BUTTON_SECONDARY, TEXT_PRIMARY, BUTTON_SECONDARY_HOVER, BUTTON_SECONDARY_HOVER);
    sim.addActionListener(
        _ -> {
          fundo.remove(painelBotoes);
          fundo.remove(texto);
          revalidate();
          repaint();
          fundo.add(new JLabel("Teste"));
          criarLayoutCarregameto();
        });

    final var nao = new BotaoRedondo("Não", ERROR_STRONG, TEXT_PRIMARY, ERROR_MEDIUM, ERROR_SOFT);
    nao.addActionListener(_ -> fechar());

    painelBotoes.add(nao);
    painelBotoes.add(sim);

    fundo.add(texto);
    fundo.add(painelBotoes, BorderLayout.PAGE_END);
  }

  private void criarLayoutCarregameto() {
    final var texto = new JLabel("<html>Aguarde enquanto os Tickers são atualizados</html>");
    texto.setFont(FontManager.getFont().deriveFont(Font.PLAIN, 16));
    texto.setForeground(TEXT_PRIMARY);
    final var carregamento =
        new Carregamento(getWidth() - 16, 16, FlowLayout.CENTER, BACKGROUND_SECONDARY);
    fundo.add(texto);
    fundo.add(carregamento, BorderLayout.PAGE_END);
    revalidate();
    repaint();
    realizarAtualizacao(carregamento);
  }

  private void realizarAtualizacao(Carregamento carregamento) {
    final var model = tabela.getModel();

    // Thread para não travar a UI!
    Thread.ofVirtual()
        .start(
            () -> {
              for (int i = 0; i < model.getRowCount(); i++) {
                final String nomeAbreviado = (String) model.getValueAt(i, 2);
                final OpcoesCripto tipoTicker = OpcoesCripto.getOpcaoPelaAbreviacao(nomeAbreviado);

                if (tipoTicker == null) {
                  continue;
                }

                carregamento.setTextoLabel("Atualizando: " + tipoTicker.getNomeExtenso());

                try {
                  final Ticker tickerAtualizado = ClienteHttp.buscarTikcer(tipoTicker);
                  if (tickerAtualizado == null) {
                    continue;
                  }

                  model.setValueAt(tickerAtualizado.getUltimoPrecoCompra(), i, 3);
                  model.setValueAt(tickerAtualizado.getUltimoPrecoVenda(), i, 4);

                } catch (IOException | InterruptedException e) {
                  e.printStackTrace();
                  return;
                }
              }
              carregamento.concluir();
              try {
                Thread.sleep(1000);
                fechar();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  private void fechar() {
    setVisible(false);
    dispose();
  }
}
