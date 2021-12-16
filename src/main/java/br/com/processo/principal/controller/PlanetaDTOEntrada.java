package br.com.processo.principal.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanetaDTOEntrada {

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	@NotEmpty
	private String clima;

	@NotNull
	@NotEmpty
	private String terreno;

}
