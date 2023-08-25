package py.com.empresa.capitalsysdata.dao.impl.base;

import py.com.empresa.capitalsysdata.common.CommonRepository;
import py.com.empresa.capitalsysdata.dao.base.BsPersonaRepository;
import py.com.empresa.capitalsysentities.entities.base.BsPersona;

/*
* Aug 24, 2023 - 5:27:24 PM - fvazquez
* 
*/
public class BsPersonaRepositoryImpl extends CommonRepository<BsPersona, BsPersonaRepository> {
	
	public BsPersona findByNombre(String nombre) {
		return this.repository.findByNombre(nombre);
	}
	
}
