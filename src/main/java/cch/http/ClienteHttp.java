package cch.http;

import cch.model.OpcoesCripto;
import cch.model.Ticker;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * @author evand
 */
public class ClienteHttp {

  private static final String URL_BASE = "https://www.mercadobitcoin.net/api/";

  public static Ticker buscarTikcer(OpcoesCripto opcoesCripto)
      throws IOException, InterruptedException {
    if (opcoesCripto == null) {
      return null;
    }

    final String body = buscaDados(URL_BASE + opcoesCripto.getAbreviacao() + "/ticker");
    final JSONObject json = new JSONObject(body);
    return new Ticker(opcoesCripto.getAbreviacao(), json);
  }

  public static String buscaDados(String url) throws IOException, InterruptedException {
    final var endereco = URI.create(url);
    final HttpResponse<String> response;
    try (final var client = HttpClient.newHttpClient()) {
      final var request = HttpRequest.newBuilder(endereco).GET().build();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new IOException("Erro ao buscar dados: " + response.statusCode());
      }
    }

    return response.body();
  }

  private ClienteHttp() {}
}
