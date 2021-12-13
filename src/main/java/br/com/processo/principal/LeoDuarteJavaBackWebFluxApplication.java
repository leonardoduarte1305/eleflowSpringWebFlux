package br.com.processo.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info( //
		title = "Api Eleflux", //
		version = "1.0", //
		description = "Documentação da API v1.0", //
		contact = @Contact(name = "Leonardo Duarte", //
				email = "leonardoduarte1305@gmail.com", //
				url = "https://leoduarteeleflowwebflux.herokuapp.com/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config"))) //
public class LeoDuarteJavaBackWebFluxApplication {

	public static void main(String[] eleflow) {
		SpringApplication.run(LeoDuarteJavaBackWebFluxApplication.class, eleflow);
	}

}
