package ru.alex.myBlog.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(urlPatterns = "/logout")
public class SecuredLogout extends HttpServlet {

    @Inject
    FacesContext facesContext;

    @Inject
    SecurityContext securityContext;

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ServletContext servletContext = getServletContext();
        if (securityContext.getCallerPrincipal() != null) {
//            req.logout();
//            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/");
//            requestDispatcher.forward(req, resp);

            req.getSession().invalidate();
            // Redrect to Home Page.
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
// #{request.requestURL}
        }
    }
}
