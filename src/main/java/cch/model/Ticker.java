package cch.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.json.JSONObject;

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
    return ultimoPrecoCompra.setScale(4, RoundingMode.DOWN);
  }

  public BigDecimal getUltimoPrecoVenda() {
    return ultimoPrecoVenda.setScale(4, RoundingMode.DOWN);
  }

  private void preencherPrecosPorJson(JSONObject json) {

    final var tickerObject = json.getJSONObject("ticker");

    this.ultimoPrecoCompra = new BigDecimal(tickerObject.getString("buy"));
    this.ultimoPrecoVenda = new BigDecimal(tickerObject.getString("sell"));
  }

  @Override
  public String toString() {
    return "Ticker{"
        + "nome='"
        + nome
        + '\''
        + ", ultimoPrecoCompra="
        + ultimoPrecoCompra
        + ", ultimoPrecoVenda="
        + ultimoPrecoVenda
        + '}';
  }
}
