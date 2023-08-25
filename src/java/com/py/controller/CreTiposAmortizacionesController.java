package com.py.controller;

import com.py.jpa.BsMonedas;
import com.py.jpa.CreTipoAmortizacion;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class CreTiposAmortizacionesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyTiposDT;
    
    private CreTipoAmortizacion tipoAmort,tipoAmortSeleceted;

    @PostConstruct
    private void ini(){
        limpiar();
    }
    
public void limpiar(){
    tipoAmort = null;
    esModificar = false;
}

    public void guardar() {
        try {
            if (genericEJB.insert(tipoAmort)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTiposDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
        }
    }
    
    public void edit() {
        try {
            genericEJB.update(tipoAmort);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTiposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Tipos de Amortizacion");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(tipoAmort);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTiposDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Tipos de Amortizacion");
            e.printStackTrace(System.err);
        }
    }


//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public boolean isEsModificar() {
        return esModificar;
    }
    
    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }
    
    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Tipos de Amortizaciones del sistema.");
            ayuda.add("");
            ayuda.add(" Para agregar debe hacar un click en el boton nuevo.");
            ayuda.add(" Para modificar, seleccionar en la grilla con un click, realizar los cambios y presionar guardar.");
            ayuda.add(" Para eliminar, seleccionar en la grilla con un click, luego presionar eliminar.");
        }
        return ayuda;
    }
    
    public void setAyuda(ArrayList<String> ayuda) {
        this.ayuda = ayuda;
    }
    
    public ArrayList<String> getAcercaDe() {
        if (acercaDe == null) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: CreTipoAmortizaciones.xhtml");
            acercaDe.add("- Controlador: CreTipoAmortizacionesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CreTipoAmortizacion.java");
        }
        return acercaDe;
    }
    
    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CreTipoAmortizacion getTipoAmort() {
        if (tipoAmort == null) {
            tipoAmort = new CreTipoAmortizacion();
        }
        return tipoAmort;
    }

    public void setTipoAmort(CreTipoAmortizacion tipoAmort) {
        this.tipoAmort = tipoAmort;
    }

    public CreTipoAmortizacion getTipoAmortSeleceted() {
        if (tipoAmortSeleceted == null) {
            tipoAmortSeleceted = new CreTipoAmortizacion();
        }
        return tipoAmortSeleceted;
    }

    public void setTipoAmortSeleceted(CreTipoAmortizacion tipoAmortSeleceted) {
        this.tipoAmortSeleceted = tipoAmortSeleceted;
        if (tipoAmortSeleceted != null) {
            tipoAmort = tipoAmortSeleceted;
        }
    }


//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyTiposDT() {
        if (lazyTiposDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTiposDT = genericLazy.getLazyDataModel(CreTipoAmortizacion.class, campos);
        }
        return lazyTiposDT;
    }

    public void setLazyTiposDT(LazyDataModel lazyTiposDT) {
        this.lazyTiposDT = lazyTiposDT;
    }
//</editor-fold>


   
    

}
