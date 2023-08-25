package com.py.controller;

import com.py.jpa.BsModulos;
import com.py.jpa.BsTipoValor;
import com.py.jpa.CobTipoCliente;
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
public class BsTipoValorController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private BsTipoValor tipoValor, tipoValorSelected;
    private BsModulos modulo;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyTipoValorDT, lazyModuloDG;

    public void limpiar() {
        tipoValor = null;
        tipoValorSelected = null;
        modulo = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void guardar() {
        try {
            if (genericEJB.insert(tipoValor)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTipoValorDT();
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
            genericEJB.update(tipoValor);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTipoValorDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Tipo de Valor");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(tipoValor);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTipoValorDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Tipo de Valor");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectModulo(SelectEvent e) {
        modulo = (BsModulos) e.getObject();
        tipoValor.setBsModulos(modulo);
    }

//<editor-fold defaultstate="collapsed" desc="Getters&Setters">
    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Tipos de Valor del sistema.");
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
        if (acercaDe == null || acercaDe.isEmpty()) {
            acercaDe = new ArrayList<>();
            acercaDe.add("Datos de la pantalla:");
            acercaDe.add("- Pantalla: BsTipoValor.xhtml");
            acercaDe.add("- Controlador: CobTipoClienteController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobTipoCliente.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsTipoValor getTipoValor() {
        if (tipoValor == null) {
            tipoValor = new BsTipoValor();
            tipoValor.setBsModulos(new BsModulos());
        }
        return tipoValor;
    }

    public void setTipoValor(BsTipoValor tipoValor) {
        this.tipoValor = tipoValor;
    }

    public BsTipoValor getTipoValorSelected() {
        if (tipoValorSelected == null) {
            tipoValorSelected = new BsTipoValor();
            tipoValorSelected.setBsModulos(new BsModulos());
        }
        return tipoValorSelected;
    }

    public void setTipoValorSelected(BsTipoValor tipoValorSelected) {
        this.tipoValorSelected = tipoValorSelected;
        if (tipoValorSelected != null) {
            tipoValor = tipoValorSelected;
        }
    }

    public BsModulos getModulo() {
        if (modulo == null) {
            modulo = new BsModulos();
        }
        return modulo;
    }

    public void setModulo(BsModulos modulo) {
        this.modulo = modulo;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyTipoValorDT() {
        if (lazyTipoValorDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoValorDT = genericLazy.getLazyDataModel(BsTipoValor.class, campos);
        }
        return lazyTipoValorDT;
    }

    public void setLazyTipoValorDT(LazyDataModel lazyTipoValorDT) {
        this.lazyTipoValorDT = lazyTipoValorDT;
    }

    public LazyDataModel getLazyModuloDG() {
        if (lazyModuloDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyModuloDG = genericLazy.getLazyDataModel(BsModulos.class, campos);
        }
        return lazyModuloDG;
    }

    public void setLazyModuloDG(LazyDataModel lazyModuloDG) {
        this.lazyModuloDG = lazyModuloDG;
    }

//</editor-fold>
}
