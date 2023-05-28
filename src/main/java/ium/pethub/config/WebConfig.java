package ium.pethub.config;

import ium.pethub.util.interceptor.AuthInterceptor;
import ium.pethub.util.interceptor.ValidInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // private final TokenArgumentResolver tokenArgumentResolver;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ValidInterceptor validInterceptor() {
        return new ValidInterceptor();}
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOriginPatterns("*")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE")
                .allowCredentials(true)
                .maxAge(3000);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // List<String> excludes = Arrays.asList("/favicon.ico");
        registry.addInterceptor(validInterceptor());
        registry.addInterceptor(authInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path path = Paths.get("pethub/src/main/upload/img/");
        registry.addResourceHandler("/upload_img/**")
                .addResourceLocations("file:"+ path.toAbsolutePath()+"/");
    }
}
