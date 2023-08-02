package cursojava.jpahibernate.datos.ejemplos;

import java.sql.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;

public class EmpleoDesdeJDBC {
	
	public static void main(String[] args) {
				
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-jdbc.xml");
			
			RepositorioCliente repoCliente = ctx.getBean(RepositorioCliente.class);
			
			String nif = "99998888U";
			
			repoCliente.alta(
				new Cliente(nif, "Cliente", "desde String", "C/ Prueba", 28010,
						"Madrid", "Madrid", Date.valueOf("1990-10-01"))
			);
			
			repoCliente.modificar(
				new Cliente(nif, "Nombre cambiado", "Apellidos cambiados", "C/ Prueba", 28010,
						"Madrid", "Madrid", Date.valueOf("1990-10-01")
				)
			);
			
			Cliente clienteEncontrado = repoCliente.buscarPorNif(nif);
			if(clienteEncontrado != null) {
				System.out.println(clienteEncontrado);
			}
			
			repoCliente.buscarTodos().forEach(System.out::println);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


