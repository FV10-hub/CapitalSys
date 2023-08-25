package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
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
public class BsSucursalesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDT;
    private LazyDataModel lazySucursalesDG;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private BsEmpresas bsEmpresa;
    private BsSucursal bsSucursal, bsSucSelected;
    
    @PostConstruct
    private void ini(){
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        bsEmpresa = null;
        getBsEmpresa();
        bsSucursal = null;
        bsSucSelected = null;
        esModificar = false;
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(bsSucursal)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazySucursalesDG();
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
            genericEJB.update(bsSucursal);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazySucursalesDG();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Sucursal");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(bsSucursal);
            mensajeAlerta("Eliminado Correctamente!");
            getLazySucursalesDG();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Sucursal");
            e.printStackTrace(System.err);
        }
    }
    
    public void onRowSelectEmpresa(SelectEvent e) {
        bsEmpresa = (BsEmpresas) e.getObject();
        bsSucursal.setBsEmpresas(bsEmpresa);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Sucursales del sistema.");
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
            acercaDe.add("- Pantalla: BsSucursales.xhtml");
            acercaDe.add("- Controlador: BsSucursalesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsSucursal.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
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

    public BsSucursal getBsSucursal() {
        if (bsSucursal == null) {
            bsSucursal = new BsSucursal();
            bsSucursal.setBsEmpresas(new BsEmpresas());
            bsSucursal.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public BsSucursal getBsSucSelected() {
        if (bsSucSelected == null) {
            bsSucSelected = new BsSucursal();
            bsSucSelected.setBsEmpresas(new BsEmpresas());
            bsSucSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return bsSucSelected;
    }

    public void setBsSucSelected(BsSucursal bsSucSelected) {
        this.bsSucSelected = bsSucSelected;
        if (bsSucSelected != null) {
            bsSucursal = bsSucSelected;
        }
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

    public LazyDataModel getLazySucursalesDG() {
        if (lazySucursalesDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazySucursalesDG = genericLazy.getLazyDataModel(BsSucursal.class, campos);
        }
        return lazySucursalesDG;
    }

    public void setLazySucursalesDG(LazyDataModel lazySucursalesDG) {
        this.lazySucursalesDG = lazySucursalesDG;
    }
//</editor-fold>
}
