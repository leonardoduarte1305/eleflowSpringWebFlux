package br.com.processo.principal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.repository.PlanetaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
//@DirtiesContext

//@DataMongoTest
//@RunWith(SpringRunner.class)
@AutoConfigureDataMongo
//@SpringBootTest
//@DirtiesContext
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

	@Before
	public void setup() {
		repository.deleteAll().thenMany(Flux.fromIterable(listaExemplo)) //
				.flatMap(repository::save).doOnNext((item -> System.err.println(item))).blockLast();
	}

	@Test
	public void dbNaoDeveEstarVazio() {

		Flux<Planeta> encontrado = repository.findByNomeIgnoringCaseContaining("Planeta 1");

		StepVerifier.create(encontrado).assertNext(planeta -> {
			assertEquals("Terreno 1", planeta.getTerreno());
			assertEquals("Clima 1", planeta.getClima());
			assertNotNull(planeta.getId());

		}).expectComplete().verify();

		Mono<Long> total = repository.findByNomeIgnoringCase("Planeta 1").count();
		System.err.println(total.block());
		assertTrue(total.block().equals(4));

	}

	@Test
	public void deveRetornarTodosOsPlanetas() {

		StepVerifier.create(repository.findAll()).expectSubscription().expectNextCount(4).verifyComplete();
	}

	@Test
	public void deveContarQuantosPlanetasVoltamNaPesquisa() {
		client.get().uri("/planetas").exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON).expectBodyList(Planeta.class).hasSize(4).consumeWith(p -> {
					List<Planeta> planetas = p.getResponseBody();

					planetas.forEach(plan -> {
						assertTrue(plan.getId() != null);
					});
				});
	}

	@Test
	public void deveRetornarListaComPlanetas() {
		WebTestClient.ResponseSpec response = client.get().uri("/planetas").exchange();

		response.expectStatus().is2xxSuccessful().expectBodyList(Planeta.class).isEqualTo(List.of());
	}

	@Test
	public void deveRetornarUmPlaneta() {
		WebTestClient.ResponseSpec response = client.get().uri("/planetas/nome/Planeta 1").exchange();

		Planeta encontrado = repository.findByNomeIgnoringCase("Planeta 1").blockFirst();

		response.expectStatus().is2xxSuccessful().expectBodyList(Planeta.class).contains(encontrado);
	}
}
