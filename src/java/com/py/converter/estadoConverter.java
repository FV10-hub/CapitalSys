
package com.py.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("estadoConverter")
public class estadoConverter implements Converter {
    
	 @Override  
	    public String getAsString(FacesContext context, UIComponent component, Object value) {  
	        String estado = "";
                if (value != null) {  
	        	estado = (String) value;  
	            switch (estado){
                        case "A":
                           estado = "ACTIVO";
                           break;
                           case "I":
                           estado = "INACTIVO";
                           break;
                    } 
	        }  
	        return estado;  
	    } 

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     
}