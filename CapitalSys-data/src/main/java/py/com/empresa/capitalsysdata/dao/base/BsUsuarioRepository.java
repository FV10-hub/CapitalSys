package py.com.empresa.capitalsysdata.dao.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.empresa.capitalsysentities.entities.base.BsUsuario;

public interface BsUsuarioRepository extends PagingAndSortingRepository<BsUsuario, Long> {
	
	@Query("SELECT p FROM BsUsuario p WHERE p.codUsuario = ?1 AND p.password = ?2")
	BsUsuario findByUsuarioAndPassword(String codUsuario, String password);
}
