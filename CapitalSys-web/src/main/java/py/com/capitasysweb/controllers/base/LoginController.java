package py.com.capitasysweb.controllers.base;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import py.com.capitasysweb.utils.CommonUtils;


/**
 * Este controlador se va encargar de manejar el flujo de inicio de sesion
 **/
@ManagedBean
@ViewScoped
public class LoginController {
	// atributos
	private String username;
	private String password;

	// metodos
	public void login() {
		if ("admin".equalsIgnoreCase(username) && "admin".equalsIgnoreCase(password)) {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_INFO, "¡EXITOSO!", "Bienvenido al home :)");
		}else {
			CommonUtils.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "¡UPS!", "El usuario y/o contraseña son incorrectos");
		}
		
	}

	//getters y setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
