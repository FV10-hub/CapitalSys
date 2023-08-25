package com.py.ejb;

import com.py.jpa.BsBarrio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ac3r
 */
@Stateless
public class BsBarrioFacade extends AbstractFacade<BsBarrio> {

    @PersistenceContext(unitName = "WebApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BsBarrioFacade() {
        super(BsBarrio.class);
    }
    
}
