/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.ejb;

import com.py.jpa.BsPersonas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ac3r
 */
@Stateless
public class BsPersonasFacade extends AbstractFacade<BsPersonas> {

    @PersistenceContext(unitName = "WebApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BsPersonasFacade() {
        super(BsPersonas.class);
    }
    
}
