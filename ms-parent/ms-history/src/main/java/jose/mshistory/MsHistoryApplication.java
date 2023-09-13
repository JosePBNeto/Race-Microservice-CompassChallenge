package jose.mshistory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot History Microservice API",
				description = "Consume a rabbitMQ Queue containing the result of a race and save it to a database",
				version = "v1.0",
				contact = @Contact(
						name = "Jose Patricio Bandeira Barbosa Neto",
						url = "https://github.com/JosePBNeto"
				))
)
public class MsHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHistoryApplication.class, args);
	}

}
