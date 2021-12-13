package br.com.processo.principal.document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
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
