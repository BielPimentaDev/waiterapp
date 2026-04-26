package com.example.waiterapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig {

    /**
     * Mantém o comportamento de servir a SPA (index.html) na rota "/".
     *
     * OBS: A documentação da API agora é gerada pelo springdoc-openapi.
     * - OpenAPI JSON: /v3/api-docs
     * - Swagger UI: /swagger-ui/index.html
     */
    @Configuration
    static class SpaForwardingConfig implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/index.html");
        }
    }

}

