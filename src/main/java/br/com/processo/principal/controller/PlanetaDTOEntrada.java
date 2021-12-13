package br.com.processo.principal.controller;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
