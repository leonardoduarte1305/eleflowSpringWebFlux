package br.com.processo.principal.config.erros;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroDeEntrada {

	private String campo;
	private String erro;

}
