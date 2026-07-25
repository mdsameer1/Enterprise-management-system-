package com.enterprisems;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@OpenAPIDefinition(
        info = @Info(
                title = "Enterprise Management System API",
                version = "1.0.0",
                description = "Production-grade Enterprise Employee & Project Management System",
                contact = @Contact(
                        name = "Enterprise Management Team",
                        email = "support@enterprise-ms.com"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080/api",
                        description = "Development Server"
                ),
                @Server(
                        url = "https://api.enterprise-ms.com/api",
                        description = "Production Server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT authentication token",
        in = SecuritySchemeIn.HEADER
)
public class EnterpriseManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseManagementSystemApplication.class, args);
    }

}
