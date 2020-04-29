package ru.alex.myBlog.Controllers;

import lombok.Data;
import ru.alex.myBlog.Utils.ControllAddSupport;
import ru.alex.myBlog.Utils.LocalDateTimeConverter;
import ru.alex.myBlog.entity.ArticlesEntity;
import ru.alex.myBlog.entity.AttachmentEntity;
import ru.alex.myBlog.session.ArticlesFacade;
import ru.alex.myBlog.session.AttachmentFacade;
import ru.alex.myBlog.session.UsersFacade;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.enterprise.SecurityContext;
import ru.alex.myBlog.session.GroupuserHasArticlesFacade;

@Named
@RequestScoped
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
@Data
public class AddArticlesController implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Inject
    private ArticlesFacade articlesFacade;
    private String title;
    private String artdescription;
    private String dataArticle;

    @Inject
    private AttachmentFacade attachmentFacade;
    private String fileName;
    private byte[] fileData;
    private String attdescription;

    @Inject
    private UsersFacade usersFacade;

    @Inject
    private GroupuserHasArticlesFacade groupuserHasArticlesFacade;

    @Inject
    SecurityContext securityContext;

    private UIInput articlesTitleInput;
    private UIInput articlesDescriptionInput;
    private UIInput articlesDateInput;

    private URI bookUri;
    private String referer;
//    private String description;

    private String text;
    private Part file1;

    private static String DIR = null;

    // ======================================
    // =           Private Methods           =
    // ======================================
    private static void setDirUpload() {
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getContext();
        String path = servletContext.getRealPath("/");
        File fl = new File(path + "/upload");
        DIR = fl.getPath() + File.separator;
//        System.out.println (DIR);
    }

    private static String getPaths(Part part) {
        StringBuilder keyvalue;
        keyvalue = new StringBuilder().append("<p> ");

        for (String file : part.getHeader("content-disposition")
                .split(";")) {

            keyvalue.append(file).append("<br>");

        }
        return keyvalue.toString();
    }

    private void upload() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (null != file1) {
            if (DIR == null) {
                setDirUpload();
            }
            try ( InputStream is = file1.getInputStream()) {
                text = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
                is.close();
                /*                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Article loaded." + "\n", null));*/

            } catch (IOException ex) {
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            }
        }

    }

    // ======================================
    // =           Public Methods           =
    // ======================================
    public String articlesTitleStyleClass() {
        if (articlesTitleInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String articlesDescriptionStyleClass() {
        if (articlesDescriptionInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String articlesDateStyleClass() {
        if (articlesDateInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String doAction() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        AttachmentEntity attachmentEntity;
        ArticlesEntity articlesEntity;

        try {
            if (securityContext.getCallerPrincipal() != null) {
                String username = securityContext.getCallerPrincipal().getName();
                upload();
                if (!text.isEmpty()) {
                    fileName = ControllAddSupport.getFileInfo(file1).get("filename");
                    fileData = text.getBytes("UTF-16");
                    attachmentEntity = attachmentFacade.create(fileName, fileData, title, LocalDateTime.now(), null);

                    /*                facesContext.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Attachment: " + attachmentEntity, null));*/
                    if (attachmentEntity.getId().equals(0)) {
                        facesContext.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "attachmentEntity NON store in DB ", null));
                    } else {
                        /*                    facesContext.addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_INFO, "Attachment CREATE ID: " + attachmentEntity.getId(), null));*/

                        articlesEntity = articlesFacade.create(title, artdescription, attachmentEntity, (LocalDate) LocalDateTimeConverter.getAsObject(dataArticle),
                                usersFacade.find(username));
                        // когда будем держать логин активного юзверя

                        if (articlesEntity.getId().equals(0)) {
                            attachmentFacade.remove(attachmentEntity);
                            facesContext.addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Article NON store in DB ", null));
                        } else {
                            /*                        facesContext.addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO, "Articles: " + articlesEntity, null));*/
                            //GroupuserHasArticlesEntity когда будем держать логин активного юзверя
                            //ролей много как быть?
//                        groupuserHasArticlesFacade.create(roles, articlesEntity);

                            String curScheme = facesContext.getExternalContext().getRequestScheme();
//        String curHost = facesContext.getExternalContext().getRequestServerName();
                            String curCntxPth = facesContext.getExternalContext().getApplicationContextPath();
                            StringBuilder book = new StringBuilder();
                            book.append("/pages/secured/viewArticle.xhtml").append("?id=").append(articlesEntity.getId());
                            URI uri = new URI(curScheme, curCntxPth + book.toString(), null);

                            bookUri = UriBuilder.fromUri(uri).build();

                            facesContext.addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_INFO, "Article created. The article " + articlesEntity.getTitle()
                                    + " has been created date:" + articlesEntity.getDate() + " id=" + articlesEntity.getId() + "\n", null));
                        }
                    }
                } else {
                    facesContext.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Article maket not loaded! OR Date convertions error", null));
                }
            }
        } catch (NullPointerException ex) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), null));
            Logger.getLogger(AddArticlesController.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            return "newArctile.xhtml";
        }
    }

    public Object getLinkNew() {
        StringBuilder bookRef;
        if (bookUri != null) {
            bookRef = new StringBuilder().append("<span>Article created: </span>").append('\n');
            bookRef = bookRef.append("<a href=\"").append(bookUri).append("\">LINK</a>");
        } else {
            bookRef = new StringBuilder().append("");
        }
        return (Object) bookRef;
    }

    public Object getFileNew() {
        StringBuilder fileInfo;
        if (DIR == null) {
            fileInfo = new StringBuilder().append("");
        } else if (file1 != null) {

            fileInfo = new StringBuilder().append(getPaths(file1)).append("SIZE =").append(file1.getSize());
        } else {
            fileInfo = new StringBuilder().append("");
        }
        return (Object) fileInfo;
    }

}
