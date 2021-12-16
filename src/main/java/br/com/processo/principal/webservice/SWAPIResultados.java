package br.com.processo.principal.webservice;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SWAPIResultados implements Serializable {

	private static final long serialVersionUID = 2146802749173733655L;

	@JsonProperty("count")
	private Long count;

	@JsonProperty("next")
	private Object next;

	@JsonProperty("previous")
	private Object previous;

	@JsonProperty("results")
	private List<SWAPIPlaneta> results;

}