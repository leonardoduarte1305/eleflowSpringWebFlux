package br.com.processo.principal.webservice;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SWAPIService {

	private final static String URL_BASE = "https://swapi.dev/api/planets/";

	public Flux<List<SWAPIPlaneta>> buscarPlanetasSWAPI() throws IOException, InterruptedException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();

		HttpClient client = HttpClient.newBuilder().build();

		HttpRequest request = HttpRequest.newBuilder() //
				.GET() //
				.header("Content-Type", "application/json") //
				.uri(URI.create(URL_BASE)) //
				.timeout(Duration.ofSeconds(10)) //
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
		});

		return null;
	}

	public Integer buscarQntAparicoes(String nome) throws IOException, InterruptedException {
		String nomePesquisa = nome.replace(" ", "+");

		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder() //
				.GET() //
				.header("Content-Type", "application/json") //
				.uri(URI.create(URL_BASE + "/?search=" + nomePesquisa)) //
				.timeout(Duration.ofSeconds(10)) //
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules(); // Registando módulos para o Jackson poder converter

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

		HttpRequest request = HttpRequest.newBuilder() //
				.GET() //
				.header("Content-Type", "application/json") //
				.uri(URI.create(URL_BASE)) //
				.timeout(Duration.ofSeconds(10)) //
				.build();

		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

		SWAPIResultados resultado = mapper.readValue(response.body(), new TypeReference<SWAPIResultados>() {
		});

		System.out.println(resultado.getResults());
	}
}
