package ru.alex.myBlog.Controllers;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

@RequestScoped
@Named
public class LoginController implements Serializable {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Inject
    FacesContext facesContext;

    @Inject
    SecurityContext securityContext;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String requestInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("getRequestPathInfo:");
        sb.append(getExternalContext().getRequestPathInfo());
        sb.append("<br>");
        sb.append("getRequestContextPath:");
        sb.append(getExternalContext().getRequestContextPath());
        sb.append("<br>");
        return sb.toString();
    }

    public void execute() throws IOException {
//        String requestUri = request.getRequestURI();
//
//        // Store the current page to redirect to after successful login.
//        int redirectId = AppUtils.storeRedirectAfterLoginUrl(request.getSession(), requestUri);
//
//        response.sendRedirect(wrapRequest.getContextPath() + "/login?redirectId=" + redirectId);
//        return;
        switch (processAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Creditials", null));
                break;
            case SUCCESS:
                getExternalContext().redirect(getExternalContext().getRequestContextPath()+"/index.xhtml");
                break;
        }
    }

    private AuthenticationStatus processAuthentication() throws IllegalArgumentException {
        ExternalContext ec = getExternalContext();

        return securityContext.authenticate((HttpServletRequest) ec.getRequest(),
                (HttpServletResponse) ec.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(username, password)));
    }

    private ExternalContext getExternalContext() {
        return facesContext.getExternalContext();
    }
}
