package gesha.web.config;

import gesha.infrastructure.config.PersistenceConfiguration;
import gesha.model.config.DomainConfiguration;
import gesha.web.config.security.WebSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Import({
        PersistenceConfiguration.class,
        DomainConfiguration.class,
        WebSecurityConfig.class
})
@ComponentScan({"gesha.web.interfaces"})
public class WebConfiguration extends WebMvcConfigurerAdapter {

}

