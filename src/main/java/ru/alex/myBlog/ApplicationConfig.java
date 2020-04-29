package ru.alex.myBlog;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/myblog",
        callerQuery = "select pass user_password from users where id_users = ?",
        groupsQuery = "SELECT t0.id_role user_group FROM myblog.groupuser t1 LEFT OUTER JOIN myblog.roles t0 ON (t0.id_role = t1.id_role) WHERE (t1.id_users = ?)"
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
    
}
