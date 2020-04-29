package ru.alex.myBlog.Controllers;

import lombok.Data;
import ru.alex.myBlog.entity.ArticlesEntity;
import ru.alex.myBlog.entity.AttachmentEntity;
import ru.alex.myBlog.session.ArticlesFacade;
import ru.alex.myBlog.session.AttachmentFacade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
/**
 * @author lesha
 */
@Named
@RequestScoped
@Data
public class ViewArticleController implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Inject
    private ArticlesFacade articlesFacade;
    private ArticlesEntity articlesEntity = new ArticlesEntity();
    @Inject
    private AttachmentFacade attachmentFacade;
    private AttachmentEntity  attachmentEntity = new AttachmentEntity();


    private long idtext;


    // ======================================
    // =           Private Methods           =
    // ======================================


    // ======================================
    // =           Public Methods           =
    // ======================================
/*    public String getText (){//говорят серверлету нужно настроить
        FacesContext facesContext = FacesContext.getCurrentInstance();
        byte[] blobText = articlesEntity.getIdtext().getFileData();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(blobText);
        String text = stringBuilder.toString();
        facesContext.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Article text data:" + text.length() + "Article blob data:" + blobText.length , null));
        return text;
    }*/


    public void doFindArticleById() {
        articlesEntity = articlesFacade.find(articlesEntity.getId());
        idtext=articlesEntity.getIdtext().getId();
    }


}
