/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.ejb;

import com.py.jpa.CobSaldos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ac3r
 */
@Stateless
public class CobSaldosFacade extends AbstractFacade<CobSaldos> {

    @PersistenceContext(unitName = "WebApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CobSaldosFacade() {
        super(CobSaldos.class);
    }
    
}
