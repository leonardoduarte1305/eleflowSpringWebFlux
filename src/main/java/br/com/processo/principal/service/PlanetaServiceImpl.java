package br.com.processo.principal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.processo.principal.controller.PlanetaDTOEntrada;
import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.repository.PlanetaRepository;
import br.com.processo.principal.webservice.SWAPIService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlanetaServiceImpl implements PlanetaService {

	@Autowired
	private PlanetaRepository repository;

	@Override
	public Flux<Planeta> listarPlanetasDoBanco() {
		return repository.findAll();
	}

	@Override
	public Mono<Planeta> buscarPorIdDoBanco(String id) {
		return repository.findById(id);
	}

	@Override
	public Mono<Boolean> removerPlaneta(String id) {
		return repository.findById(id) //
				.flatMap(planeta -> repository.delete(planeta) //
						.then(Mono.just(Boolean.TRUE))) //
				.defaultIfEmpty(Boolean.FALSE);
	}

	@Override
	public Mono<Planeta> adicionarUmPlaneta(PlanetaDTOEntrada planetaDTO) {

		// Vai buscar se e quantas vezes ele apareceu em algum filme da série
		Integer quantidade = 0;
		try {
			quantidade = new SWAPIService().buscarQntAparicoes(planetaDTO.getNome());
		} catch (IOException | InterruptedException e) {
			e.getMessage();
		}

		Planeta salvar = new Planeta(planetaDTO);
		salvar.setQtdAparicoesFilmes(quantidade);

		return repository.save(salvar);
	}

	@Override
	public Flux<Planeta> buscarPorNomeDoBanco(String nome) {
		return repository.findByNomeIgnoringCaseContaining(nome);
	}

	@Override
	public Flux<List<Planeta>> listarPlanetasDoSWAPI() {

		try {
			return new SWAPIService().buscarPlanetasSWAPI();
		} catch (IOException | InterruptedException e) {
			e.getMessage();
		}

		return Flux.just();
	}

}
