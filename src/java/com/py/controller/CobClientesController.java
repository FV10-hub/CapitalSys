package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
import com.py.jpa.CobClientes;
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
public class CobClientesController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyEmpresaDG;
    private CobClientes clientes, clienteSelected;
    private BsEmpresas bsEmpresa;
    private BsPersonas bsPersonas;
    private CobTipoCliente tipoCliente;
    private LazyDataModel lazyClienteDT;
    private LazyDataModel lazyPersonaDG;
    private LazyDataModel lazyTipoClienteDG;

    public void limpiar() {
        bsEmpresa = null;
        bsPersonas = null;
        clientes = null;
        clienteSelected = null;
        esModificar = false;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void onRowSelectPersona(SelectEvent ep) {
        bsPersonas = (BsPersonas) ep.getObject();
        clientes.setBsPersonas(bsPersonas);
    }
    
    public void onRowSelectEmpresa(SelectEvent em) {
        bsEmpresa =  (BsEmpresas) em.getObject();
        clientes.setBsEmpresas(bsEmpresa);
    }
    
    public void onRowSelectTipoCLlente(SelectEvent tp) {
        tipoCliente =  (CobTipoCliente) tp.getObject();
        clientes.setCobTipoCliente(tipoCliente);
    }

    public void guardar() {
        try {
            if (genericEJB.insert(clientes)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyClienteDT();
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
            genericEJB.update(clientes);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyClienteDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Cliente");
            e.printStackTrace(System.err);
        }
    }

    public void delete() {
        try {
            genericEJB.delete(clientes);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyClienteDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Cliente");
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Clientes del sistema.");
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
            acercaDe.add("- Pantalla: CobClientes.xhtml");
            acercaDe.add("- Controlador: CobClientesController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CobClientes.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public CobClientes getClientes() {
        if (clientes == null) {
            clientes = new CobClientes();
            clientes.setBsPersonas(new BsPersonas());
            clientes.setCobTipoCliente(new CobTipoCliente());
            clientes.setBsEmpresas(new BsEmpresas());
            clientes.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return clientes;
    }

    public void setClientes(CobClientes clientes) {
        this.clientes = clientes;
    }

    public CobClientes getClienteSelected() {
        if (clienteSelected == null) {
            clienteSelected = new CobClientes();
            clienteSelected.setBsPersonas(new BsPersonas());
            clienteSelected.setCobTipoCliente(new CobTipoCliente());
            clienteSelected.setBsEmpresas(new BsEmpresas());
            clienteSelected.getBsEmpresas().setBsPersonas(new BsPersonas());
        }
        return clienteSelected;
    }

    public void setClienteSelected(CobClientes clienteSelected) {
        this.clienteSelected = clienteSelected;
        if (clienteSelected != null) {
            clientes = clienteSelected;
        }
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

    public BsPersonas getBsPersonas() {
        if (bsPersonas == null) {
            bsPersonas = new BsPersonas();
        }
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
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
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">

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

    public LazyDataModel getLazyClienteDT() {
        if (lazyClienteDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyClienteDT = genericLazy.getLazyDataModel(CobClientes.class, campos);
        }
        return lazyClienteDT;
    }

    public void setLazyClienteDT(LazyDataModel lazyClienteDT) {
        this.lazyClienteDT = lazyClienteDT;
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

    public LazyDataModel getLazyTipoClienteDG() {
        if (lazyTipoClienteDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoClienteDG = genericLazy.getLazyDataModel(CobTipoCliente.class, campos);
        }
        return lazyTipoClienteDG;
    }

    public void setLazyTipoClienteDG(LazyDataModel lazyTipoClienteDG) {
        this.lazyTipoClienteDG = lazyTipoClienteDG;
    }
//</editor-fold>

}
