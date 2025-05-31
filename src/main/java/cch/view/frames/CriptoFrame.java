package cch.view.frames;

import cch.http.ClienteHttp;
import cch.model.Ticker;
import cch.view.widgets.Frame;
import cch.view.widgets.Table;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class CriptoFrame extends Frame {

    private Table tabela;
    private JScrollPane scroll;

    public CriptoFrame() throws InterruptedException {
        super("Cripto");

        final var cliente = new ClienteHttp();
        tabela = new Table();
        scroll = new JScrollPane(tabela);

        final var model = (DefaultTableModel) tabela.getModel();

        model.addColumn("Nome");
        model.addColumn("Valor de Compra");
        model.addColumn("Valor de Venda");
        try {
            final String body = cliente.buscaDados("https://www.mercadobitcoin.net/api/BTC/ticker");
            final JSONObject json = new JSONObject(body);
            final Ticker ticker = new Ticker("BTC", json);
            model.insertRow(0, new Object[]{ticker.getNome(), ticker.getUltimoPrecoCompra(), ticker.getUltimoPrecoVenda()});

            this.add(scroll);
        } catch (IOException e) {
            final var label = new JLabel("Erro ao buscar dados");
            this.add(label);
        }

        this.pack();
    }
}
