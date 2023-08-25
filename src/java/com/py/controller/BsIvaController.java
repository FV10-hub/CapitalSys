package com.py.controller;

import com.py.jpa.BsIva;
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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsIvaController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyIvaDT;

    private BsIva bsIva, bsIvaSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        bsIva = null;
        bsIvaSelected = null;
        getBsIva();
        getBsIvaSelected();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }
//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void guardar() {
        try {
            if (genericEJB.insert(bsIva)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyIvaDT();
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
            genericEJB.update(bsIva);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyIvaDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el IVA");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsIva);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyIvaDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el IVA");
            e.printStackTrace(System.err);
        }
    }
//</editor-fold>
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar IVAS del sistema.");
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
            acercaDe.add("- Pantalla: BsIva.xhtml");
            acercaDe.add("- Controlador: BsIvaController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsIva.java");
        }
        return acercaDe;
    }
    
    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }
    
    public BsIva getBsIva() {
        if (bsIva == null) {
            bsIva = new BsIva();
        }
        return bsIva;
    }
    
    public void setBsIva(BsIva bsIva) {
        this.bsIva = bsIva;
    }
    
    public BsIva getBsIvaSelected() {
        if (bsIvaSelected == null) {
            bsIvaSelected = new BsIva();
        }
        return bsIvaSelected;
    }
    
    public void setBsIvaSelected(BsIva bsIvaSelected) {
        this.bsIvaSelected = bsIvaSelected;
        if(bsIvaSelected != null){
            bsIva = bsIvaSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyIvaDT() {
        if (lazyIvaDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyIvaDT = genericLazy.getLazyDataModel(BsIva.class, campos);
        }
        return lazyIvaDT;
    }
    
    public void setLazyIvaDT(LazyDataModel lazyIvaDT) {
        this.lazyIvaDT = lazyIvaDT;
    }
//</editor-fold>

}
