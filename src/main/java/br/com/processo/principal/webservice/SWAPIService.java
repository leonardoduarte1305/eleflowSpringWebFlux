package br.com.processo.principal.webservice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.processo.principal.document.Planeta;
import io.github.cdimascio.dotenv.Dotenv;
import reactor.core.publisher.Flux;

public class SWAPIService {

	private static Dotenv env = Dotenv.configure().load();
	private static String URL_BASE = env.get("URL_BASE");

	private HttpClient client;
	private ObjectMapper mapper;
	private HttpRequest request;
	private HttpResponse<String> response;

	public SWAPIService() {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();// Registrando módulos para o Jackson poder converter das datas
		client = HttpClient.newBuilder().build();
	}

	public Flux<List<Planeta>> buscarPlanetasSWAPI() throws IOException, InterruptedException {
		List<Planeta> encontrados = new ArrayList<>();
		Integer page = 1;

		while (page != 0) {
			request = pesquisarComArgumento("?page=", page.toString());
			response = client.send(request, BodyHandlers.ofString());

			SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
			});

			resultado.getResults().forEach(e -> encontrados.add(new Planeta(e)));

			if (resultado.getNext() != null) {
				page++;
			} else {
				page = 0;
			}
		}

		return Flux.just(encontrados);
	}

	public Integer buscarQntAparicoes(String nome) throws IOException, InterruptedException {
		String nomePesquisa = nome.replace(" ", "+");

		request = pesquisarComArgumento("/?search=", nomePesquisa);
		response = client.send(request, BodyHandlers.ofString());

		SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
		});

		if (resultado == null || resultado.getCount() == 0) {
			return 0;
		}

		return resultado.getResults().get(0).getFilms().size();
	}

	private HttpRequest pesquisarComArgumento(String arg, String pesquisarPor) {
		return HttpRequest.newBuilder() //
				.GET() //
				.header("Content-Type", "application/json") //
				.uri(URI.create(URL_BASE + arg + pesquisarPor)) //
				.timeout(Duration.ofSeconds(10)) //
				.build();
	}

}
