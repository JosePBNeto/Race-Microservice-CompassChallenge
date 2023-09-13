package msraces;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Race Microservice API",
				description = "Simulate a Race by retrieving random cars from Cars Microservice and send the result to a RabbitMQ queue",
				version = "v1.0",
				contact = @Contact(
						name = "Jose Patricio Bandeira Barbosa Neto",
						url = "https://github.com/JosePBNeto"
				))
)
public class MsRacesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRacesApplication.class, args);
	}

}
