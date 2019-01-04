/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Lancamento;

/**
 *
 * @author Ronan
 */
@Stateless
public class LancamentoFacade extends AbstractFacade<Lancamento> {

    @PersistenceContext(unitName = "WebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LancamentoFacade() {
        super(Lancamento.class);
    }
    
}
