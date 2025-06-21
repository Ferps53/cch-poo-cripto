package cch.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ticker {

    private final String nome;
    private BigDecimal ultimoPrecoCompra;
    private BigDecimal ultimoPrecoVenda;

    public Ticker(String nome, JSONObject json) {
        this.nome = nome;
        preencherPrecosPorJson(json);
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getUltimoPrecoCompra() {
        return ultimoPrecoCompra.setScale(2, RoundingMode.DOWN);
    }

    public BigDecimal getUltimoPrecoVenda() {
        return ultimoPrecoVenda.setScale(2, RoundingMode.DOWN);
    }

    public void atualizarValores(JSONObject json) {
        preencherPrecosPorJson(json);
    }

    private void preencherPrecosPorJson(JSONObject json) {

        final var tickerObject = json.getJSONObject("ticker");

        this.ultimoPrecoCompra = new BigDecimal(tickerObject.getString("buy"));
        this.ultimoPrecoVenda = new BigDecimal(tickerObject.getString("sell"));

    }

    @Override
    public String toString() {
        return "Ticker{" +
                "nome='" + nome + '\'' +
                ", ultimoPrecoCompra=" + ultimoPrecoCompra +
                ", ultimoPrecoVenda=" + ultimoPrecoVenda +
                '}';
    }
}
