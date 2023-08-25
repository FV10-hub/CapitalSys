/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.ejb;

import com.py.jpa.BsParametrizaciones;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ac3r
 */
@Stateless
public class BsParametrizacionesFacade extends AbstractFacade<BsParametrizaciones> {

    @PersistenceContext(unitName = "WebApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BsParametrizacionesFacade() {
        super(BsParametrizaciones.class);
    }
    
    
      public boolean insertar(BsParametrizaciones entidad){
            getEntityManager().persist(entidad);
            return true;
        
    }
     public boolean borrar(BsParametrizaciones entidad){
            getEntityManager().remove(entidad);
            return true;
        
    }
    
    public boolean actualizar(BsParametrizaciones entidad){
            getEntityManager().merge(entidad);
           // FacesContext.getCurrentInstance().addMessage(null
             //    , new FacesMessage("Modificado jaja con exito!!!"));
            // RequestContext.getCurrentInstance().update("form");
            return true;
        
    }
}
