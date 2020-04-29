package ru.alex.myBlog.session;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lesha
 */
public class DBProducer {

    @Produces
    @PersistenceContext(unitName = "MyBlogPU")
    private EntityManager em;
}
