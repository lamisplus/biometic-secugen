package org.lamisplus.modules.secugen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableSwagger2
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("APPLICATION LAUNCHED");
    }

    /*
     * Provides sensible defaults and convenience methods for configuration.
     * @return a Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /*
     *
     * @return ApiInfo for documentation
     */

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Lamisplus")
                .description("Lamisplus Application Api Documentation")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("http://swagger.io/terms/")
                .version("1.0.0").contact(new Contact("Development Team","http://lamisplus.org/base-module", "info@lamisplus.org"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    /*
     * @Param name
     * @Param keyName
     * @Param passAs
     * @return ApiKey
     * Sending Authorization:
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
}


//@SpringBootApplication
//public class Application implements CommandLineRunner {
// 
//    private static Logger logger = LoggerFactory.getLogger(Application.class);
//    
//    @Autowired
//    private SecugenManager secugenManager;
// 
//    public static void main(String[] args) {
//        logger.info("STARTING THE APPLICATION");
//        SpringApplication.run(Application.class, args);
//        logger.info("APPLICATION FINISHED");
//    }
//  
//    @Override
//    public void run(String... args) {
//        logger.info("EXECUTING : command line runner");
//        Long error = secugenManager.boot();
//        if (error==null || error == 0L){
//            logger.info("EXECUTING : Device Information Below");
//            
//            Gson gson = new Gson();
//            logger.info(gson.toJson(secugenManager.getBasicDeviceInfo()));
//            
//            BiometricTemplate biometricTemplate = secugenManager.registerFingerPrint();
//            
//            logger.info(gson.toJson(biometricTemplate));
//        }
//    }
//}
