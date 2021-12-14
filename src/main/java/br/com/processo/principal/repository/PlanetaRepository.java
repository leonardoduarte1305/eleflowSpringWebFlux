package br.com.processo.principal.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.processo.principal.document.Planeta;
import reactor.core.publisher.Flux;

@Repository
public interface PlanetaRepository extends ReactiveMongoRepository<Planeta, String> {

	Flux<Planeta> findByNomeIgnoringCase(String nome);

	Flux<Planeta> findByNomeIgnoringCaseContaining(String nome);

}
