package notadomain.aeras.Base64POC.config;

import notadomain.aeras.Base64POC.repository.ImageRepository;
import notadomain.aeras.Base64POC.repository.MockRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ImageRepository getRepo(){
        return  new MockRepository();
    }
}
