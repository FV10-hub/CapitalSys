package com.py.controller;

import com.py.jpa.BsPersonas;
import com.py.jpa.BsUsuarios;
import com.py.utils.ConstantesAplicacion;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fernando
 */
@Named
@SessionScoped
public class BsLogin extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private BsUsuarios bsUsuario;
    private String user;
    private String pass;
    private HttpSession session;

    public String login() {
        try {

            Query codUsuario = em.createQuery("SELECT b FROM BsUsuarios b WHERE b.codUsuario = :codUsuario", BsUsuarios.class)
                    .setParameter("codUsuario", user);

            if (codUsuario != null) {
                bsUsuario = (BsUsuarios) codUsuario.getSingleResult();
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                session = null;
                session = (HttpSession) ctx.getSession(false);
                session.setAttribute(ConstantesAplicacion.USUARIOSESSION, bsUsuario.getCodUsuario());
                 System.out.println(" constante LOGIN "+session.getAttribute(ConstantesAplicacion.USUARIOSESSION));
                if (bsUsuario.getSena().equals(pass)) {
                    return "inicio";
                } else {
                    mensajeError("La Sena es Incorrecta");
                    return "login";
                }
            } else {
                mensajeError("El Codigo Usuario no Existe");
                return "login";
            }
           
        } catch (Exception e) {
            mensajeError("Usuario y/o Contraseña incorrectas");
            e.printStackTrace(System.err);
            user = null;
            pass = null;
            return "login";
        }
    }

    public void logout() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            ctx.invalidateSession();
            ctx.redirect(ctxPath + "/login.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public BsUsuarios getBsUsuario() {
        if (bsUsuario == null) {
            bsUsuario = new BsUsuarios();
            bsUsuario.setBsPersonas(new BsPersonas());
        }
        return bsUsuario;
    }

    public void setBsUsuario(BsUsuarios bsUsuario) {
        this.bsUsuario = bsUsuario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
