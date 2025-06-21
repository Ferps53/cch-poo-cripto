package cch.view.widgets;

import javax.swing.*;
import java.awt.*;

// Cria um painel com bordas redondas
public class PainelBordaArredondada extends JPanel {

    private final int raio;
    private Color corDeFundo;

    public PainelBordaArredondada(int raio) {
        super();
        this.raio = raio;
    }

    public PainelBordaArredondada() {
        super();
        this.raio = 16;
    }

    public PainelBordaArredondada(Color corDeFundo) {
        super();
        this.raio = 16;
        this.corDeFundo = corDeFundo;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(raio, raio);
        int largura = getWidth();
        int altura = getHeight();
        Graphics2D graphics2D = (Graphics2D) g;

        // Usa a cor de fundo padrão caso não for definida
        if (corDeFundo != null) {
            graphics2D.setColor(corDeFundo);
        } else {
            graphics2D.setColor(getBackground());
        }

        graphics2D.fillRoundRect(0, 0, largura - 1, altura - 1, arcs.width, arcs.height);
        graphics2D.setColor(getForeground());
        graphics2D.drawRoundRect(0, 0, largura - 1, altura - 1, arcs.width, arcs.height);
    }
}
