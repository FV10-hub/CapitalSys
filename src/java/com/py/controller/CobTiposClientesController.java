package com.py.controller;

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
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class CobTiposClientesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private CobTipoCliente tipoCliente, tipoClienteSelected;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyTipoClienteDT;

    public void limpiar() {
        tipoCliente = null;
        tipoClienteSelected = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void guardar() {
        try {
            if (genericEJB.insert(tipoCliente)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTipoClienteDT();
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
            genericEJB.update(tipoCliente);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTipoClienteDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Tipo de Cliente");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(tipoCliente);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTipoClienteDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Tipo de Cliente");
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Tipos de Clientes del sistema.");
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
            acercaDe.add("- Pantalla: CobTiposClientes.xhtml");
            acercaDe.add("- Controlador: CobTipoClienteController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobTipoCliente.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CobTipoCliente getTipoCliente() {
        if (tipoCliente == null) {
            tipoCliente = new CobTipoCliente();
        }
        return tipoCliente;
    }

    public void setTipoCliente(CobTipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public CobTipoCliente getTipoClienteSelected() {
        if (tipoClienteSelected == null) {
            tipoClienteSelected = new CobTipoCliente();
        }
        return tipoClienteSelected;
    }

    public void setTipoClienteSelected(CobTipoCliente tipoClienteSelected) {
        this.tipoClienteSelected = tipoClienteSelected;
        if (tipoClienteSelected != null) {
            tipoCliente = tipoClienteSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyTipoClienteDT() {
        if (lazyTipoClienteDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoClienteDT = genericLazy.getLazyDataModel(CobTipoCliente.class, campos);
        }
        return lazyTipoClienteDT;
    }

    public void setLazyTipoClienteDT(LazyDataModel lazyTipoClienteDT) {
        this.lazyTipoClienteDT = lazyTipoClienteDT;
    }
//</editor-fold>

}
