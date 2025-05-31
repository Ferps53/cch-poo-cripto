package cch.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author evand
 */
public class ClienteHttp {
    public String buscaDados(String url) throws IOException, InterruptedException {
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
}
