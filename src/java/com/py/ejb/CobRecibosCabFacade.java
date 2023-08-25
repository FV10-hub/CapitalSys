/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.ejb;

import com.py.jpa.CobRecibosCab;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ac3r
 */
@Stateless
public class CobRecibosCabFacade extends AbstractFacade<CobRecibosCab> {

    @PersistenceContext(unitName = "WebApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CobRecibosCabFacade() {
        super(CobRecibosCab.class);
    }
    
}
