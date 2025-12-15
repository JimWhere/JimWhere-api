package com.jimwhere.api.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "JimWhere REST API 명세서",
        version = "v1.0",
        description = "사업자 분들의 물건을 창고에서 대신 관리해주는 시스템"
    ),
    servers = {
        @Server(url = "http://localhost:8081", description = "Local Server")
    }
)
@Configuration
public class SwaggerConfig {
}
