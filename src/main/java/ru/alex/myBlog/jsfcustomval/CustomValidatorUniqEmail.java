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

import ru.alex.myBlog.session.UsersFacade;

import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "CustomValidatorUniqEmail")
public class CustomValidatorUniqEmail implements Validator {

    private UsersFacade usersFacade;

    public CustomValidatorUniqEmail() {
        this.usersFacade = CDI.current().select(UsersFacade.class).get();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {

        if (value == null) {
            FacesMessage facesMessage = new FacesMessage("Email is cannot be empty");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }

        Integer uniqEmail = usersFacade.UserUniqEmail((String.valueOf(value)));

        if (uniqEmail >= 1) {
            FacesMessage facesMessage = new FacesMessage("email is USED, change your E-Mail!");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);

        }

    }

}
