package br.com.processo.principal.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
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
