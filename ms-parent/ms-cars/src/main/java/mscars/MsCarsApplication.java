package mscars;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Cars Microservice API",
				description = "Create, read, update and delete Cars",
				version = "v1.0",
				contact = @Contact(
						name = "Jose Patricio Bandeira Barbosa Neto",
						url = "https://github.com/JosePBNeto"
		))
)
public class MsCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCarsApplication.class, args);
	}

}
