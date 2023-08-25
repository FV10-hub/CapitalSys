/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Fernando
 */
public class GenericMensajes {
    private FacesMessage facesMsg;
    public void mensajeExito(String mensaje) {
        facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void mensajeAlerta(String mensaje) {
        facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO/*SEVERITY_WARN*/, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void mensajeError(String mensaje) {
//        JsfUtil.agregarMensajeErrorCampo(null, mensaje);
        facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
}