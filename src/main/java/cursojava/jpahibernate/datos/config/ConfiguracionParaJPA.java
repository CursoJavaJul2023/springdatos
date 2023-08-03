package cursojava.jpahibernate.datos.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
	basePackages = {
		"cursojava.jpahibernate.orm.modelobanco.repositorios.jpa",
		"cursojava.jpahibernate.orm.modelobanco.servicios.impl"
	}
)
@EnableJpaRepositories(
	basePackages = {
		"cursojava.jpahibernate.orm.modelocompras.repositorios"
	}
)
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class ConfiguracionParaJPA {

	@Value("${cursojava.driver}")
	private String driverClassName;
	@Value("${cursojava.url}")
	private String url;
	@Value("${cursojava.user}")
	private String username;
	@Value("${cursojava.password}")
	private String password;

	@Bean
//	@Lazy
//	@Scope("singleton")
//	@Primary
//	@Qualifier("desarrollo")
	public DataSource dataSource() {
		
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		
		dmds.setDriverClassName(driverClassName);
		dmds.setUrl(url);
		dmds.setUsername(username);
		dmds.setPassword(password);
		
		return dmds;		
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(
			"cursojava.jpahibernate.orm.modelobanco.entidades",
			"cursojava.jpahibernate.orm.modelobanco.dto",
			"cursojava.jpahibernate.orm.modelocompras.entidades",
			"cursojava.jpahibernate.orm.modelocompras.dto"
		);
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties config = new Properties();
		config.setProperty("hibernate.show_sql", "true");
		config.setProperty("hibernate.format_sql", "true");
		
		emf.setJpaProperties(config);
		
		return emf;		
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		
		JpaTransactionManager tx = new JpaTransactionManager(emf);
		return tx;
	}
	
	
}


