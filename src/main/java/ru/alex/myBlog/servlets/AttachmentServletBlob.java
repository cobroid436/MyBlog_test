package ru.alex.myBlog.servlets;

import ru.alex.myBlog.Controllers.ViewArticleController;
import ru.alex.myBlog.entity.ArticlesEntity;
import ru.alex.myBlog.entity.AttachmentEntity;
import ru.alex.myBlog.session.ArticlesFacade;
import ru.alex.myBlog.session.AttachmentFacade;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(urlPatterns = "/pages/secured/attachmentblob/*")
// for download content form server to client as file stream
public class AttachmentServletBlob extends HttpServlet{
    // ======================================
    // =             Attributes             =
    // ======================================
    @Inject
    private ArticlesFacade articlesFacade;
    private ArticlesEntity articlesEntity = new ArticlesEntity();
    @Inject
    private AttachmentFacade attachmentFacade;
    private AttachmentEntity attachmentEntity = new AttachmentEntity();
    @Inject
    ViewArticleController viewArticleController;

    // ======================================
    // =           Private Methods           =
    // ======================================


    // ======================================
    // =           Public Methods           =
    // ======================================


    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
           try (OutputStream out = response.getOutputStream()) {
                long id = Integer.valueOf(request.getParameter("id"));
                attachmentEntity = attachmentFacade.find(id);
                byte[] text = attachmentEntity.getFileData();
                long textLength = text.length;

                response.setContentLengthLong(textLength);
                out.write(text);
            } catch (Exception ex) {
                Logger.getLogger(AttachmentServletBlob.class.getName()).log(Level.SEVERE, null, ex);
            }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Attachment blob data transfer";
    }
}

