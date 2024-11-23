package sumob.gecot.temperature_notifier;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@SpringBootApplication
public class TemperatureNotifierApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureNotifierApplication.class, args);
	}

}
