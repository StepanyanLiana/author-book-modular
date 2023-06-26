package am.itspace.authorbookrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"am.itspace.authorbookrest","am.itspace.authorbookcommon"})
@EntityScan("am.itspace.authorbookcommon.entity")
@EnableJpaRepositories(basePackages = "am.itspace.authorbookcommon.repository")
public class AuthorBookRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorBookRestApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
