package com.py.controller;

import com.py.jpa.BsBarrio;
import com.py.jpa.BsCiudad;
import com.py.jpa.BsDepartamentos;
import com.py.jpa.BsDireccionesPersonas;
import com.py.jpa.BsIdentificaciones;
import com.py.jpa.BsPaises;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsTelefPersonas;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class BsPersonasController extends GenericMensajes implements Serializable {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private GenericoEJB genericEJB;

    private BsPersonas bsPersona, bsPersonaSelected;
    private GenericBigLazyList genericLazy;
    private LazyDataModel lazyPersonaDT, lazyPaisesDG, lazyCiudadesDG, lazyBarriosDT;
    private LazyDataModel lazyDepartamento;
    private BsDepartamentos bsdepartamento;
    private HashMap campos;
    private boolean esModificar;
    private boolean esModificarIden;
    private boolean esModificarTel;
    private boolean esModificarDir;
    private ArrayList<String> ayuda = new ArrayList();
    private ArrayList<String> acercaDe = new ArrayList();
    private BsIdentificaciones bsIdenSelected;
    private List<BsIdentificaciones> listaIden;
    private BsTelefPersonas bsTelefonos;
    private List<BsTelefPersonas> listaTelefonos;
    private BsDireccionesPersonas bsDirecciones;
    private List<BsDireccionesPersonas> listaDirecciones;

    public void limpiar() {
        bsPersona = null;
        bsPersonaSelected = null;
        bsIdenSelected = null;
        bsDirecciones = null;
        bsTelefonos = null;
        getBsDirecciones();

        listaTelefonos = null;
        listaIden = null;
        listaDirecciones = null;

        esModificar = false;
        esModificarIden = false;
        esModificarTel = false;
        esModificarDir = false;
        ayuda = null;
        acercaDe = null;
    }

    @PostConstruct
    private void ini() {
        limpiar();
    }

    public void guardarPersona() {
        try {
            //grabarIden();
            if (listaIden != null) {
                bsPersona.setBsIdentificaciones(listaIden);
            }
            if (listaTelefonos != null) {
                bsPersona.setBsTelefPersonas(listaTelefonos);
            }
            if (listaDirecciones != null) {
                bsPersona.setBsDirecPersonas(listaDirecciones);
            }
            bsPersona.setNombreFantasia(getBsPersona().getNombre() + " " + getBsPersona().getApellidos());
            if (esModificar) {
                genericEJB.update(bsPersona);
                mensajeAlerta("Se Actualizo Correctamente");
            } else {
                genericEJB.insert(bsPersona);
                mensajeAlerta("Se Guardo Correctamente");
            }

            getLazyPersonaDT();
        } catch (Exception e) {
            mensajeAlerta("Error al guardar Persona");
            e.printStackTrace(System.err);
        }
    }

    public void limpiaIdentificacion() {
        bsIdenSelected = null;
    }

    public void limpiarTelefonos() {
        bsTelefonos = null;
    }

    public void limpiarDirecciones() {
        bsDirecciones = null;
    }

    public void addIdentificacion() {
        try {
            BsIdentificaciones obj = new BsIdentificaciones();
            obj = bsIdenSelected;
            obj.setBsPersonas(bsPersona);
            listaIden.add(obj);
        } catch (Exception e) {
            mensajeAlerta("No se cargo la Lista de Identificaciones");
            e.printStackTrace(System.err);
        }
    }

    public void addTelefono() {
        try {
            BsTelefPersonas telef = new BsTelefPersonas();
            telef = bsTelefonos;
            if (telef.getArea() == null) {
                telef.setArea("0");
            }
            telef.setBsPersonas(bsPersona);
            listaTelefonos.add(telef);
        } catch (Exception e) {
            mensajeAlerta("No se cargo la Lista de Telefonos");
            e.printStackTrace(System.err);
        }
    }

    public void addDirecciones() {
        try {
            BsDireccionesPersonas direc = new BsDireccionesPersonas();
            direc = bsDirecciones;
            direc.setBsPersonas(bsPersona);
            listaDirecciones.add(direc);
        } catch (Exception e) {
            mensajeAlerta("No se cargo la Lista de Telefonos");
            e.printStackTrace(System.err);
        }
    }

    public void modificarIdenPer() {
        try {
            for (int i = 0; i < listaIden.size(); i++) {
                if (listaIden.get(i).getNumeroIden().equals(bsIdenSelected.getNumeroIden())) {
                    listaIden.set(i, bsIdenSelected);
                    break;
                }
            }
        } catch (Exception e) {
            mensajeError("Error al modificar a la lista de Identificacion Personal");
            e.printStackTrace(System.err);
        }
    }

    public void modificarTelPer() {
        try {
            for (int i = 0; i < listaTelefonos.size(); i++) {
                if (listaTelefonos.get(i).getNumero().equals(bsTelefonos.getNumero())) {
                    listaTelefonos.set(i, bsTelefonos);
                    break;
                }
            }
        } catch (Exception e) {
            mensajeError("Error al modificar a la lista de Telefonos Personal");
            e.printStackTrace(System.err);
        }
    }

    public void modificarDirec() {
        try {
            for (int i = 0; i < listaDirecciones.size(); i++) {
                if (listaDirecciones.get(i).getDetalle().equals(bsDirecciones.getDetalle())) {
                    listaDirecciones.set(i, bsDirecciones);
                    break;
                }
            }
        } catch (Exception e) {
            mensajeError("Error al modificar a la lista de Direcciones Personales");
            e.printStackTrace(System.err);
        }
    }

    public void identemove() {
        try {
            for (int i = 0; i < listaIden.size(); i++) {
                if (listaIden.get(i).getNumeroIden().equals(bsIdenSelected.getNumeroIden())) {
                    listaIden.remove(i);
                    break;
                }
            }
            genericEJB.borrar(bsIdenSelected);
            getListaIden();
            mensajeAlerta("La Identificacion se ha eliminado con exito.");
        } catch (Exception e) {
            mensajeError("Error al Eliminar Identificacion");
            e.printStackTrace(System.err);
        }
    }

    public void telefRemove() {
        try {
            for (int i = 0; i < listaTelefonos.size(); i++) {
                if (listaTelefonos.get(i).getNumero().equals(bsTelefonos.getNumero())) {
                    listaTelefonos.remove(i);
                    break;
                }
            }
            genericEJB.borrar(bsTelefonos);
            getListaTelefonos();
            mensajeAlerta("El Telefono se ha eliminado con exito.");
        } catch (Exception e) {
            mensajeError("Error al Eliminar Telefono");
            e.printStackTrace(System.err);
        }
    }

    public void direRemove() {
        try {
            for (int i = 0; i < listaDirecciones.size(); i++) {
                if (listaDirecciones.get(i).getDetalle().equals(bsDirecciones.getDetalle())) {
                    listaDirecciones.remove(i);
                    break;
                }
            }
            genericEJB.borrar(bsDirecciones);
            getListaDirecciones();
            mensajeAlerta("La Direccion se ha eliminado con exito.");
        } catch (Exception e) {
            mensajeError("Error al Eliminar Telefono");
            e.printStackTrace(System.err);
        }
    }

    public void borrarPersona() {
        try {
//            if (listaIden != null) {
//                for (BsIdentificaciones idenEliminar : listaIden) {
//                    genericEJB.delete(idenEliminar);
//                }
//            }
//            if (listaTelefonos != null) {
//                for (BsTelefPersonas telefonoEliminar : listaTelefonos) {
//                    genericEJB.delete(telefonoEliminar);
//                }
//            }
//            if (listaDirecciones != null) {
//                for (BsDireccionesPersonas direccionEliminar : listaDirecciones) {
//                    genericEJB.delete(direccionEliminar);
//                }
//            }
            genericEJB.delete(bsPersona);
            mensajeAlerta("Persona Eliminado Correctamente!");
            getLazyPersonaDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar la Persona");
            e.printStackTrace(System.err);
        }
    }

    public void onRowSelectPais(SelectEvent ep) {
        BsPaises auxpais = new BsPaises();
        auxpais = (BsPaises) ep.getObject();
        bsDirecciones.setBsPaises(auxpais);
    }

    public void onRowSelectCiudades(SelectEvent e) {
        BsCiudad bsCiudades = new BsCiudad();
        bsCiudades = (BsCiudad) e.getObject();
        bsDirecciones.setBsCiudad(bsCiudades);
        lazyBarriosDT = null;
        getLazyBarriosDT();
    }

    public void onRowSelectBarrio(SelectEvent epe) {
        BsBarrio bsBarrios = new BsBarrio();
        bsBarrios = (BsBarrio) epe.getObject();
        bsDirecciones.setBsBarrio(bsBarrios);
    }
    
    public void onRowSelectDep(SelectEvent dep){
        bsdepartamento = (BsDepartamentos) dep.getObject();
        bsDirecciones.setBsDepartamentos(bsdepartamento);
    }

//<editor-fold defaultstate="collapsed" desc="Getter&Setters">
    public BsPersonas getBsPersona() {
        if (bsPersona == null) {
            bsPersona = new BsPersonas();
        }
        return bsPersona;
    }

    public void setBsPersona(BsPersonas bsPersona) {
        this.bsPersona = bsPersona;
    }

    public BsPersonas getBsPersonaSelected() {
        if (bsPersonaSelected == null) {
            bsPersonaSelected = new BsPersonas();
        }
        return bsPersonaSelected;
    }

    public BsDepartamentos getBsdepartamento() {
        if (bsdepartamento == null) {
            bsdepartamento = new BsDepartamentos();
            bsdepartamento.setBsPaises(new BsPaises());
        }
        return bsdepartamento;
    }

    public void setBsdepartamento(BsDepartamentos bsdepartamento) {
        this.bsdepartamento = bsdepartamento;
    }

    public void setBsPersonaSelected(BsPersonas bsPersonaSelected) {
        this.bsPersonaSelected = bsPersonaSelected;
        if (bsPersonaSelected != null) {
            bsPersona = bsPersonaSelected;

            String stel = "select * from bs_telef_personas b where b.bs_personas_id = " + bsPersona.getId();
            String stelefono = stel;
            Query qstelefono = em.createNativeQuery(stelefono, BsTelefPersonas.class);
            listaTelefonos = qstelefono.getResultList();

            String sident = "select * from bs_identificaciones b where b.bs_personas_id = " + bsPersona.getId();
            String sidentificacion = sident;
            Query qsident = em.createNativeQuery(sidentificacion, BsIdentificaciones.class);
            listaIden = qsident.getResultList();

            String sdirec = "select * from bs_direcciones_personas b where b.bs_personas_id = " + bsPersona.getId();
            String sdirecciones = sdirec;
            Query qsdirec = em.createNativeQuery(sdirecciones, BsDireccionesPersonas.class);
            listaDirecciones = qsdirec.getResultList();

        }
    }

    public boolean isEsModificar() {
        return esModificar;
    }

    public void setEsModificar(boolean esModificar) {
        this.esModificar = esModificar;
    }

    public boolean isEsModificarIden() {
        return esModificarIden;
    }

    public void setEsModificarIden(boolean esModificarIden) {
        this.esModificarIden = esModificarIden;
    }

    public boolean isEsModificarTel() {
        return esModificarTel;
    }

    public void setEsModificarTel(boolean esModificarTel) {
        this.esModificarTel = esModificarTel;
    }

    public boolean isEsModificarDir() {
        return esModificarDir;
    }

    public void setEsModificarDir(boolean esModificarDir) {
        this.esModificarDir = esModificarDir;
    }

    public ArrayList<String> getAyuda() {
        if (ayuda == null) {
            ayuda = new ArrayList<>();
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Personas del sistema.");
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
            acercaDe.add("- Pantalla: BsPersonas.xhtml");
            acercaDe.add("- Controlador: BsPersonasController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: BsPersonas.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BsIdentificaciones getBsIdenSelected() {
        if (bsIdenSelected == null) {
            bsIdenSelected = new BsIdentificaciones();
            bsIdenSelected.setBsPersonas(new BsPersonas());
        }
        return bsIdenSelected;
    }

    public void setBsIdenSelected(BsIdentificaciones bsIdenSelected) {
        this.bsIdenSelected = bsIdenSelected;
    }

    public List<BsIdentificaciones> getListaIden() {
        if (listaIden == null) {
            listaIden = new ArrayList<>();
        }
        for (BsIdentificaciones in : listaIden) {
            if (in.getBsPersonas() == null) {
                in.setBsPersonas(new BsPersonas());
            }
        }
        return listaIden;
    }

    public void setListaIden(List<BsIdentificaciones> listaIden) {
        this.listaIden = listaIden;
    }

    public BsTelefPersonas getBsTelefonos() {
        if (bsTelefonos == null) {
            bsTelefonos = new BsTelefPersonas();
            bsTelefonos.setBsPersonas(new BsPersonas());
        }
        return bsTelefonos;
    }

    public void setBsTelefonos(BsTelefPersonas bsTelefonos) {
        this.bsTelefonos = bsTelefonos;
    }

    public List<BsTelefPersonas> getListaTelefonos() {
        if (listaTelefonos == null || listaTelefonos.isEmpty()) {
            listaTelefonos = new ArrayList<>();
        }
        for (BsTelefPersonas i : listaTelefonos) {
            if (i.getBsPersonas() == null) {
                i.setBsPersonas(new BsPersonas());
            }
        }
        return listaTelefonos;
    }

    public void setListaTelefonos(List<BsTelefPersonas> listaTelefonos) {
        this.listaTelefonos = listaTelefonos;
    }

    public BsDireccionesPersonas getBsDirecciones() {
        if (bsDirecciones == null) {
            bsDirecciones = new BsDireccionesPersonas();
            bsDirecciones.setBsPersonas(new BsPersonas());
            bsDirecciones.setBsPaises(new BsPaises());
            bsDirecciones.setBsDepartamentos(new BsDepartamentos());
            bsDirecciones.setBsCiudad(new BsCiudad());
            bsDirecciones.setBsBarrio(new BsBarrio());
        }
        return bsDirecciones;
    }

    public void setBsDirecciones(BsDireccionesPersonas bsDirecciones) {
        this.bsDirecciones = bsDirecciones;
    }

    public List<BsDireccionesPersonas> getListaDirecciones() {
        if (listaDirecciones == null || listaDirecciones.isEmpty()) {
            listaDirecciones = new ArrayList<>();
        }
        for (BsDireccionesPersonas iter : listaDirecciones) {
            if (iter.getBsPersonas() == null) {
                iter.setBsPersonas(new BsPersonas());
            }
            if (iter.getBsPaises() == null) {
                iter.setBsPaises(new BsPaises());
            }
            if (iter.getBsDepartamentos() == null) {
                iter.setBsDepartamentos(new BsDepartamentos());
            }
            if (iter.getBsCiudad() == null) {
                iter.setBsCiudad(new BsCiudad());
            }
            if (iter.getBsBarrio() == null) {
                iter.setBsBarrio(new BsBarrio());
            }
        }
        return listaDirecciones;
    }

    public void setListaDirecciones(List<BsDireccionesPersonas> listaDirecciones) {
        this.listaDirecciones = listaDirecciones;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazzys">
    public LazyDataModel getLazyPersonaDT() {
        if (lazyPersonaDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPersonaDT = genericLazy.getLazyDataModel(BsPersonas.class, campos);
        }
        return lazyPersonaDT;
    }

    public void setLazyPersonaDT(LazyDataModel lazyPersonaDT) {
        this.lazyPersonaDT = lazyPersonaDT;
    }

    public LazyDataModel getLazyDepartamento() {
        if (lazyDepartamento == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyDepartamento = genericLazy.getLazyDataModel(BsDepartamentos.class, campos);
        }
        return lazyDepartamento;
    }

    public void setLazyDepartamento(LazyDataModel lazyDepartamento) {
        this.lazyDepartamento = lazyDepartamento;
    }

    public LazyDataModel getLazyCiudadesDG() {
        if (lazyCiudadesDG == null) {
            campos = new HashMap();
            genericLazy = new GenericBigLazyList(em);
            lazyCiudadesDG = genericLazy.getLazyDataModel(BsCiudad.class, campos);
        }
        return lazyCiudadesDG;
    }

    public void setLazyCiudadesDG(LazyDataModel lazyCiudadesDG) {
        this.lazyCiudadesDG = lazyCiudadesDG;
    }

    public LazyDataModel getLazyBarriosDT() {
        if (lazyBarriosDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyBarriosDT = genericLazy.getLazyDataModel(BsBarrio.class, campos);
        }
        return lazyBarriosDT;
    }

    public void setLazyBarriosDT(LazyDataModel lazyBarriosDT) {
        this.lazyBarriosDT = lazyBarriosDT;
    }

    public LazyDataModel getLazyPaisesDG() {
        if (lazyPaisesDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyPaisesDG = genericLazy.getLazyDataModel(BsPaises.class, campos);
        }
        return lazyPaisesDG;
    }

    public void setLazyPaisesDG(LazyDataModel lazyPaisesDG) {
        this.lazyPaisesDG = lazyPaisesDG;
    }

//</editor-fold>
}
