package br.com.processo.principal.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.service.PlanetaService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(PlanetaController.class)
public class PlanetaControllerTest {

	@Autowired
	WebTestClient client;// = WebTestClient.bindToController(new
							// PlanetaController()).configureClient().baseUrl("/planetas").build();

	@MockBean
	PlanetaService service;

	@Test
	public void testeBuscarDoBancoPorNome() {
		
		Planeta planeta = new Planeta("Mercurio", "Árido", "Montranhoso");
		planeta.setId("61b6b5b1ff8e821dc8068495");
		
		Mono<Planeta> planetaMono = Mono.just(planeta);
		Mockito.when(service.buscarPorIdDoBanco(planeta.getId())).thenReturn(planetaMono);
		
		client.get()
			.uri("/61b6b5b1ff8e821dc8068495")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Planeta.class)
			.value(p -> p.getNome(), equalTo("Mercurio"));
	}
	
	@Test
	public void testeBuscarTodosDoBanco() {
		Planeta planeta1 = new Planeta("Mercurio", "seco", "Montanhoso");

		client.get().accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(Planeta.class)
				.hasSize(10).contains(planeta1);
	}

	@Test
	public void testeBuscarTodosDoBanco2() {
		client.get().accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk().expectBodyList(Planeta.class)
				.consumeWith(resultado -> {
					assertNotNull(resultado.getResponseBody().get(0).getId());
				});
	}

	@Test
	public void testeBuscarTodosDoBanco3() {
		EntityExchangeResult<List<Planeta>> resultado = client.get().accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectBodyList(Planeta.class).returnResult();

		assertNotNull(resultado.getResponseBody().get(0).getId());
	}

	@Test
	public void testeDelete() {
		client
		.delete()
		.uri("/123")
		.exchange()
		.expectBody()
		.isEmpty();
	}

	@Test
	public void testeBuscarDoBanco() {
		client.get().uri("/nome/tatooine").accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

	@Test
	public void testeBuscarDoBancoComAssertiva() {
		EntityExchangeResult<Planeta> resultado = client.get().uri("/nome/tatooine").exchange()
				.expectBody(Planeta.class).returnResult();

		assertTrue(("tatooine").equals(resultado.getResponseBody().getNome()));
	}

}
