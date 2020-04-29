/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alex.myBlog.Controllers;

import lombok.Data;
import ru.alex.myBlog.entity.GroupuserEntity;
import ru.alex.myBlog.entity.RolesEntity;
import ru.alex.myBlog.entity.UsersEntity;
import ru.alex.myBlog.session.GroupuserFacade;
import ru.alex.myBlog.session.RolesFacade;
import ru.alex.myBlog.session.UsersFacade;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Named
@RequestScoped
public class AddUserController implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Inject
    private UsersFacade usersFacade;
    private String idusers;
    private String name;
    private String pass;
    private String email;

    @Inject
    private GroupuserFacade groupuserFacade;
//    private GroupuserEntity groupuserEntity = new GroupuserEntity();
    
    @Inject
    private RolesFacade rolesFacade;
//    private RolesEntity rolesEntity = new RolesEntity();

    private UIInput idusersInput;
    private UIInput nameInput;
    private UIInput emailInput;
    private UIInput passwordInput;
    private UIInput passwordRetryInput;

    // ======================================
    // =           Public Methods           =
    // ======================================
    public String idusersInputStyleClass() {
        if (idusersInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String emailStyleClass() {
        if (emailInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String passwordStyleClass() {
        if (passwordInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }

    public String passwordRetryStyleClass() {
        if (passwordRetryInput.isValid()) {
            return "labelClass";
        } else {
            return "errorClass";
        }
    }
    
    

    public String doCreateUser() {
        LocalDateTime curday = LocalDateTime.now();
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (!passwordRetryInput.getValue().toString().equals(passwordInput.getValue().toString())) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!", "Passwords do not match!");
            facesContext.addMessage(null,message);
        }else {
        try {
            UsersEntity usersEntity = usersFacade.create(idusers, name, pass, email, curday );

/*            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Pass created. The pass: " + usersEntity.getPass(), null));*/

            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "User created. The login: " + usersEntity.getIdusers()
                    + " has been created date:" + usersEntity.getCreateDate() + " email:" + usersEntity.getEmail(), null));

            groupuserFacade.create(usersEntity,rolesFacade.find("USER"));//Default group new user


/*            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "GroupuserEntity created: " + groupuserEntity, null));*/


        } catch (NullPointerException ex) {
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage() +'\n' , null));
        }
    }
        return "registration.xhtml";
    }

}
