package py.com.empresa.capitalsysdata.dao.base;

import org.springframework.data.repository.PagingAndSortingRepository;

import py.com.empresa.capitalsysentities.entities.base.BsPersona;

public interface BsPersonaRepository extends PagingAndSortingRepository<BsPersona, Long> {

	BsPersona findByNombre(String nombre);
	
	
}
