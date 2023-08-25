package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsUsuarios;
import com.py.jpa.CobCajas;
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
public class CobCajasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCajasDT, lazySucursalDG, lazyUsuarioDG;
    private BsUsuarios bsUsuario;
    private BsSucursal bsSucursal;
    private CobCajas cajas, cajasSelected;

    public void limpiar() {
        bsUsuario = null;
        bsSucursal = null;
        cajas = null;
        cajasSelected = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void onRowSelectSucursal(SelectEvent ep) {
        bsSucursal =  (BsSucursal) ep.getObject();
        cajas.setBsSucursal(bsSucursal);
    }
    
    public void onRowSelectUsuario(SelectEvent em) {
        bsUsuario =  (BsUsuarios) em.getObject();
        cajas.setBsUsuarios(bsUsuario);
    }
    
    public void guardar() {
        try {
            if (genericEJB.insert(cajas)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCajasDT();
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
            genericEJB.update(cajas);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyCajasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Caja");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(cajas);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCajasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Caja");
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Cajas del sistema.");
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
            acercaDe.add("- Pantalla: CobCajas.xhtml");
            acercaDe.add("- Controlador: CobCajasController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobCajas.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsUsuarios getBsUsuario() {
        if (bsUsuario == null) {
            bsUsuario = new BsUsuarios();
            bsUsuario.setBsPersonas(new BsPersonas());
        }
        return bsUsuario;
    }

    public void setBsUsuario(BsUsuarios bsUsuario) {
        this.bsUsuario = bsUsuario;
    }

    public BsSucursal getBsSucursal() {
        if (bsSucursal == null) {
            bsSucursal = new BsSucursal();
            bsSucursal.setBsEmpresas(new BsEmpresas());
        }
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public CobCajas getCajas() {
        if (cajas == null) {
            cajas = new CobCajas();
            cajas.setBsSucursal(new BsSucursal());
            cajas.setBsUsuarios(new BsUsuarios());
        }
        return cajas;
    }

    public void setCajas(CobCajas cajas) {
        this.cajas = cajas;
    }

    public CobCajas getCajasSelected() {
        if (cajasSelected == null) {
            cajasSelected = new CobCajas();
            cajasSelected.setBsSucursal(new BsSucursal());
            cajasSelected.setBsUsuarios(new BsUsuarios());
        }
        return cajasSelected;
    }

    public void setCajasSelected(CobCajas cajasSelected) {
        this.cajasSelected = cajasSelected;
        if (cajasSelected != null) {
            cajas = cajasSelected;
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

    public LazyDataModel getLazyCajasDT() {
        if (lazyCajasDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCajasDT = genericLazy.getLazyDataModel(CobCajas.class, campos);
        }
        return lazyCajasDT;
    }

    public void setLazyCajasDT(LazyDataModel lazyCajasDT) {
        this.lazyCajasDT = lazyCajasDT;
    }

    public LazyDataModel getLazySucursalDG() {
        if (lazySucursalDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazySucursalDG = genericLazy.getLazyDataModel(BsSucursal.class, campos);
        }
        return lazySucursalDG;
    }

    public void setLazySucursalDG(LazyDataModel lazySucursalDG) {
        this.lazySucursalDG = lazySucursalDG;
    }

    public LazyDataModel getLazyUsuarioDG() {
        if (lazyUsuarioDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyUsuarioDG = genericLazy.getLazyDataModel(BsUsuarios.class, campos);
        }
        return lazyUsuarioDG;
    }

    public void setLazyUsuarioDG(LazyDataModel lazyUsuarioDG) {
        this.lazyUsuarioDG = lazyUsuarioDG;
    }
//</editor-fold>

}
