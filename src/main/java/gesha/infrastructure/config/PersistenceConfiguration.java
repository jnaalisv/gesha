package gesha.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@ComponentScan(basePackages = {"gesha.infrastructure.persistence"})
@EnableTransactionManagement
public class PersistenceConfiguration {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName("org.h2.Driver");
        dataSourceConfig.setJdbcUrl("jdbc:h2:mem:db");
        dataSourceConfig.setUsername("sa");
        dataSourceConfig.setPassword("");
        return new HikariDataSource(dataSourceConfig);
    }

    private static final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        //Specifies the action that is invoked to the database when the Hibernate SessionFactory is created or closed.
        hibernateProperties.put("hibernate.hbm2ddl.auto","create");

        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        hibernateProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        hibernateProperties.put("hibernate.format_sql", "true");
        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws PropertyVetoException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("gesha.model.domain.user");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
