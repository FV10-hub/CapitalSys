
package com.py.converter;

import com.py.ejb.BsPersonasFacade;
import com.py.jpa.BsPersonas;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


@Named(value = "objectConverter")
@ApplicationScoped

public class objectConverter implements Converter,Serializable  {

    /**
     * Creates a new instance of objectConverter
     */
    public objectConverter() {
    }
     public Object getAsObject(FacesContext context, UIComponent component, String value) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (value.trim().equals("")) {
            return null;
        } else {
            return new BsPersonas(Long.parseLong(value));
        }
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((BsPersonas) value).getId());
        }
    }
}
