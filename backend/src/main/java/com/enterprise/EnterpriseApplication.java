package com.enterprise;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main Spring Boot Application Class
 * Enterprise Management System - Employee & Project Management
 * 
 * Features:
 * - JWT Authentication with Refresh Tokens
 * - Role-Based Access Control (Admin, HR, Manager, Employee)
 * - Complete Employee Lifecycle Management
 * - Project & Task Management
 * - Attendance & Leave Management
 * - Real-time Notifications
 * - Comprehensive Reporting
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class EnterpriseApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplication.class, args);
    }

    /**
     * Configure OpenAPI/Swagger Documentation
     * Accessible at: http://localhost:8080/swagger-ui.html
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Enterprise Management System API")
                        .version("1.0.0")
                        .description("Production-grade Employee & Project Management System\n\n" +
                                "Features:\n" +
                                "- JWT Authentication with Refresh Tokens\n" +
                                "- Role-Based Access Control\n" +
                                "- Employee Management\n" +
                                "- Project & Task Management\n" +
                                "- Attendance & Leave Management\n" +
                                "- Notifications & Reports\n" +
                                "- File Uploads & Audit Logs")
                        .contact(new Contact()
                                .name("Enterprise Support")
                                .email("support@enterprise.com")
                                .url("https://enterprise.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}