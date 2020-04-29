/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.UsersEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;

/**
 *
 * @author lesha
 */
@Named
@Stateless
public class UsersFacade extends AbstractFacade<UsersEntity> implements Serializable {

    @Inject
    private EntityManager em;
    
    @Inject
    Pbkdf2PasswordHash passwordHasher;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(UsersEntity.class);
    }

    @Transactional
    public UsersEntity create (String idusers, String name, String pass, String email, LocalDateTime createDate ){
        UsersEntity newUser = new UsersEntity(idusers, name, passwordHasher.generate(pass.toCharArray()), email, createDate);
        em.persist(newUser);
        return newUser;
    }
    
    public Optional<UsersEntity> getUser(String username){
        return em.createNamedQuery("Users.findByName",UsersEntity.class)
                .setParameter("name", username)
                .getResultStream()
                .findFirst();
    }
    
    public Integer UserUniqLogin(String _login) {
        return (Integer.valueOf(em.createNamedQuery("Users.uniqLogin")
        .setParameter("id_users", _login)
                .getSingleResult()
                .toString()));
    }

    public Integer UserUniqEmail(String _email) {
        return (Integer.valueOf(em.createNamedQuery("Users.uniqEmail")
        .setParameter("email", _email)
                .getSingleResult()
                .toString()));
    }
}
