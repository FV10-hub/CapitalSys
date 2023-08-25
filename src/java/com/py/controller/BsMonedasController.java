package com.py.controller;

import com.py.jpa.BsMonedas;
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
public class BsMonedasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyMonedasDT;
    
    private BsMonedas bsMonedas,bsMonedaSeleceted;

    @PostConstruct
    private void ini(){
        limpiar();
    }
    
public void limpiar(){
    bsMonedas = null;
    esModificar = false;
}

    public void guardar() {
        try {
            if (genericEJB.insert(bsMonedas)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyMonedasDT();
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
            genericEJB.update(bsMonedas);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyMonedasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Moneda");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsMonedas);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyMonedasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Moneda");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectMoneda(SelectEvent e){
        bsMonedas = (BsMonedas) e.getObject();
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Monedas del sistema.");
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
            acercaDe.add("- Pantalla: BsMonedas.xhtml");
            acercaDe.add("- Controlador: BsMonedasController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsMonedas.java");
        }
        return acercaDe;
    }
    
    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsMonedas getBsMonedaSeleceted() {
        if (bsMonedaSeleceted == null) {
            bsMonedaSeleceted = new BsMonedas();
        }
        return bsMonedaSeleceted;
    }

    public void setBsMonedaSeleceted(BsMonedas bsMonedaSeleceted) {
        this.bsMonedaSeleceted = bsMonedaSeleceted;
        if (bsMonedaSeleceted != null) {
            bsMonedas = bsMonedaSeleceted;
        }
    }
    
    public BsMonedas getBsMonedas() {
        if (bsMonedas == null) {
            bsMonedas = new BsMonedas();
        }
        return bsMonedas;
    }
    
    public void setBsMonedas(BsMonedas bsMonedas) {
        this.bsMonedas = bsMonedas;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyMonedasDT() {
        if (lazyMonedasDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedasDT = genericLazy.getLazyDataModel(BsMonedas.class, campos);
        }
        return lazyMonedasDT;
    }
    
    public void setLazyMonedasDT(LazyDataModel lazyMonedasDT) {
        this.lazyMonedasDT = lazyMonedasDT;
    }
    
//</editor-fold>
   
    

}
