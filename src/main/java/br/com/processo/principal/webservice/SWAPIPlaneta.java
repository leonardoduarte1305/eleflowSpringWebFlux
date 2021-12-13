package br.com.processo.principal.webservice;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SWAPIPlaneta implements Serializable {

	private static final long serialVersionUID = -6051453722452187796L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("rotation_period")
	private String rotationPeriod;

	@JsonProperty("orbital_period")
	private String orbitalPeriod;

	@JsonProperty("diameter")
	private String diameter;

	@JsonProperty("climate")
	private String climate;

	@JsonProperty("gravity")
	private String gravity;

	@JsonProperty("terrain")
	private String terrain;

	@JsonProperty("surface_water")
	private String surface_Water;

	@JsonProperty("population")
	private String population;

	@JsonProperty("residents")
	private List<String> residents;

	@JsonProperty("films")
	private List<String> films;

	@JsonProperty("created")
	private LocalDateTime created;

	@JsonProperty("edited")
	private LocalDateTime edited;

	@JsonProperty("url")
	private String url;

	@Override
	public String toString() {
		return "SWAPIPlaneta [Nome: " + name + ", Clima: " + climate + ", Terreno: " + terrain + ", Apareceu: "
				+ films.size() + " vezes na série]\n";
	}

}
