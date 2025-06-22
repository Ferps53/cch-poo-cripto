package cch.model;

public enum OpcoesCripto {
  BITCOIN("BTC", "Bitcoin"),
  ETHEREUM("ETH", "Ethereum"),
  SOLANA("SOL", "Solana"),
  XRP("XRP", "XRP"),
  THETER("USDT", "Theter"),
  USD_COIN("USDC", "USD Coin"),
  TRON("TRX", "TRON"),
  DOGECOIN("DOGE", "Dogecoin"),
  CARDANO("ADA", "Cardano"),
  WRAPPED_BITCOIN("WBTC", "Wrapped Bitcoin");

  private final String abreviacao;
  private final String nomeExtenso;

  public String getAbreviacao() {
    return abreviacao;
  }

  public String getNomeExtenso() {
    return nomeExtenso;
  }

  public static String getNomeExtensoPelaAbreviacao(String abreviacao) {
    for (final var item : values()) {
      if (abreviacao.equals(item.getAbreviacao())) {
        return item.getNomeExtenso();
      }
    }
    return null;
  }

  public static OpcoesCripto getOpcaoPelaAbreviacao(String abreviacao) {
    for (final var item : values()) {
      if (abreviacao.toUpperCase().equals(item.getAbreviacao())) {
        return item;
      }
    }
    return null;
  }

  OpcoesCripto(String abreviacao, String nomeExtenso) {
    this.abreviacao = abreviacao;
    this.nomeExtenso = nomeExtenso;
  }

  @Override
  public String toString() {
    return String.format("%s - %s", getNomeExtenso(), getAbreviacao());
  }
}
