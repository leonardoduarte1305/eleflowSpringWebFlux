package br.com.processo.principal.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.processo.principal.controller.PlanetaDTOEntrada;
import br.com.processo.principal.document.Planeta;

//@SpringBootTest
//@WebFluxTest
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class PlanetaRepositoryTest {

	@Autowired
	private PlanetaRepository repository;

	@Test
	public void deveSalvarUmNovoPlaneta() {

		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Planeta Teste");
		planetaDTO.setClima("Semi-arido");
		planetaDTO.setTerreno("Montanhoso");

		Planeta planetaSalvo = repository.save(new Planeta(planetaDTO)).block();

		assertNotNull(planetaSalvo.getId());
	}

}
