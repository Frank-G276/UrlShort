package com.guilombo.urlshort;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlshortApplication {

	private static final Logger logger = LoggerFactory.getLogger(UrlshortApplication.class);

	public static void main(String[] args) {
		loadEnvVariables();
		SpringApplication.run(UrlshortApplication.class, args);
	}

	private static void loadEnvVariables() {
		try {
			Dotenv dotenv = Dotenv.configure()
					.directory("./") // Busca en el directorio raíz del proyecto
					.ignoreIfMissing() // No falla si no encuentra el archivo
					.load();

			dotenv.entries().forEach(entry -> {
				String key = entry.getKey();
				// Solo sobreescribe si no está ya definido en system properties
				if (System.getProperty(key) == null) {
					System.setProperty(key, entry.getValue());
					logger.debug("Loaded env var: {}={}", key, maskSensitiveValue(key, entry.getValue()));
				}
			});

			logger.info("Variables de entorno cargadas correctamente");

		} catch (Exception e) {
			logger.warn("No se pudo cargar el archivo .env: {}", e.getMessage());
			logger.info("Usando variables de entorno del sistema");
		}
	}

	private static String maskSensitiveValue(String key, String value) {
		// Enmascara valores sensibles para logs
		if (key.contains("PASSWORD") || key.contains("SECRET") || key.contains("KEY")) {
			return "*****";
		}
		return value;
	}
}