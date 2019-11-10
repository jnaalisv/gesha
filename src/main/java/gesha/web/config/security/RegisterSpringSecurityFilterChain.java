package gesha.web.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;

/**
 * Registers the {@link DelegatingFilterProxy} to use the
 * springSecurityFilterChain before any other registered {@link Filter}
 *
 * @author jnaalisv
 *
 */
public class RegisterSpringSecurityFilterChain extends AbstractSecurityWebApplicationInitializer {

}
