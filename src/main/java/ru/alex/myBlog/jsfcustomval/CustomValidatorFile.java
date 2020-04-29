package ru.alex.myBlog.jsfcustomval;

import ru.alex.myBlog.Utils.ControllAddSupport;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@FacesValidator(value="CustomFileValidator")
public class CustomValidatorFile implements Validator
{
    private long min =50;
    private long max = 5242880;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;
        String text = "",extension ="", filename="";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
                filename= ControllAddSupport.getFileInfo(file).get("filename");
                extension=ControllAddSupport.getFileInfo(file).get("extension");
                // Do not accept an upload unless it contains the string
                // content="text/html; charset=UTF-8"


                if (extension.equalsIgnoreCase("htm") ||extension.equalsIgnoreCase("html") || extension.equalsIgnoreCase("xhtml")){
                    InputStream is = file.getInputStream();
                    if (file.getSize()<= max && file.getSize()>=min){
                        text = new Scanner(is, "UTF-8").useDelimiter("\\A").next().substring(0,500);
                        is.close();
                        if (!(text.contains("charset=utf-8") || text.contains("charset=UTF-8") || text.contains("charset=\"utf-8\"") || text.contains("charset=\"UTF-8\""))) {
                            FacesMessage facesMessage = new FacesMessage("Данный файл не соответствует кодировке UTF-8! "+  text );
                            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                            throw new ValidatorException(facesMessage);
                        }else {
                            facesContext.addMessage(null, new FacesMessage(
                                    FacesMessage.SEVERITY_INFO, "File it's OK" + "\n", null));
                        }
                    }else {
                        FacesMessage facesMessage = new FacesMessage("Данный файл не соответствует размеру! " + filename);
                        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                        throw new ValidatorException(facesMessage);
                    }
                }else {
                    FacesMessage facesMessage = new FacesMessage("Данный файл не соответствует типу html! " + filename);
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(facesMessage);
                }
            } catch (IOException ex) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Invalid file",ex.getMessage()), ex);
        }

    }
}
