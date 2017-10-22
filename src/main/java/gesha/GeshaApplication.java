package gesha;

import gesha.web.config.WebConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@Import(WebConfiguration.class)
public class GeshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeshaApplication.class, args);
    }

}