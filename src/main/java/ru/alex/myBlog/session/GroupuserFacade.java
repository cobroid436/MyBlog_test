/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.GroupuserEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import ru.alex.myBlog.entity.RolesEntity;
import ru.alex.myBlog.entity.UsersEntity;


@Named
@Stateless
public class GroupuserFacade extends AbstractFacade<GroupuserEntity> implements Serializable {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupuserFacade() {
        super(GroupuserEntity.class);
    }

    @Transactional
    public GroupuserEntity create (UsersEntity users, RolesEntity roles ){
        GroupuserEntity gue = new GroupuserEntity(roles, users);
        em.persist(gue);
        return gue;
    }
    
    
    public Optional<UsersEntity> getUser(String username){
        return em.createNamedQuery("Users.findByName",UsersEntity.class)
                .setParameter("name", username)
                .getResultStream()
                .findFirst();
    }
    
        public List<RolesEntity> getRoles(UsersEntity user){
        return em.createNamedQuery("Groupuser.findByUser",RolesEntity.class)
                .setParameter("user", user.getIdusers())
                .getResultList();
    }
}
