package br.com.processo.principal.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.processo.principal.webservice.SWAPIPlaneta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Planeta implements Serializable {

	private static final long serialVersionUID = -5035475006233488741L;

	@Id
	private String id;

	private String nome;

	private String clima;

	private String terreno;

	private Integer qtdAparicoesFilmes;

	public Planeta(String nome, String clima, String terreno) {
		this.nome = nome;
		this.clima = clima;
		this.terreno = terreno;
	}

	public Planeta(PlanetaDTOEntrada planetaDTO) {
		this.nome = planetaDTO.getNome();
		this.clima = planetaDTO.getClima();
		this.terreno = planetaDTO.getTerreno();
	}

	public Planeta(SWAPIPlaneta swapiPlanet) {
		this.nome = swapiPlanet.getName();
		this.clima = swapiPlanet.getClimate();
		this.terreno = swapiPlanet.getTerrain();
	}

}
