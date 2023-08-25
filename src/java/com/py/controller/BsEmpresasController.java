package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class BsEmpresasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private BsEmpresas bsEmpresa, bsEmpresaSelected;
    private BsPersonas bsPersonas;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDT;
    private LazyDataModel lazyPersonaDG;

    public void limpiar() {
        bsEmpresa = null;
        bsEmpresaSelected = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void onRowSelectPersona(SelectEvent ep){
        bsPersonas = (BsPersonas) ep.getObject();
        bsEmpresa.setBsPersonas(bsPersonas);
    }
    
       public void guardar() {
        try {
            if (genericEJB.insert(bsEmpresa)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyEmpresaDT();
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
            genericEJB.update(bsEmpresaSelected);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyEmpresaDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar las Notificaciones");
            e.printStackTrace(System.err);
        }
    }
    
      public void delete() {
        try {
            genericEJB.delete(bsEmpresaSelected);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyEmpresaDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Notificacion");
           e.printStackTrace(System.err);
        }
    }
    
//<editor-fold defaultstate="collapsed" desc="Getter&Setters">
    public boolean isEsModificar() {
        return esModificar;
    }
    
    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }
    
    public BsEmpresas getBsEmpresa() {
        if (bsEmpresa == null) {
            bsEmpresa = new BsEmpresas();
            bsEmpresa.setBsPersonas(new BsPersonas());
        }
        return bsEmpresa;
    }
    
    public void setBsEmpresa(BsEmpresas bsEmpresa) {
        this.bsEmpresa = bsEmpresa;
    }
    
    public BsEmpresas getBsEmpresaSelected() {
        if (bsEmpresaSelected == null) {
            bsEmpresaSelected = new BsEmpresas();
            bsEmpresaSelected.setBsPersonas(new BsPersonas());
        }
        return bsEmpresaSelected;
    }
    
    public void setBsEmpresaSelected(BsEmpresas bsEmpresaSelected) {
        this.bsEmpresaSelected = bsEmpresaSelected;
        if (bsEmpresaSelected != null) {
            bsEmpresa = bsEmpresaSelected;
        }
    }
    
    public BsPersonas getBsPersonas() {
        if (bsPersonas == null) {
            bsPersonas = new BsPersonas();
        }
        return bsPersonas;
    }
    
    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }
    
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyEmpresaDT() {
        if (lazyEmpresaDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresaDT = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresaDT;
    }
    
    public void setLazyEmpresaDT(LazyDataModel lazyEmpresaDT) {
        this.lazyEmpresaDT = lazyEmpresaDT;
    }
    
    public LazyDataModel getLazyPersonaDG() {
        if (lazyPersonaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPersonaDG = genericLazy.getLazyDataModel(BsPersonas.class, campos);
        }
        return lazyPersonaDG;
    }
    
    public void setLazyPersonaDG(LazyDataModel lazyPersonaDG) {
        this.lazyPersonaDG = lazyPersonaDG;
    }
//</editor-fold>
    
    
}
