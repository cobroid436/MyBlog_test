package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.ArticlesEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.time.LocalDate;
import javax.transaction.Transactional;
import ru.alex.myBlog.entity.AttachmentEntity;
import ru.alex.myBlog.entity.UsersEntity;

/**
 *
 * @author lesha
 */
@Named
@Stateless
public class ArticlesFacade extends AbstractFacade<ArticlesEntity> implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Inject
    private EntityManager em;

    // ======================================
    // =          Business methods          =
    // ======================================
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticlesFacade() {
        super(ArticlesEntity.class);
    }
    
    @Transactional
    public ArticlesEntity create (String title, String description, AttachmentEntity idtext, LocalDate date, UsersEntity idusers){
        ArticlesEntity ae = new ArticlesEntity(title, description, idtext, date, idusers);
        em.persist(ae);
        return ae;
    }
}
