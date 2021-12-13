package br.com.processo.principal.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.processo.principal.document.Planeta;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class PlanetaControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void listarPlanetasDoBancoTest() {
		webTestClient.get() //
				.uri("/planetas") //
				.accept(MediaType.APPLICATION_JSON) //
				.exchange() //
				.expectStatus().isOk() //
				.returnResult(Planeta.class) //
				.getRequestBodyContent();

	}

}
