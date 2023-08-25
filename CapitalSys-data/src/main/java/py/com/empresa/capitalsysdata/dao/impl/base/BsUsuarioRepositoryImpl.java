package py.com.empresa.capitalsysdata.dao.impl.base;

import py.com.empresa.capitalsysdata.common.CommonRepository;
import py.com.empresa.capitalsysdata.dao.base.BsUsuarioRepository;
import py.com.empresa.capitalsysentities.entities.base.BsUsuario;

/*
* Aug 24, 2023 - 5:27:24 PM - fvazquez
* 
*/
public class BsUsuarioRepositoryImpl extends CommonRepository<BsUsuario, BsUsuarioRepository> {
	
	public BsUsuario findUsuarioAndPassword(String usuario, String password) {
		return this.repository.findByUsuarioAndPassword(usuario, password);
	}
	
}
