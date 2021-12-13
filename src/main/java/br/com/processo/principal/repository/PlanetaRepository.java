package br.com.processo.principal.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.processo.principal.document.Planeta;
import reactor.core.publisher.Mono;

@Repository
public interface PlanetaRepository extends ReactiveMongoRepository<Planeta, String> {

	Mono<Planeta> findByNomeIgnoringCase(String nome);

}
