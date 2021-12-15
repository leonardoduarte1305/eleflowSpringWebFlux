package br.com.processo.principal.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.processo.principal.controller.PlanetaDTOEntrada;
import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.service.PlanetaService;

//@RunWith(SpringRunner.class)
@SpringBootTest
@WebFluxTest
public class PlanetaRepositoryTest {

	@MockBean
	PlanetaRepository repository;

	@MockBean
	PlanetaService service;

	// @Test
	public void deveSalvarUmNovoPlaneta() {

		PlanetaDTOEntrada planetaDTO = new PlanetaDTOEntrada();
		planetaDTO.setNome("Planeta Teste");
		planetaDTO.setClima("Semi-arido");
		planetaDTO.setTerreno("Montanhoso");

		Planeta planetaSalvo = repository.save(new Planeta(planetaDTO)).block();

		Assert.assertEquals(planetaSalvo.getId(), is(notNullValue()));
	}

}
