package com.py.controller;

import com.py.jpa.BsCiudad;
import com.py.jpa.BsDepartamentos;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPaises;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsTalonariosController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyTalonariosDT, lazySucursalDG, lazyTipoDG, lazyTimbradoDG;

    private BsSucursal sucursal;
    private BsTipComprobantes tipoComprobante;
    private BsTimbrados timbrado;
    private BsTalonarios talonarios, talonariosSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        talonarios = null;
        talonariosSelected = null;
        sucursal = null;
        tipoComprobante = null;
        timbrado = null;
        //getTimbrado();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(talonarios)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyTalonariosDT();
            } else {
                mensajeAlerta("No se pudo Guardar");
            }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
        }
    }

    public void edit() {
        try {
            genericEJB.update(talonarios);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyTalonariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Talonarios");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(talonarios);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyTalonariosDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Talonarios");
            e.printStackTrace(System.err);
        }
    }

   public void onrow(SelectEvent o){
       timbrado = (BsTimbrados) o.getObject();
       talonarios.setBsTimbrados(timbrado);
   }

    public void onRowSelectTipo(SelectEvent ep) {
        tipoComprobante = (BsTipComprobantes) ep.getObject();
        talonarios.setBsTipComprobantes(tipoComprobante);
    }

    public void onRowSelectSucursal(SelectEvent ep) {
        sucursal = (BsSucursal) ep.getObject();
        talonarios.setBsSucursal(sucursal);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Talonarios del sistema.");
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
            acercaDe.add("- Pantalla: BsTalonarios.xhtml");
            acercaDe.add("- Controlador: BsTalonariosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsTalonarios.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsSucursal getSucursal() {
        if (sucursal == null) {
            sucursal = new BsSucursal();
            sucursal.setBsEmpresas(new BsEmpresas());
        }
        return sucursal;
    }

    public void setSucursal(BsSucursal sucursal) {
        this.sucursal = sucursal;
    }

    public BsTipComprobantes getTipoComprobante() {
        if (tipoComprobante == null) {
            tipoComprobante = new BsTipComprobantes();
            tipoComprobante.setBsEmpresas(new BsEmpresas());
        }
        return tipoComprobante;
    }

    public void setTipoComprobante(BsTipComprobantes tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
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

    public BsTalonarios getTalonarios() {
        if (talonarios == null) {
            talonarios = new BsTalonarios();
            talonarios.setBsTipComprobantes(new BsTipComprobantes());
            talonarios.setBsSucursal(new BsSucursal());
            talonarios.setBsTimbrados(new BsTimbrados());
            
        }
        return talonarios;
    }

    public void setTalonarios(BsTalonarios talonarios) {
        this.talonarios = talonarios;
    }

    public BsTalonarios getTalonariosSelected() {
        if (talonariosSelected == null) {
            talonariosSelected = new BsTalonarios();
            talonariosSelected.setBsTipComprobantes(new BsTipComprobantes());
            talonariosSelected.setBsSucursal(new BsSucursal());
            talonariosSelected.setBsTimbrados(new BsTimbrados());
        }
        return talonariosSelected;
    }

    public void setTalonariosSelected(BsTalonarios talonariosSelected) {
        this.talonariosSelected = talonariosSelected;
        if (talonariosSelected != null) {
            talonarios = talonariosSelected;
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyTalonariosDT() {
        if (lazyTalonariosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTalonariosDT = genericLazy.getLazyDataModel(BsTalonarios.class, campos);
        }
        return lazyTalonariosDT;
    }

    public void setLazyTalonariosDT(LazyDataModel lazyTalonariosDT) {
        this.lazyTalonariosDT = lazyTalonariosDT;
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

    public LazyDataModel getLazyTipoDG() {
        if (lazyTipoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoDG = genericLazy.getLazyDataModel(BsTipComprobantes.class, campos);
        }
        return lazyTipoDG;
    }

    public void setLazyTipoDG(LazyDataModel lazyTipoDG) {
        this.lazyTipoDG = lazyTipoDG;
    }

    public LazyDataModel getLazyTimbradoDG() {
        if (lazyTimbradoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTimbradoDG = genericLazy.getLazyDataModel(BsTimbrados.class, campos);
        }
        return lazyTimbradoDG;
    }

    public void setLazyTimbradoDG(LazyDataModel lazyTimbradoDG) {
        this.lazyTimbradoDG = lazyTimbradoDG;
    }

//</editor-fold>
}
