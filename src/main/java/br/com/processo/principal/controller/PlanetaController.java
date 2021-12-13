package br.com.processo.principal.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.processo.principal.document.Planeta;
import br.com.processo.principal.document.PlanetaDTOEntrada;
import br.com.processo.principal.service.PlanetaService;
import br.com.processo.principal.webservice.SWAPIPlaneta;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	public Mono<Planeta> adicionarUmPlaneta(@Valid @RequestBody PlanetaDTOEntrada planeta) {
		return service.adicionarUmPlaneta(planeta);
	}

	@GetMapping(value = "/nome/{nome}")
	Mono<Planeta> buscarPorNomeDoBanco(@PathVariable String nome) {
		return service.buscarPorNomeDoBanco(nome);
	}

	@GetMapping(value = "/swapi")
	public Flux<List<SWAPIPlaneta>> listarPlanetasDoSWAPI() {
		return service.listarPlanetasDoSWAPI();
	}

}
