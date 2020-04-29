package ru.alex.myBlog.Utils;


import javax.faces.application.FacesMessage;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Faces converter for support of LocalDate
 * @author Juneau
 */
//@FacesConverter(value="localDateTimeConverter")
public class LocalDateTimeConverter {

    public static Object getAsObject(String value) throws DateTimeParseException, ParseException {
        LocalDate localDate = LocalDate.now();
        try {
            if (value.contains("/")){
                localDate= LocalDate.parse(value,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }else if (value.contains("-")){
                localDate= LocalDate.parse(value,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }else if (value.contains(".")){
                localDate= LocalDate.parse(value,DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }else{// if (data.contains(" "))
                localDate= LocalDate.parse(value,DateTimeFormatter.ofPattern("dd MM yyyy"));
            }
        }catch (DateTimeParseException ex){
            FacesMessage facesMessage = new FacesMessage("Error data format:" + ex.getMessage());
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
//            context.addMessage(null,facesMessage);
            throw new ParseException("Error data format:" + ex.getMessage(),0);
        }
        return localDate;
    }

    public static String getAsString(Object value) {

        LocalDate dateValue = (LocalDate) value;

        return dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}

