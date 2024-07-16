package alura.challengeApiRest.infra.springdoc;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Foro-hub")
                        .description("Bienvenido a la API Rest de Foro-Hub. Esta API proporciona todas las funcionalidades CRUD necesarias para gestionar un foro, incluyendo la creación, consulta, actualización y eliminación de tópicos. Foro-Hub está diseñado para facilitar la interacción entre usuarios que buscan resolver sus dudas y compartir conocimientos.")
                        .contact(new Contact()
                                .name("Maria Cedano")
                                .email("angelesjuanina@gmail.com"))
                        .license(new License()
                                .name("MC")
                                .url("http://foro-hub/api/licencia")));
    }
}

