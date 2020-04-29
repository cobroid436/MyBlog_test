package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.MessagesEntity;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import ru.alex.myBlog.entity.ArticlesEntity;
import ru.alex.myBlog.entity.UsersEntity;

@Named
@Stateless
public class MessagesFacade extends AbstractFacade<MessagesEntity> implements Serializable {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagesFacade() {
        super(MessagesEntity.class);
    }
    
    @Transactional
    public MessagesEntity create (String text, LocalDateTime date, UsersEntity idusers, ArticlesEntity idarticles){
        MessagesEntity me = new MessagesEntity(text, date, idusers, idarticles);
        em.persist(me);
        return me;
    }

}
