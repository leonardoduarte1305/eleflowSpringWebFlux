package br.com.processo.principal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
@RequestMapping("/")
public class AbstractController {

	@GetMapping
	public String health() {
		return "Acesse: https://leoduarteeleflowwebflux.herokuapp.com/swagger-ui.html para conferir a totalidade dos recursos desta api";
	}
}
