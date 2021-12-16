package br.com.processo.principal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void test() {
		client.get().uri("/").exchange().returnResult(String.class)
				.equals("Acesse: https://leoduarteeleflowwebflux.herokuapp.com/swagger-ui.html"
						+ " para conferir a totalidade dos recursos desta api");

	}

}
