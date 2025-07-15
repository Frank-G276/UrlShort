package com.guilombo.urlshort;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlshortApplication {

	public static void main(String[] args) {
		try {
			// Cargar variables de entorno desde el archivo .env
			Dotenv dotenv = Dotenv.configure()
					.directory("./")
					.ignoreIfMissing()
					.load();

			// Establecer las variables como propiedades del sistema
			dotenv.entries().forEach(entry ->
					System.setProperty(entry.getKey(), entry.getValue())
			);

			System.out.println("Variables de entorno cargadas correctamente");

		} catch (Exception e) {
			System.out.println("Error cargando archivo .env: " + e.getMessage());
		}

		SpringApplication.run(UrlshortApplication.class, args);
	}
}