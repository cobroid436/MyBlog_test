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

import org.apache.commons.lang3.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@FacesValidator(value = "CustomValidatorValidateAlphaNumber")
public class CustomValidatorValidateAlphaNumber implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        HtmlInputText htmlInputText = (HtmlInputText) uiComponent;

            if (!StringUtils.isAlphanumeric((String.valueOf(value)))) {
                FacesMessage facesMessage = new FacesMessage(htmlInputText.
                        getLabel() + ": only alphabetic and Number characters are allowed.");
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(facesMessage);
            }

    }

}
