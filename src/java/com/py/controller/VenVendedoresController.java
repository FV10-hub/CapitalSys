package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsPersonas;
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
public class VenVendedoresController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;
    private HashMap campos;
    private boolean esModificar;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();

    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPersonasDG, lazyVendedoresDT;

    private VenVendedor venVendedores, venVendedoresSelected;
    private BsPersonas bsPersona;

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void limpiar() {
        venVendedores = null;
        venVendedoresSelected = null;
        bsPersona = null;
        esModificar = false;
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void guardar() {
        try {
            BsEmpresas emp = em.createNamedQuery("BsEmpresas.findById", BsEmpresas.class)
                    .setParameter("id", 1)
                    .getSingleResult();
            venVendedores.setBsEmpresas(emp);
            if (genericEJB.insert(venVendedores)) {
                mensajeAlerta("Se guardo Correctamente");
                getLazyVendedoresDT();
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
            genericEJB.update(venVendedores);
            //esConfirmado = true;
            mensajeAlerta("Modificado Correctamente!");
            getLazyVendedoresDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Vendedor");
            e.printStackTrace(System.err);
        }
    }
    
    public void delete() {
        try {
            genericEJB.delete(venVendedores);
            mensajeAlerta("Eliminado Correctamente!");
            getLazyVendedoresDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Vendedor");
            e.printStackTrace(System.err);
        }
    }
    
    public void onRowSelectPersonas(SelectEvent ep) {
        bsPersona = (BsPersonas) ep.getObject();
        venVendedores.setBsPersonas(bsPersona);
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Vendedores del sistema.");
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
            acercaDe.add("- Pantalla: CobCobradores.xhtml");
            acercaDe.add("- Controlador: VenVendedoresController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: VenVendedores.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public VenVendedor getVenVendedores() {
        if (venVendedores == null) {
            venVendedores = new VenVendedor();
            venVendedores.setBsEmpresas(new BsEmpresas());
            venVendedores.setBsPersonas(new BsPersonas());
        }
        return venVendedores;
    }

    public void setVenVendedores(VenVendedor venVendedores) {
        this.venVendedores = venVendedores;
    }

    public VenVendedor getVenVendedoresSelected() {
        if (venVendedoresSelected == null) {
            venVendedoresSelected = new VenVendedor();
            venVendedoresSelected.setBsEmpresas(new BsEmpresas());
            venVendedoresSelected.setBsPersonas(new BsPersonas());
        }
        return venVendedoresSelected;
    }

    public void setVenVendedoresSelected(VenVendedor venVendedoresSelected) {
        this.venVendedoresSelected = venVendedoresSelected;
        if (venVendedoresSelected != null) {
            venVendedores = venVendedoresSelected;
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

    public LazyDataModel getLazyVendedoresDT() {
        if (lazyVendedoresDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyVendedoresDT = genericLazy.getLazyDataModel(VenVendedor.class, campos);
        }
        return lazyVendedoresDT;
    }

    public void setLazyVendedoresDT(LazyDataModel lazyVendedoresDT) {
        this.lazyVendedoresDT = lazyVendedoresDT;
    }
//</editor-fold>

}
