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
import reactor.core.publisher.Flux;

public class SWAPIService {

	private final static String URL_BASE = "https://swapi.dev/api/planets/";
	private HttpClient client;
	private ObjectMapper mapper;

	public SWAPIService() {
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();// Registrando módulos para o Jackson poder converter das datas
		client = HttpClient.newBuilder().build();
	}

	// TODO WEBSERVICE service.listarPlanetasDoSWAPI()
	public Flux<List<Planeta>> buscarPlanetasSWAPI() throws IOException, InterruptedException {
		List<Planeta> encontrados = new ArrayList<>();
		int page = 1;

		while (page != 0) {
			HttpRequest request = HttpRequest.newBuilder() //
					.GET() //
					.header("Content-Type", "application/json") //
					.uri(URI.create(URL_BASE + "?page=" + page)) //
					.timeout(Duration.ofSeconds(10)) //
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

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

		HttpRequest request = HttpRequest.newBuilder() //
				.GET() //
				.header("Content-Type", "application/json") //
				.uri(URI.create(URL_BASE + "/?search=" + nomePesquisa)) //
				.timeout(Duration.ofSeconds(10)) //
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
		});

		if (resultado == null || resultado.getCount() == 0) {
			return 0;
		}

		return resultado.getResults().get(0).getFilms().size();
	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	public static void main(String[] args) throws IOException, InterruptedException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		HttpClient client = HttpClient.newBuilder().build();

		int page = 1;

		while (page != 0) {
			HttpRequest request = HttpRequest.newBuilder() //
					.GET() //
					.header("Content-Type", "application/json") //
					.uri(URI.create(URL_BASE + "?page=" + page)) //
					.timeout(Duration.ofSeconds(10)) //
					.build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
			});

			System.out.println("Page: " + page);
			System.out.println(resultado.getResults());
			System.out.println("\n\n\n\n");

			if (resultado.getNext() != null) {
				page++;
			} else {
				page = 0;
			}
		}

	}
}
