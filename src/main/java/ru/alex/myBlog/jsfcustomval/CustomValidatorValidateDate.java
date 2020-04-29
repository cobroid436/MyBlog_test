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


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@FacesValidator(value = "CustomValidatorValidateDate")
public class CustomValidatorValidateDate implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException, DateTimeParseException {
        HtmlInputText htmlInputText = (HtmlInputText) uiComponent;
        String data = (String) value;
        Pattern pattern1 = Pattern.compile ("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d");
        LocalDate localDate;
//        Pattern pattern1 = Pattern.compile ("\\d{1,2}[ .-/]\\d{1,2}[ .-/]\\d{2,4}");
//Чтобы отключить чувствительность к регистру, можно использовать Pattern.CASE_INSENSITIVE.

        Matcher matcher1 = pattern1.matcher (data);

        if (!data.isEmpty()) {
            if (!matcher1.matches()) {
                FacesMessage facesMessage = new FacesMessage(htmlInputText.getLabel() + ": data format is not valid, use separator space or . or / or - sample: " +
                        LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(facesMessage);
            }else{
                try{
                    if (data.contains("/")){
                        localDate= LocalDate.parse(data,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    }else if (data.contains("-")){
                        localDate= LocalDate.parse(data,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }else if (data.contains(".")){
                        localDate= LocalDate.parse(data,DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    }else{// if (data.contains(" "))
                        localDate= LocalDate.parse(data,DateTimeFormatter.ofPattern("dd MM yyyy"));
                    }
                    if(localDate.getDayOfMonth()< Integer.parseInt(data.substring(0,2))){
                        FacesMessage facesMessage = new FacesMessage(htmlInputText.getLabel() + ": data is not correct,  sample: " +
                                localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(facesMessage);
                    }
                }catch (DateTimeParseException e){
                    FacesMessage facesMessage = new FacesMessage(htmlInputText.getLabel() + ": data is not correct,  sample: " +
                            LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n"+ e.getLocalizedMessage());
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(facesMessage);
                }
            }
        } else {
            FacesMessage facesMessage = new FacesMessage("Data is cannot be empty");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMessage);
        }
    }
}
