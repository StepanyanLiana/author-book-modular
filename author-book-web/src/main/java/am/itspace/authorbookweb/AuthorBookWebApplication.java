package am.itspace.authorbookweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"am.itspace.authorbookweb","am.itspace.authorbookcommon"})
@EntityScan("am.itspace.authorbookcommon.entity")
@EnableJpaRepositories(basePackages = "am.itspace.authorbookcommon.repository")

public class AuthorBookWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorBookWebApplication.class, args);
    }
}
