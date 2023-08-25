package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsModulos;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
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
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsTimbradosController extends GenericMensajes implements Serializable {

    Logger logger = Logger.getLogger(BsTimbradosController.class);
    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresas, lazyTimbradosDT;
    private BsEmpresas empresas;
    private BsTimbrados timbrado, timbradoSelected;

    public void limpiar() {
        empresas = null;
        timbrado = null;
        timbradoSelected = null;
        ayuda = null;
        acercaDe = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }
//<editor-fold defaultstate="collapsed" desc="Metodos">

    public void onRowSelectEmpresa(SelectEvent ep) {
        empresas = (BsEmpresas) ep.getObject();
        timbrado.setBsEmpresas(empresas);
    }

    public void guardar() {
        try {
            if (genericEJB.insert(timbrado)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTimbradosDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
            //       }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en guardar ", e);
        }
    }

    public void edit() {
        try {
            genericEJB.update(timbrado);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTimbradosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Timbrado");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en editar ", e);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(timbrado);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTimbradosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Timbrado");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un Error logger en Eliminar ", e);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Timbrados del sistema.");
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
            acercaDe.add("- Pantalla: BsTimbrados.xhtml");
            acercaDe.add("- Controlador: BsTimbradosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsTimbrados.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsEmpresas getEmpresas() {
        if (empresas == null) {
            empresas = new BsEmpresas();
            empresas.setBsPersonas(new BsPersonas());
        }
        return empresas;
    }

    public void setEmpresas(BsEmpresas empresas) {
        this.empresas = empresas;
    }

    public BsTimbrados getTimbrado() {
        if (timbrado == null) {
            timbrado = new BsTimbrados();
            timbrado.setBsEmpresas(new BsEmpresas());
            timbrado.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return timbrado;
    }

    public void setTimbrado(BsTimbrados timbrado) {
        this.timbrado = timbrado;
    }

    public BsTimbrados getTimbradoSelected() {
        if (timbradoSelected == null) {
            timbradoSelected = new BsTimbrados();
            timbradoSelected.setBsEmpresas(new BsEmpresas());
            timbradoSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return timbradoSelected;
    }

    public void setTimbradoSelected(BsTimbrados timbradoSelected) {
        this.timbradoSelected = timbradoSelected;
        if (timbradoSelected != null) {
            timbrado = timbradoSelected;
        }
    }

 
    
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyEmpresas() {
        if (lazyEmpresas == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresas = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresas;
    }

    public void setLazyEmpresas(LazyDataModel lazyEmpresas) {
        this.lazyEmpresas = lazyEmpresas;
    }

    public LazyDataModel getLazyTimbradosDT() {
        if (lazyTimbradosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTimbradosDT = genericLazy.getLazyDataModel(BsTimbrados.class, campos);
        }
        return lazyTimbradosDT;
    }

    public void setLazyTimbradosDT(LazyDataModel lazyTimbradosDT) {
        this.lazyTimbradosDT = lazyTimbradosDT;
    }
//</editor-fold>

}
