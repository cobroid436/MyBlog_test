package ru.alex.myBlog.Controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import ru.alex.myBlog.entity.RolesEntity;
import ru.alex.myBlog.entity.UsersEntity;
import ru.alex.myBlog.session.GroupuserFacade;
import ru.alex.myBlog.session.UsersFacade;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RequestScoped
@Named
public class HomeController implements Serializable {

    @Inject
    UsersFacade usersFacade;

    @Inject
    GroupuserFacade groupuserFacade;

    @Inject
    SecurityContext securityContext;

    @Inject ExternalContext externalContext;

    private Optional<UsersEntity> currentUser;
    private List<RolesEntity> currentRoles;
    private String userName;

    @PostConstruct
    public void initialize() {
//        ExternalContext ec = getExternalContext();
        if (securityContext.getCallerPrincipal() != null) {
            String username = securityContext.getCallerPrincipal().getName();
            this.currentUser = usersFacade.getUser(username);
            usersFacade.getUser(username).ifPresent(user -> {
                this.currentRoles = groupuserFacade.getRoles(user);
                this.userName = user.getIdusers();
            });
        } else {
            this.currentUser = usersFacade.getUser("guest");
            this.userName = "guest";
            this.currentRoles = groupuserFacade.getRoles(this.currentUser.get());
//            securityContext.authenticate((HttpServletRequest)ec.getRequest(), 
//                (HttpServletResponse)ec.getResponse(), 
//                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential("guest", "guest1GUEST")));
        }
    }

    public String urlLogout() throws URISyntaxException {
        //http://#{request.getHeader('host')}#{request.contextPath}/logout

        String curScheme = externalContext.getRequestScheme();
//        String curHost = facesContext.getExternalContext().getRequestServerName();
        String curCntxPth = externalContext.getApplicationContextPath();
        URI uri = new URI(curScheme, curCntxPth + "/logout", null);
        return uri.toString();
    }
//
//    private ExternalContext getExternalContext() {
//        return this.facesContext.getExternalContext();
//    }

    public UsersEntity getCurrentUser() {
        return this.currentUser.orElse(new UsersEntity());
    }

    public List<RolesEntity> getCurrentRoles() {
        return this.currentRoles;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAllowedToSeeUsers() {
        return externalContext.isUserInRole("ADMIN");
    }
    
    public boolean isAllowedToSeeLogout() {
        boolean result = false;
        if (securityContext.getCallerPrincipal() != null) {
            result = true;
        }
        return result;
    }
    
    public boolean isAllowedToSeeRegAut() {
        boolean result = true;
        if (securityContext.getCallerPrincipal() != null) {
            result = false;
        }
        return result;
    }

    public String getCntxPrincipal() {
        if (securityContext.getCallerPrincipal() != null) {
            Map<String, Object> map =externalContext.getRequestMap();
            StringBuilder strB = new StringBuilder();
            map.forEach((k, v) -> {
            strB.append("Key:").append(k).append("<br/>"); 
           });
            
            return strB.toString();
        }
        return "guest";
    }

}
