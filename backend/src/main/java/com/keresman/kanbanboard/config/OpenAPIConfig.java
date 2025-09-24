package com.keresman.kanbanboard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Swagger/OpenAPI configuration for API documentation. */
@Configuration
public class OpenAPIConfig {

  private static final String SECURITY_SCHEME_NAME = "bearer-jwt";

  /**
   * Configures the OpenAPI specification for the application.
   *
   * @return an {@link OpenAPI} object with metadata and docs settings
   */
  @Bean
  public OpenAPI kanbanBoardOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Kanban Board API")
                .description("API documentation for the Kanban Board project")
                .version("v1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
        .components(
            new Components()
                .addSecuritySchemes(
                    SECURITY_SCHEME_NAME,
                    new SecurityScheme()
                        .name(SECURITY_SCHEME_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}
