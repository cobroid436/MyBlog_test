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
import javax.inject.Named;

@Named
@FacesValidator(value = "CustomValidatorValidateUniqLogin")
public class CustomValidatorValidateUniqLogin implements Validator {

    private UsersFacade usersFacade;

    public CustomValidatorValidateUniqLogin() {
        this.usersFacade = CDI.current().select(UsersFacade.class).get();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        FacesMessage facesMessage;

        Integer uniqLogin = usersFacade.UserUniqLogin((String.valueOf(value)));

        if (uniqLogin >= 1) {
            facesMessage = new FacesMessage("Login is USED, change your Login!");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }

    }

}
