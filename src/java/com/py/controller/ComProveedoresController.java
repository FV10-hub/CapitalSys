package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.ComProveedores;
import com.py.jpa.VenVendedor;
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
public class ComProveedoresController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPersonasDG, lazyProveedorDT;

    private ComProveedores proveedor, proveedorSelected;
    private BsPersonas bsPersona;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        proveedor = null;
        proveedorSelected = null;
        bsPersona = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void guardar() {
        try {
            BsEmpresas emp = em.createNamedQuery("BsEmpresas.findById", BsEmpresas.class)
                    .setParameter("id", 1)
                    .getSingleResult();
            proveedor.setBsEmpresas(emp);
            if (genericEJB.insert(proveedor)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyProveedorDT();
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
            genericEJB.update(proveedor);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyProveedorDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Proveedor");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(proveedor);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyProveedorDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Proveedor");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectPersonas(SelectEvent ep) {
        bsPersona = (BsPersonas) ep.getObject();
        proveedor.setBsPersonas(bsPersona);
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
        if (ayuda == null || ayuda.isEmpty()) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Proveedores del sistema.");
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
            acercaDe.add("- Pantalla: ComProveedores.xhtml");
            acercaDe.add("- Controlador: ComProveedoresController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: ComProveedores.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public ComProveedores getProveedor() {
        if (proveedor == null) {
            proveedor = new ComProveedores();
            proveedor.setBsEmpresas(new BsEmpresas());
            proveedor.setBsPersonas(new BsPersonas());
        }
        return proveedor;
    }

    public void setProveedor(ComProveedores proveedor) {
        this.proveedor = proveedor;
    }

    public ComProveedores getProveedorSelected() {
        if (proveedorSelected == null) {
            proveedorSelected = new ComProveedores();
            proveedorSelected.setBsEmpresas(new BsEmpresas());
            proveedorSelected.setBsPersonas(new BsPersonas());
        }
        return proveedorSelected;
    }

    public void setProveedorSelected(ComProveedores proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
        if (proveedorSelected != null) {
            proveedor = proveedorSelected;
        }
    }

    public BsPersonas getBsPersona() {
        if (bsPersona == null) {
            bsPersona = new BsPersonas();
        }
        return bsPersona;
    }

    public void setBsPersona(BsPersonas bsPersona) {
        this.bsPersona = bsPersona;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyPersonasDG() {
        if (lazyPersonasDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPersonasDG = genericLazy.getLazyDataModel(BsPersonas.class, campos);
        }
        return lazyPersonasDG;
    }

    public void setLazyPersonasDG(LazyDataModel lazyPersonasDG) {
        this.lazyPersonasDG = lazyPersonasDG;
    }

    public LazyDataModel getLazyProveedorDT() {
        if (lazyProveedorDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyProveedorDT = genericLazy.getLazyDataModel(ComProveedores.class, campos);
        }
        return lazyProveedorDT;
    }

    public void setLazyProveedorDT(LazyDataModel lazyProveedorDT) {
        this.lazyProveedorDT = lazyProveedorDT;
    }

//</editor-fold>
}
