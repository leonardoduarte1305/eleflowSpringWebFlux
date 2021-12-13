package br.com.processo.principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info( //
		title = "Api Eleflux", //
		description = "API desenvolvida para teste de conhecimento", //
		version = "1.0", //
		contact = @Contact(name = "Leonardo Duarte", url = "", // TODO url do Heroku
				email = "leonardoduarte1305@gmail.com")))
public class LeoDuarteJavaBackWebFluxApplication {

	public static void main(String[] eleflow) {
		SpringApplication.run(LeoDuarteJavaBackWebFluxApplication.class, eleflow);
	}

}
