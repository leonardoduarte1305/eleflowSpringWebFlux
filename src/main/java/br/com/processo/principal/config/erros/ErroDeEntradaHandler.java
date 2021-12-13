package br.com.processo.principal.config.erros;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class ErroDeEntradaHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<List<ErroDeEntrada>> handleException(WebExchangeBindException e) {

		List<ErroDeEntrada> erros = new ArrayList<ErroDeEntrada>();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

		fieldErrors.forEach(err -> {
			String mensagem = messageSource.getMessage(err, LocaleContextHolder.getLocale());
			ErroDeEntrada erro = new ErroDeEntrada(err.getField(), mensagem);
			erros.add(erro);
		});

		return ResponseEntity.badRequest().body(erros);
	}
}
