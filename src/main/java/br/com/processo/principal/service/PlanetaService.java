package br.com.processo.principal.service;

import java.util.List;

import br.com.processo.principal.controller.PlanetaDTOEntrada;
import br.com.processo.principal.document.Planeta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlanetaService {

	Mono<Planeta> adicionarUmPlaneta(PlanetaDTOEntrada planeta);

	Flux<Planeta> listarPlanetasDoBanco();

	Flux<List<Planeta>> listarPlanetasDoSWAPI();

	Flux<Planeta> buscarPorNomeDoBanco(String nome);

	Mono<Planeta> buscarPorIdDoBanco(String id);

	Mono<Boolean> removerPlaneta(String id);

}
