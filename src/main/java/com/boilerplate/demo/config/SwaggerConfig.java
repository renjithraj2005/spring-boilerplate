package com.boilerplate.demo.config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile(value = {"development", "dev","preprod"})
public class SwaggerConfig {

  @Autowired private ConfigProperties configProperties;

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.boilerplate.demo"))
        .paths(Predicates.not(PathSelectors.regex("/error")))
        .build()
        .apiInfo(metadata())
        .ignoredParameterTypes(OAuth2Authentication.class)
        .useDefaultResponseMessages(false)
        .securitySchemes(Arrays.asList(apiKey()))
        .securityContexts(Arrays.asList(securityContext()))
        .genericModelSubstitutes(Optional.class);
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
        .title("JSON Web Token Authentication API")
        .description("This is a sample JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/). For this sample, you can use the `admin` or `client` users (password: admin and client respectively) to test the authorization filters. Once you have successfully logged in and obtained the token, you should click on the right top button `Authorize` and introduce it with the prefix \"Bearer \".")
        .version("1.0.0")
        .license("Auth License").licenseUrl("https://example.com")
        .contact(new Contact(null, null, "demo@example.com"))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey(configProperties.getSwaggerApiKeyName(), "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/.*"))
            .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference(configProperties.getSwaggerApiKeyName(), authorizationScopes));
  }

  @Bean
  public SecurityConfiguration security() {
    return SecurityConfigurationBuilder.builder()
            .clientId(configProperties.getDefaultOAuth2Client())
            .clientSecret(configProperties.getDefaultOAuth2ClientSecret())
            .realm("demo")
            .appName("demo")
            .scopeSeparator(",")
            .additionalQueryStringParams(null)
            .useBasicAuthenticationWithAccessCodeGrant(false)
            .build();
  }
}
