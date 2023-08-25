package com.py.controller;

import com.py.jpa.BsCiudad;
import com.py.jpa.BsDepartamentos;
import com.py.jpa.BsEmpresas;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPaises;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.TesCuentaBancaria;
import com.py.jpa.TesTipoCuentas;
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
public class TesCuentasBancariasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyCuentasDT, lazyEmpresaDG, lazyTipoDG, lazyPersonaDG,lazyMonedaDG;

    private BsEmpresas empresa;
    private BsPersonas personas;
    private BsMonedas moneda;
    private TesTipoCuentas tipoCuenta;
    private TesCuentaBancaria cuenta, cuentaSelected;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        empresa = null;
        personas = null;
        moneda = null;
        tipoCuenta = null;
        cuenta = null;
        cuentaSelected = null;
        //getTimbrado();
        esModificar = false;
        ayuda = null;
        acercaDe = null;
    }

    public void guardar() {
        try {
            if (genericEJB.insert(cuenta)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyCuentasDT();
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
            genericEJB.update(cuenta);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyCuentasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar la Cuenta Bancaria");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(cuenta);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyCuentasDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Cuenta Bancaria");
            e.printStackTrace(System.err);
        }
    }

    public void onrowSelectEmpresa(SelectEvent o) {
        empresa = (BsEmpresas) o.getObject();
        cuenta.setBsEmpresas(empresa);
    }

    public void onRowSelectPersona(SelectEvent ep) {
        personas = (BsPersonas) ep.getObject();
        cuenta.setBsPersonas(personas);
    }

    public void onRowSelectMoneda(SelectEvent ep) {
        moneda = (BsMonedas) ep.getObject();
        cuenta.setBsMonedas(moneda);
    }

    public void onRowSelectTipo(SelectEvent ep) {
        tipoCuenta = (TesTipoCuentas) ep.getObject();
        cuenta.setTesTipoCuentas(tipoCuenta);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Cuentas Bancarias del sistema.");
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
            acercaDe.add("- Pantalla: TesCuentaBancaria.xhtml");
            acercaDe.add("- Controlador: TesCuentaBancariaController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: TesCuentaBancaria.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsEmpresas getEmpresa() {
        if (empresa == null) {
            empresa = new BsEmpresas();
            empresa.setBsPersonas(new BsPersonas());
        }
        return empresa;
    }

    public void setEmpresa(BsEmpresas empresa) {
        this.empresa = empresa;
    }

    public BsMonedas getMoneda() {
        if (moneda == null) {
            moneda = new BsMonedas();
        }
        return moneda;
    }

    public void setMoneda(BsMonedas moneda) {
        this.moneda = moneda;
    }

    public BsPersonas getPersonas() {
        if (personas == null) {
            personas = new BsPersonas();
        }
        return personas;
    }

    public void setPersonas(BsPersonas personas) {
        this.personas = personas;
    }

    public TesTipoCuentas getTipoCuenta() {
        if (tipoCuenta == null) {
            tipoCuenta = new TesTipoCuentas();
        }
        return tipoCuenta;
    }

    public void setTipoCuenta(TesTipoCuentas tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public TesCuentaBancaria getCuenta() {
        if (cuenta == null) {
            cuenta = new TesCuentaBancaria();
            cuenta.setBsEmpresas(new BsEmpresas());
            cuenta.setBsMonedas(new BsMonedas());
            cuenta.setBsPersonas(new BsPersonas());
            cuenta.setTesTipoCuentas(new TesTipoCuentas());
        }
        return cuenta;
    }

    public void setCuenta(TesCuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    public TesCuentaBancaria getCuentaSelected() {
        if (cuentaSelected == null) {
            cuentaSelected = new TesCuentaBancaria();
            cuentaSelected.setBsEmpresas(new BsEmpresas());
            cuentaSelected.setBsMonedas(new BsMonedas());
            cuentaSelected.setBsPersonas(new BsPersonas());
            cuentaSelected.setTesTipoCuentas(new TesTipoCuentas());
        }
        return cuentaSelected;
    }

    public void setCuentaSelected(TesCuentaBancaria cuentaSelected) {
        this.cuentaSelected = cuentaSelected;
        if (cuentaSelected != null) {
            cuenta = cuentaSelected;
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazyCuentasDT() {
        if (lazyCuentasDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyCuentasDT = genericLazy.getLazyDataModel(TesCuentaBancaria.class, campos);
        }
        return lazyCuentasDT;
    }

    public void setLazyCuentasDT(LazyDataModel lazyCuentasDT) {
        this.lazyCuentasDT = lazyCuentasDT;
    }

    public LazyDataModel getLazyEmpresaDG() {
        if (lazyEmpresaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyEmpresaDG = genericLazy.getLazyDataModel(BsEmpresas.class, campos);
        }
        return lazyEmpresaDG;
    }

    public void setLazyEmpresaDG(LazyDataModel lazyEmpresaDG) {
        this.lazyEmpresaDG = lazyEmpresaDG;
    }

    public LazyDataModel getLazyTipoDG() {
        if (lazyTipoDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoDG = genericLazy.getLazyDataModel(TesTipoCuentas.class, campos);
        }
        return lazyTipoDG;
    }

    public void setLazyTipoDG(LazyDataModel lazyTipoDG) {
        this.lazyTipoDG = lazyTipoDG;
    }

    public LazyDataModel getLazyMonedaDG() {
        if (lazyMonedaDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyMonedaDG = genericLazy.getLazyDataModel(BsMonedas.class, campos);
        }
        return lazyMonedaDG;
    }

    public void setLazyMonedaDG(LazyDataModel lazyMonedaDG) {
        this.lazyMonedaDG = lazyMonedaDG;
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
