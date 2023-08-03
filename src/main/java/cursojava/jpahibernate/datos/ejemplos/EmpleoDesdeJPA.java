package cursojava.jpahibernate.datos.ejemplos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cursojava.jpahibernate.datos.config.ConfiguracionParaJPA;
import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;
import cursojava.jpahibernate.orm.modelobanco.servicios.ServicioOperacionesModeloBanco;
import cursojava.jpahibernate.orm.modelocompras.entidades.ClienteCompras;
import cursojava.jpahibernate.orm.modelocompras.repositorios.RepositorioClientesCompras;

public class EmpleoDesdeJPA {
	
	public static void main(String[] args) {
				
		try {
			// ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-jpa.xml");
			
			ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfiguracionParaJPA.class);
			
			// probarRepositorioCliente(ctx);
			
			// probarServicioOperacionesBanco(ctx);
			
			RepositorioClientesCompras repoClientes = ctx.getBean(RepositorioClientesCompras.class);
			
			Optional<ClienteCompras> opCliente = repoClientes.findByNif("00000001B");
			opCliente.ifPresent(System.out::println);
			
			System.out.println("---------------------------------------");			
			
			repoClientes.findByNombreLikeAndApellidosLikeOrderByNifDesc("%1", "%2")
				.forEach(System.out::println);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void probarServicioOperacionesBanco(ApplicationContext ctx) {
		ServicioOperacionesModeloBanco srvOpBanco = ctx.getBean(ServicioOperacionesModeloBanco.class);
		
		// srvOpBanco.cargarConDatosModeloBanco(1, 1000, 10, 10, 10, 4);
		
//			srvOpBanco.hacerTransferencia(
//				new CuentaPk("0001", "0001", "0000000001"),
//				new CuentaPk("0001", "0001", "0000000002"),
//				BigDecimal.valueOf(10.0)
//			);
	}

	private static void probarRepositorioCliente(ApplicationContext ctx) throws NegocioException {
		
		RepositorioCliente repoCliente = ctx.getBean(RepositorioCliente.class);
		
		String nif = "99998888X";
		
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
	}

}


