/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.converter;

import com.py.ejb.BsPersonasFacade;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;

/**
 *
 * @author Win10
 */
@FacesConverter("personaConverter")
public class PersonasConverter implements Converter {

    @EJB
    BsPersonasFacade perEJB;

    @Override
    public Object getAsObject(final FacesContext arg0, final UIComponent arg1, final String value) {
//        if(value != null && value.trim().length() > 0){
//            try {
//                EntidadPersona per = (EntidadPersona) perEJB.find(value);
//                return per;
//            }
//            catch(Exception e){
//                e.printStackTrace();
//                return null;
//            }
//        }
        return null;
    }

    @Override
    public String getAsString(final FacesContext arg0, final UIComponent arg1, final Object o) {
        if (o != null && o.toString() != null) {
            return o.toString();
        } else {
            return null;
        }
    }
}
