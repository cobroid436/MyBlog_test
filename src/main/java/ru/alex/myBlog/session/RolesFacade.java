/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.RolesEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import javax.transaction.Transactional;

@Named
@Stateless
public class RolesFacade extends AbstractFacade<RolesEntity> implements Serializable {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolesFacade() {
        super(RolesEntity.class);
    }

    @Transactional
    public RolesEntity create (String roles ){
        RolesEntity re = new RolesEntity(roles);
        em.persist(re);
        return re;
    }
}
