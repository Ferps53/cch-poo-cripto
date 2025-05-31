package cch.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ticker {

    private String nome;
    private BigDecimal ultimoPrecoCompra;
    private BigDecimal ultimoPrecoVenda;

    public Ticker(String nome, JSONObject json) {
        this.nome = nome;

        final var tickerObject = json.getJSONObject("ticker");

        this.ultimoPrecoCompra = new BigDecimal(tickerObject.getString("buy"));
        this.ultimoPrecoVenda = new BigDecimal(tickerObject.getString("sell"));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getUltimoPrecoCompra() {
        return ultimoPrecoCompra.setScale(2, RoundingMode.DOWN);
    }

    public void setUltimoPrecoCompra(BigDecimal ultimoPrecoCompra) {
        this.ultimoPrecoCompra = ultimoPrecoCompra;
    }

    public BigDecimal getUltimoPrecoVenda() {
        return ultimoPrecoVenda.setScale(2, RoundingMode.DOWN);
    }

    public void setUltimoPrecoVenda(BigDecimal ultimoPrecoVenda) {
        this.ultimoPrecoVenda = ultimoPrecoVenda;
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
