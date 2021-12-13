package br.com.processo.principal.service;

import java.util.List;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.document.PlanetaDTOEntrada;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetaService {

	Mono<Planeta> adicionarUmPlaneta(PlanetaDTOEntrada planeta);

	Flux<Planeta> listarPlanetasDoBanco();

	Flux<List<Planeta>> listarPlanetasDoSWAPI();

	Flux<Planeta> buscarPorNomeDoBanco(String nome);

	Mono<Planeta> buscarPorIdDoBanco(String id);

	Mono<Void> removerPlaneta(String id);

}
