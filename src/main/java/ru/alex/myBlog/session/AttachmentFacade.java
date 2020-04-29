package ru.alex.myBlog.session;

import ru.alex.myBlog.entity.AttachmentEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;


@Named
@Stateless
public class AttachmentFacade extends AbstractFacade<AttachmentEntity> implements Serializable {

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

    public AttachmentFacade() {
        super(AttachmentEntity.class);
    }

    @Transactional
    public AttachmentEntity create (String fileName, byte[] fileData, String description, LocalDateTime date, Long refid ){
        refid = refid != null ? refid : 0;
        AttachmentEntity atte = new AttachmentEntity(fileName, fileData, description, date, refid);
        em.persist(atte);
        return atte;
    }
}
