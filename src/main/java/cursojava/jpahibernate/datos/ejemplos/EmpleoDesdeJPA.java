package cursojava.jpahibernate.datos.ejemplos;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cursojava.jpahibernate.datos.config.ConfiguracionParaJPA;
import cursojava.jpahibernate.orm.modelobanco.entidades.Cliente;
import cursojava.jpahibernate.orm.modelobanco.entidades.CuentaPk;
import cursojava.jpahibernate.orm.modelobanco.repositorios.RepositorioCliente;
import cursojava.jpahibernate.orm.modelobanco.repositorios.jdbc.NegocioException;
import cursojava.jpahibernate.orm.modelobanco.servicios.ServicioOperacionesModeloBanco;
import cursojava.jpahibernate.orm.modelocompras.entidades.Articulo;
import cursojava.jpahibernate.orm.modelocompras.entidades.ClienteCompras;
import cursojava.jpahibernate.orm.modelocompras.repositorios.RepositorioClientesCompras;
import cursojava.jpahibernate.orm.modelocompras.servicios.ServiciosModeloCompras;

public class EmpleoDesdeJPA {
	
	public static void main(String[] args) {
				
		try {
			// ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-jpa.xml");
			
			ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfiguracionParaJPA.class);
			
			// probarRepositorioCliente(ctx);
			
			// probarServicioOperacionesBanco(ctx);
			
			// probarConsultasSpringDataJPA(ctx);
			
			ServiciosModeloCompras servicioCompras = ctx.getBean(ServiciosModeloCompras.class);
			
			servicioCompras.efectuarCompra(
				"00000001B",
				new String[] { "ART000001", "ART000002", "ART000003" },
				new Integer[] { 4, 4, 4 }
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void probarConsultasSpringDataJPA(ApplicationContext ctx) {
		RepositorioClientesCompras repoClientes = ctx.getBean(RepositorioClientesCompras.class);
		
		Optional<ClienteCompras> opCliente = repoClientes.findByNif("00000001B");
		opCliente.ifPresent(System.out::println);
		
		System.out.println("---------------------------------------");			
		
		repoClientes.findByNombreLikeAndApellidosLikeOrderByNifDesc("%1", "%1")
			.forEach(System.out::println);
		
		System.out.println("---------------------------------------");			

		repoClientes.buscarClientesPorFechaNacimiento(LocalDate.of(1980, 1, 10))
			.forEach(System.out::println);
		
		System.out.println("---------------------------------------");	
		
		repoClientes.buscarClientesPorFechaNacimientoEnIntervalo(LocalDate.of(1980, 1, 1), LocalDate.of(1980, 1, 31))
			.forEach(System.out::println);
		
		System.out.println("---------------------------------------");	
		
		repoClientes.buscarPorCompraEntreFechas(LocalDate.of(1980, 1, 1), LocalDate.of(1980, 1, 31))
			.forEach(System.out::println);
		
		System.out.println("---------------------------------------");	

		repoClientes.porArticuloComprado("NINGUNO")
			.forEach(System.out::println);
		
		System.out.println("---------------------------------------");	

		repoClientes.porAltaEnElDiaActual()
			.forEach(System.out::println);
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


