/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.alex.myBlog.jsfcustomval;

/**
 *
 * @author lesha
 */

import ru.alex.myBlog.Controllers.AddUserController;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "CustomValidatorValidatePassRetry")
public class CustomValidatorValidatePassRetry implements Validator {

    private AddUserController addUserController;

    public CustomValidatorValidatePassRetry() {
//        this.userController = CDI.current().select(UserController.class).get();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        HtmlInputSecret htmlInputSecret = (HtmlInputSecret) uiComponent;
        HtmlInputSecret passwordField = (HtmlInputSecret) facesContext.getViewRoot().findComponent("registrationForm:userPassword");
        if (passwordField == null)
            throw new IllegalArgumentException(String.format("Unable to find component."));
        String password = (String) passwordField.getValue();
            FacesMessage facesMessage = new FacesMessage(htmlInputSecret.getLabel() + ": pass :" + password);
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);


/*        if (value != null) {
            String pass2 = value.toString();
            if (!StringUtils.isEmpty(pass2)) {
                if (!userController.getUserPasswordInput().getValue().equals(pass2)) {
                    FacesMessage facesMessage = new FacesMessage(htmlInputSecret.getLabel() + ": pass2 not equals for pass");
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(facesMessage);
                }
            }
        }*/

    }
}
