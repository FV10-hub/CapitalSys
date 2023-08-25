package com.py.utils;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class Navegacion extends GenericController implements Serializable{
 
    private String rutaEmrpesa = "base/BsEmpresas.xhtml";
    private String rutaModulos = "base/BsModulos.xhtml";
    private String rutaSucursales = "base/BsSucursales.xhtml";
    private String rutaCobradores = "cobranzas/CobCobradores.xhtml";

    public String getRutaEmrpesa() {
        return rutaEmrpesa;
    }

    public void setRutaEmrpesa(String rutaEmrpesa) {
        this.rutaEmrpesa = rutaEmrpesa;
    }

    public String getRutaModulos() {
        return rutaModulos;
    }

    public void setRutaModulos(String rutaModulos) {
        this.rutaModulos = rutaModulos;
    }

    public String getRutaSucursales() {
        return rutaSucursales;
    }

    public void setRutaSucursales(String rutaSucursales) {
        this.rutaSucursales = rutaSucursales;
    }

    public String getRutaCobradores() {
        return rutaCobradores;
    }

    public void setRutaCobradores(String rutaCobradores) {
        this.rutaCobradores = rutaCobradores;
    }

    

}
