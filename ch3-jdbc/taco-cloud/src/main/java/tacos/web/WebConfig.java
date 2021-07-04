package tacos.web;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // WebMvcConfigurer 로 "/" 경로 요청 시 view 지정
        registry.addViewController("/").setViewName("home");
    }
}
