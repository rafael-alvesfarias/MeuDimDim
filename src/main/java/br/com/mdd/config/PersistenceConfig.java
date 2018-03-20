package br.com.mdd.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.mdd.domain.model.Account;
import br.com.mdd.domain.model.Category;
import br.com.mdd.domain.model.Expense;
import br.com.mdd.domain.model.FixedExpense;
import br.com.mdd.domain.model.Income;
import br.com.mdd.domain.model.Investment;
import br.com.mdd.domain.model.Role;
import br.com.mdd.domain.model.User;
import br.com.mdd.domain.model.VariableExpense;

@Configuration
@EnableTransactionManagement
@PropertySource({ "file:${app_root_dir}/conf/persistence-mysql.properties" })
public class PersistenceConfig {
	
	@Autowired
	private Environment env;
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
	    dataSource.setUrl(env.getProperty("jdbc.url"));
	    dataSource.setUsername(env.getProperty("jdbc.user"));
	    dataSource.setPassword(env.getProperty("jdbc.pass"));
	 
	    return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		
		sessionBuilder
			.addAnnotatedClasses(Category.class,
					Expense.class,
					FixedExpense.class,
					Income.class,
					User.class,
					Role.class,
					VariableExpense.class,
					Investment.class,
					Account.class);
		
		sessionBuilder.addProperties(getHibernateProperties());
		
		return sessionBuilder.buildSessionFactory();
	}
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	 
	    return transactionManager;
	}
	
	private Properties getHibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
	    properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
	    
	    return properties;
	}
}
