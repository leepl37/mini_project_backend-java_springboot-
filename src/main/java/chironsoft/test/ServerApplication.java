package chironsoft.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
	
//	@Value("${server.http.port}")
//	private String serverHttpPort;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
