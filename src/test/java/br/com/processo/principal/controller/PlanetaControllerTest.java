package br.com.processo.principal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.processo.principal.config.erros.ErroDeEntrada;
import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.repository.PlanetaRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@AutoConfigureDataMongo
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanetaControllerTest {

	@Autowired
	private WebTestClient client;

	@Autowired
	private PlanetaRepository repository;

	List<Planeta> listaExemplo = Arrays.asList( //
			new Planeta("Planeta 1", "Clima 1", "Terreno 1"), //
			new Planeta("Planeta 2", "Clima 2", "Terreno 2"), //
			new Planeta("Planeta 3", "Clima 3", "Terreno 3"), //
			new Planeta("Planeta 4", "Clima 4", "Terreno 4"));

	@BeforeEach
	public void setup() {
		repository.deleteAll().thenMany(Flux.fromIterable(listaExemplo)) //
				.flatMap(repository::save).blockLast();
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	@Order(1)
	public void deveContarQuantosPlanetasVoltamNaPesquisa() {
		StepVerifier.create(repository.findAll()).expectSubscription().expectNextCount(4).verifyComplete();
	}

	@Test
	@Order(2)
	public void deveBuscarPlanetaPorId() {

		Planeta esperado = repository.findByNomeIgnoringCase("Planeta 1").blockFirst();
		String id = esperado.getId();

		client.get().uri("/planetas/" + id).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Planeta.class)
				.value(item -> item.get(0).getId().equals(esperado.getId()));
	}

	@Test
	@Order(3)
	public void naoDeveEncontrarPlanetaComIdInexistente() {

		client.get().uri("/planetas/" + "inexistente").exchange().expectStatus().is2xxSuccessful().expectBody()
				.isEmpty();
	}

	@Test
	@Order(4)
	public void deveRemoverPlanetaDoBanco() {
		Planeta esperado = repository.findByNomeIgnoringCase("Planeta 1").blockFirst();
		String id = esperado.getId();

		client.delete().uri("/planetas/" + id).exchange().expectStatus().isAccepted().expectBody().equals("Removido");
	}

	@Test
	@Order(5)
	public void naoDeveRemoverPlanetaComIdInvalido() {
		client.delete().uri("/planetas/" + "id_qualquer").exchange().expectStatus().isNotFound();
	}

	@Test
	@Order(6)
	public void deveSalvarPlanetaNoBanco() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Nome novo");
		planetaDTO.setClima("Semi-árido");
		planetaDTO.setTerreno("Rochoso");

		Planeta salvo = client.post().uri("/planetas/").bodyValue(planetaDTO).exchange().expectStatus().isCreated()
				.expectBody(Planeta.class).returnResult().getResponseBody();

		assertTrue(salvo.getId() != null);
	}

	@Test
	@Order(7)
	public void naoDeveSalvarPlanetaComNomeVazio() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("");
		planetaDTO.setClima("Semi-árido");
		planetaDTO.setTerreno("Rochoso");

		List<ErroDeEntrada> erros = client.post().uri("/planetas/").bodyValue(planetaDTO).exchange().expectStatus()
				.isBadRequest().expectBodyList(ErroDeEntrada.class).returnResult().getResponseBody();

		assertEquals("não deve estar vazio", erros.get(0).getErro());
		assertEquals("nome", erros.get(0).getCampo());
	}

	@Test
	@Order(8)
	public void naoDeveSalvarPlanetaComClimaVazio() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Mercurio");
		planetaDTO.setClima("");
		planetaDTO.setTerreno("Rochoso");

		List<ErroDeEntrada> erros = client.post().uri("/planetas/").bodyValue(planetaDTO).exchange().expectStatus()
				.isBadRequest().expectBodyList(ErroDeEntrada.class).returnResult().getResponseBody();

		assertEquals("não deve estar vazio", erros.get(0).getErro());
		assertEquals("clima", erros.get(0).getCampo());
	}

	@Test
	@Order(9)
	public void naoDeveSalvarPlanetaComTerrenoVazio() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Mercurio");
		planetaDTO.setClima("Frio");
		planetaDTO.setTerreno("");

		List<ErroDeEntrada> erros = client.post().uri("/planetas/").bodyValue(planetaDTO).exchange().expectStatus()
				.isBadRequest().expectBodyList(ErroDeEntrada.class).returnResult().getResponseBody();

		assertEquals("não deve estar vazio", erros.get(0).getErro());
		assertEquals("terreno", erros.get(0).getCampo());
	}

	@Test
	@Order(10)
	public void deveSalvarPlanetaPesquisandoTotalDeAparicoes() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Tatooine");
		planetaDTO.setClima("Frio");
		planetaDTO.setTerreno("Arenoso");

		client.post().uri("/planetas/").bodyValue(planetaDTO).exchange().expectStatus().isCreated()
				.expectBody(Planeta.class).returnResult().getResponseBody().getQtdAparicoesFilmes().equals(4);
	}

	@Test
	@Order(11)
	public void deveBuscarPlanetaPeloNome() {
		String nome = "Planeta 1";

		client.get().uri("/planetas/nome/" + nome).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Planeta.class)
				.value(item -> item.get(0).getClima().equals("Clima 1"));
	}

	@Test
	@Order(12)
	public void naoDeveEncontrarPlanetaComNomeInexistente() {
		String nome = "qualquer";

		client.get().uri("/planetas/nome/" + nome).exchange().expectStatus().isOk().expectBodyList(Planeta.class)
				.returnResult().getResponseBody().isEmpty();
	}

	@Test
	@Order(13)
	public void deveTrazerTodosOsPlanetasDoBanco() {
		client.get().uri("/planetas/").exchange().expectStatus().isOk().expectBodyList(Planeta.class).hasSize(4);
	}

	// @Test
	// @Order(14)
	public void deveTrazerListaDePlanetasDoSWAPI() {
		int tamanho = client.get().uri("/planetas/swapi").exchange().expectStatus().is2xxSuccessful()
				.expectBodyList(Planeta.class).returnResult().getResponseBody().size();

		assertEquals(60, tamanho);
	}
}
