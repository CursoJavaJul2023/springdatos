package cursojava.jpahibernate.orm.modelocompras.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cursojava.jpahibernate.orm.modelocompras.entidades.ClienteCompras;

// Interfaz base de todos los repositorios de Spring Data
// public interface RepositorioClientesCompras extends Repository<ClienteCompras, String> {
	
// Interfaz para incorporar métodos CRUD
// public interface RepositorioClientesCompras extends CrudRepository<ClienteCompras, String> {

// Interfaz para incorporar soporte de ordenación y paginación
// public interface RepositorioClientesCompras extends PagingAndSortingRepository<ClienteCompras, String> {
	
// Interfaz para emplear SOLO desde JPA
public interface RepositorioClientesCompras extends JpaRepository<ClienteCompras, String> {

	// Podemos definir métodos para llevar a cabo consultas
	
	// 1. Mediante métodos que emplean nomenclatura documentada en Spring Data
	
	Optional<ClienteCompras> findByNif(String nif);
	
	List<ClienteCompras> findByNombreLikeAndApellidosLikeOrderByNifDesc(String nombre, String apellidos);
	
	
}





