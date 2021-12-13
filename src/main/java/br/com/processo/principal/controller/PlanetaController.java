package br.com.processo.principal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.service.PlanetaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Planetas", description = "Realiza operações com Planetas")
@RestController
@RequestMapping("/planetas")
public class PlanetaController {

	@Autowired
	private PlanetaService service;

	@GetMapping
	public Flux<Planeta> listarPlanetasDoBanco() {
		return service.listarPlanetasDoBanco();
	}

	@GetMapping(value = "/{id}")
	Mono<Planeta> buscarPorIdDoBanco(@PathVariable String id) {
		return service.buscarPorIdDoBanco(id);
	}

	@DeleteMapping(value = "/{id}")
	Mono<Void> removerPlaneta(@PathVariable String id) {
		return service.removerPlaneta(id);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Planeta> adicionarUmPlaneta(@Validated @RequestBody PlanetaDTOEntrada planeta) {
		return service.adicionarUmPlaneta(planeta);
	}

	@GetMapping(value = "/nome/{nome}")
	Flux<Planeta> buscarPorNomeDoBanco(@PathVariable String nome) {
		return service.buscarPorNomeDoBanco(nome);
	}

	@GetMapping(value = "/swapi")
	public Flux<List<Planeta>> listarPlanetasDoSWAPI() {
		return service.listarPlanetasDoSWAPI();
	}

}
