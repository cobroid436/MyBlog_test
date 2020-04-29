package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.GroupuserHasArticlesEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import javax.transaction.Transactional;
import ru.alex.myBlog.entity.ArticlesEntity;
import ru.alex.myBlog.entity.RolesEntity;


@Named
@Stateless
public class GroupuserHasArticlesFacade extends AbstractFacade<GroupuserHasArticlesEntity> implements Serializable {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GroupuserHasArticlesFacade() {
        super(GroupuserHasArticlesEntity.class);
    }
    
    @Transactional
    public GroupuserHasArticlesEntity create (RolesEntity roles, ArticlesEntity idarticles){
        GroupuserHasArticlesEntity guhae = new GroupuserHasArticlesEntity(roles, idarticles);
        em.persist(guhae);
        return guhae;
    }
}
