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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Planetas", description = "Realiza operações com Planetas")
@RestController
@RequestMapping("/planetas")
public class PlanetaController {

	@Autowired
	private PlanetaService service;

	@Operation(summary = "Listar planetas cadastrados.", //
			description = "Lista todos os planetas cadastrados no nosso banco de dados")
	@GetMapping
	public Flux<Planeta> listarPlanetasDoBanco() {
		return service.listarPlanetasDoBanco();
	}

	@Operation(summary = "Buscar um planeta pelo seu Id.", //
			description = "Traz o planeta cadastrado usando seu id (String) como chave para busca.", parameters = {
					@Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "atributo Id") })
	@GetMapping(value = "/{id}")
	Mono<Planeta> buscarPorIdDoBanco(@PathVariable String id) {
		return service.buscarPorIdDoBanco(id);
	}

	@Operation(summary = "Exclui um planeta pelo seu Id.", //
			description = "Exclui o planeta cadastrado usando seu id (String) como chave para a operação.", parameters = {
					@Parameter(name = "id", in = ParameterIn.PATH, description = "atributo Id") })
	@DeleteMapping(value = "/{id}")
	Mono<Void> removerPlaneta(@PathVariable String id) {
		return service.removerPlaneta(id);
	}

	@Operation(summary = "Cadastra um planeta.", //
			description = "Cadastra um novo planeta.")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Mono<Planeta> adicionarUmPlaneta(@Validated @RequestBody final PlanetaDTOEntrada planeta) {
		return service.adicionarUmPlaneta(planeta);
	}

	@Operation(summary = "Buscar um planeta pelo seu nome.", //
			description = "Traz o planeta cadastrado usando seu nome como chave para busca.", parameters = {
					@Parameter(name = "nome", in = ParameterIn.PATH, required = true, description = "atributo nome") })
	@GetMapping(value = "/nome/{nome}")
	Flux<Planeta> buscarPorNomeDoBanco(@PathVariable String nome) {
		return service.buscarPorNomeDoBanco(nome);
	}

	@Operation(summary = "Listar planetas cadastrados no site SWAPI.dev.", //
			description = "Lista todos os planetas cadastrados no site SWAPI.dev.")
	@GetMapping(value = "/swapi")
	public Flux<List<Planeta>> listarPlanetasDoSWAPI() {
		return service.listarPlanetasDoSWAPI();
	}

}
