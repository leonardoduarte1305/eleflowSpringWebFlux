package br.com.processo.principal.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.document.PlanetaDTOEntrada;
import br.com.processo.principal.repository.PlanetaRepository;
import br.com.processo.principal.service.exception.EntidadeJaExistenteException;
import br.com.processo.principal.webservice.SWAPIPlaneta;
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
	public Mono<Void> removerPlaneta(String id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<Planeta> adicionarUmPlaneta(PlanetaDTOEntrada planetaDTO) {

		// Verifica se o Planeta já está no banco de dados
		Mono<Planeta> encontrado = repository.findByNomeIgnoringCase(planetaDTO.getNome());
		try {
			if (encontrado.toFuture().get() != null) {
				return Mono.error(new EntidadeJaExistenteException("Planeta já cadastrado"));
			}
		} catch (InterruptedException | ExecutionException e) {
			e.getMessage();
		}

		// Se não foi cadastrado ainda, vai buscar se e quantas vezes ele apareceu em
		// algum filme da série
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
	public Mono<Planeta> buscarPorNomeDoBanco(String nome) {
		return repository.findByNomeIgnoringCase(nome);
	}

	@Override
	public Flux<List<SWAPIPlaneta>> listarPlanetasDoSWAPI() {

		try {
			return new SWAPIService().buscarPlanetasSWAPI();

		} catch (IOException | InterruptedException e) {
			e.getMessage();
		}

		return null;
	}

}
