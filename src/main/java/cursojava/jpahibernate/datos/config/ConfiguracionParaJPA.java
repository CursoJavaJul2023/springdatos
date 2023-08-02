package cursojava.jpahibernate.datos.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
	basePackages = {
		"cursojava.jpahibernate.orm.modelobanco.repositorios.jpa"
	}
)
@EnableTransactionManagement
public class ConfiguracionParaJPA {

	@Bean
//	@Lazy
//	@Scope("singleton")
//	@Primary
//	@Qualifier("desarrollo")
	public DataSource dataSource() {
		
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("org.postgresql.Driver");
		dmds.setUrl("jdbc:postgresql://localhost/curso");
		dmds.setUsername("curso");
		dmds.setPassword("Fedora2023");
		
		return dmds;		
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(
			"cursojava.jpahibernate.orm.modelobanco.entidades",
			"cursojava.jpahibernate.orm.modelobanco.dto"
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


