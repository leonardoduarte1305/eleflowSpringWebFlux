package br.com.processo.principal.service;

import static org.hamcrest.CoreMatchers.any;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.processo.principal.controller.PlanetaDTOEntrada;
import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.repository.PlanetaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@AutoConfigureDataMongo
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanetaServiceImplTest {

	@Autowired
	private PlanetaService service;

	@MockBean
	private PlanetaRepository repository;

	@Test
	public void deveLancarExcecaoAoSerInterrompido() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Nome novo");
		planetaDTO.setClima("Semi-árido");
		planetaDTO.setTerreno("Rochoso");

		Mockito.when(repository.save(Mockito.any())).thenThrow(InterruptedException.class);

		Flux<Planeta> source = service.adicionarUmPlaneta(planetaDTO)
				.concatWith(Mono.error(new InterruptedException("Deu meleca")));

		StepVerifier.create(source).expectError(InterruptedException.class).verify();

	}

	@Test
	public void deveLancarExcecaoNaoHavendoDados() {
		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Outro planeta");
		planetaDTO.setClima("Árido");

		StepVerifier.create(Mono.error(new IOException("Exceção"))).expectError(IOException.class).verify();
	}

	public PlanetaServiceImplTest(PlanetaService service) {
		this.service = service;
	}

}
