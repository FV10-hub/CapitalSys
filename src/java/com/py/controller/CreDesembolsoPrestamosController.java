package com.py.controller;

import com.py.jpa.BsEmpresas;
import com.py.jpa.BsMonedas;
import com.py.jpa.BsPersonas;
import com.py.jpa.BsSucursal;
import com.py.jpa.BsTalonarios;
import com.py.jpa.BsTimbrados;
import com.py.jpa.BsTipComprobantes;
import com.py.jpa.CobClientes;
import com.py.jpa.CobSaldos;
import com.py.jpa.CrMotivosPrestamos;
import com.py.jpa.CreDesembolsoDet;
import com.py.jpa.CreDesembolsosCab;
import com.py.jpa.CreSolicitudes;
import com.py.jpa.CreTipoAmortizacion;
import com.py.jpa.VenVendedor;
import com.py.utils.GenericBigLazyList;
import com.py.utils.GenericController;
import com.py.utils.GenericMensajes;
import com.py.utils.GenericoEJB;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Fernando
 */
@Named
@ViewScoped
public class CreDesembolsoPrestamosController extends GenericMensajes implements Serializable {

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
    private LazyDataModel lazySolcitudDG;
    private LazyDataModel lazyTalonarioDG;
    private LazyDataModel lazyTipoAmortDG;
    private LazyDataModel lazyDesembolsoDT;

    private CreTipoAmortizacion tipoAmort;
    private BsTalonarios talonario;
    private CreSolicitudes solicitud;
    private CreDesembolsosCab desembolso, desembolsoSelected;
    private CreDesembolsoDet detalle;
    private List<CreDesembolsoDet> listaDetalle;
    private BigDecimal totalCapital, totalInteres, totalIVA, totalCuota;
    private CobSaldos saldos;

    @PostConstruct
    private void ini() {
        limpiar();
    }

//<editor-fold defaultstate="collapsed" desc="Metodos">
    public void limpiar() {
        tipoAmort = null;
        talonario = null;
        solicitud = null;
        desembolso = null;
        getDesembolso();
        desembolsoSelected = null;
        detalle = null;
        listaDetalle = null;
        esModificar = false;
        ayuda = null;
        acercaDe = null;
        totalCapital = new BigDecimal(BigInteger.ZERO);
        totalInteres = new BigDecimal(BigInteger.ZERO);
        totalIVA = new BigDecimal(BigInteger.ZERO);
        totalCuota = new BigDecimal(BigInteger.ZERO);
    }

    public void guardar() {
        try {
            BigDecimal nro_comprobante = new BigDecimal(BigInteger.ZERO);
            if (listaDetalle.size() > 0) {
                desembolso.setCreDesembolsoDetList(listaDetalle);
                try {
                    Query nro_aux = em.createQuery("SELECT MAX(v.nroComprobante) FROM CreDesembolsosCab v");
                    nro_comprobante = (BigDecimal) nro_aux.getSingleResult();
                } catch (Exception e) {
                    nro_comprobante = new BigDecimal(BigInteger.ZERO);
                }
                if (nro_comprobante == null) {
                    nro_comprobante = new BigDecimal(BigInteger.ZERO);
                }
                desembolso.setNroComprobante(nro_comprobante.add(new BigDecimal(BigInteger.ONE)));
                if (genericEJB.insert(desembolso)) {
                    mensajeAlerta("Se guardo Correctamente");
                    getLazyDesembolsoDT();
                } else {
                    mensajeAlerta("No se pudo Guardar");
                }
            } else {
                mensajeError("No puede Guardar un Desembolso sin Cuotas");
                return;
            }
        } catch (Exception e) {
            mensajeError("Ocurrio un error");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void edit() {
        try {
            if (listaDetalle.size() > 0) {
                try {
                    CreSolicitudes solAux = desembolso.getCreSolicitudes();
                    solAux.setDesembolsadoAux(true);
                    genericEJB.update(solAux);
                } catch (Exception e) {
                    mensajeError("Ocurrio Un Error al actualizar estado de la Solicitud");
                    e.printStackTrace(System.err);
                    logger.error("Ocurrio un error eliminar detalle Loger ", e);
                }
                desembolso.setCreDesembolsoDetList(listaDetalle);
                generarSaldos(desembolso);
                genericEJB.update(desembolso);
                //esConfirmado = true;
                mensajeAlerta("Modificado Correctamente!");
                getLazyDesembolsoDT();
            } else {
                mensajeError("No puede Actualizar un Desembolso sin Cuotas");
                return;
            }
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Modificar el Desembolso");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    private void generarSaldos(CreDesembolsosCab param) {
        if (param != null && param.getId() != null) {
            if (param.getEstado().equals("A")) {
                if (!listaDetalle.isEmpty()) {
                    try {
                        for (CreDesembolsoDet det : listaDetalle) {
                            saldos = new CobSaldos();
                            saldos.setCobClientes(desembolso.getCreSolicitudes().getCobClientes());
                            saldos.setCreDesembolsosCab(desembolso);
                            saldos.setCreDesembolsoDet(det);
                            saldos.setFecVencimiento(det.getFecVencimiento());
                            saldos.setFechaAlta(new Date());
                            saldos.setMontoCuota(det.getMontoCuota());
                            saldos.setNroCuota(BigDecimal.valueOf(det.getNroCuota()));
                            saldos.setSaldoCuota(det.getMontoCuota());
                            saldos.setVenComprobantesCabecera(null);
                            saldos.setTipoComprobante("DES");
                            saldos.setNroComprobante(desembolso.getNroComprobante());
                            saldos.setSerComprobante(talonario.getBsTimbrados().getCodEstablecimiento() + "-" + talonario.getBsTimbrados().getCodExpedicion());
                            genericEJB.insert(saldos);
                        }
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al generar el saldo del Desembolso");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error Loger ", e);
                    }
                } else {
                    mensajeError("Genere Cuota antes de aprobar el desembolso");
                    return;
                }
            }
        }
    }

    public void delete() {
        try {
            Query detalle = em.createNamedQuery("CreDesembolsoDet.findByIdCab", CreDesembolsoDet.class);
            detalle.setParameter("idCab", desembolso.getId());
            listaDetalle = detalle.getResultList();
            Query saldoDetalle = em.createNamedQuery("CobSaldos.findByDesCab", CobSaldos.class);
            saldoDetalle.setParameter("idCreCab", desembolso.getId());
            List<CobSaldos> listaSaldos = saldoDetalle.getResultList();
            if (listaSaldos.size() > 0) {
                for (CobSaldos sal : listaSaldos) {
                    try {
                        genericEJB.delete(sal);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar el Saldo del detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            if (listaDetalle.size() > 0) {
                for (CreDesembolsoDet det : listaDetalle) {
                    try {
                        genericEJB.delete(det);
                    } catch (Exception e) {
                        mensajeError("Ocurrio Un Error al Eliminar el desembolso detalle");
                        e.printStackTrace(System.err);
                        logger.error("Ocurrio un error eliminar detalle Loger ", e);
                    }
                }
            }
            try {
                CreSolicitudes solAux = desembolso.getCreSolicitudes();
                solAux.setDesembolsadoAux(false);
                solAux.setEstado("P");
                genericEJB.update(solAux);
            } catch (Exception e) {
                mensajeError("Ocurrio Un Error al actualizar estado de la Solicitud");
                e.printStackTrace(System.err);
                logger.error("Ocurrio un error eliminar detalle Loger ", e);
            }
            genericEJB.delete(desembolso);
            mensajeAlerta("Eliminado Correctamente!");
            limpiar();
            getLazyDesembolsoDT();
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al Eliminar el Desembolso");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error Loger ", e);
        }
    }

    public void onRowSelectAmortizacion(SelectEvent mp) {
        tipoAmort = (CreTipoAmortizacion) mp.getObject();
        desembolso.setCreTipoAmortizacion(tipoAmort);
    }

    public void onRowSelectSolicitud(SelectEvent m) {
        solicitud = (CreSolicitudes) m.getObject();
        desembolso.setCreSolicitudes(solicitud);
    }

    public void onRowSelectTalonario(SelectEvent t) {
        talonario = (BsTalonarios) t.getObject();
        desembolso.setBsTalonarios(talonario);
    }

    public void generarCuota() {
        try {
            if (listaDetalle == null || listaDetalle.isEmpty()) {
                if (desembolso != null && desembolso.getCreSolicitudes() != null) {
                    if (desembolso.getCreSolicitudes().getPrimerVencimiento() == null) {
                        mensajeAlerta("Debe definir el Primet Vencimiento");
                        return;
                    }
                } else {
                    mensajeAlerta("Debe seleccionar Una Solicitud");
                    return;
                }

                totalCapital = new BigDecimal(BigInteger.ZERO);
                totalInteres = new BigDecimal(BigInteger.ZERO);
                totalIVA = new BigDecimal(BigInteger.ZERO);
                totalCuota = new BigDecimal(BigInteger.ZERO);

                listaDetalle = new ArrayList<>();
                BigDecimal porcAnual = desembolso.getCreSolicitudes().getTazaAnual().divide(BigDecimal.valueOf(100));
                BigDecimal interes = desembolso.getCreSolicitudes().getMontoSolicitado().multiply(porcAnual);
                BigDecimal montoConInteres = desembolso.getCreSolicitudes().getMontoSolicitado().add(interes);
                BigDecimal montoXcuota = desembolso.getCreSolicitudes().getMontoSolicitado().divide(desembolso.getCreSolicitudes().getPlazo(), 2, BigDecimal.ROUND_UP);
                BigDecimal cuota = montoConInteres.divide(desembolso.getCreSolicitudes().getPlazo(), 2, BigDecimal.ROUND_UP);
                BigDecimal interesXcuota = interes.divide(desembolso.getCreSolicitudes().getPlazo(), 2, BigDecimal.ROUND_UP);
                BigDecimal ivaInteresXcuota = interesXcuota.divide(BigDecimal.valueOf(11), 2, BigDecimal.ROUND_UP);

                Calendar cal = Calendar.getInstance();
                cal.setTime(desembolso.getCreSolicitudes().getPrimerVencimiento());
                for (int i = 1; i <= desembolso.getCreSolicitudes().getPlazo().intValue(); i++) {
                    detalle = new CreDesembolsoDet();
                    detalle.setCreDesembolsosCab(desembolso);
                    detalle.setMontoCapital(montoXcuota);
                    detalle.setMontoCuota(cuota);
                    detalle.setMontoInteres(interesXcuota.subtract(ivaInteresXcuota));
                    detalle.setMontoIva(ivaInteresXcuota);
                    detalle.setNroCuota(i);
                    detalle.setFecVencimiento(cal.getTime());
                    cal.add(Calendar.MONTH, 1);

                    totalCapital = totalCapital.add(montoXcuota);
                    totalInteres = totalInteres.add(interesXcuota);
                    totalIVA = totalIVA.add(ivaInteresXcuota);
                    totalCuota = totalCuota.add(cuota);

                    listaDetalle.add(detalle);
                }
            } else {
                mensajeError("Primero elimine las cuotas ya generadas");
                return;
            }
        } catch (Exception e) {
            mensajeError("Ocurrio Un Error al generar cuota");
            e.printStackTrace(System.err);
            logger.error("Ocurrio un error al generar cuota Loger ", e);
        }
    }

    public void eliminarCuotas() {
        listaDetalle = null;
        getListaDetalle();
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
            ayuda.add("Esta pantalla puede ser utilizada para agregar, modificar o elminar Solicitudes del sistema.");
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
            acercaDe.add("- Pantalla: CreDesembolsoPrestamos.xhtml");
            acercaDe.add("- Controlador: CreDesembolsoPrestamosController.java");
            acercaDe.add("- EJB: genericEJB.java");
            acercaDe.add("- JPA: CreDesembolsoCab.java");
        }
        return acercaDe;
    }

    public void setAcercaDe(ArrayList<String> acercaDe) {
        this.acercaDe = acercaDe;
    }

    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    public void setTotalCapital(BigDecimal totalCapital) {
        this.totalCapital = totalCapital;
    }

    public BigDecimal getTotalInteres() {
        return totalInteres;
    }

    public void setTotalInteres(BigDecimal totalInteres) {
        this.totalInteres = totalInteres;
    }

    public BigDecimal getTotalIVA() {
        return totalIVA;
    }

    public void setTotalIVA(BigDecimal totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimal getTotalCuota() {
        return totalCuota;
    }

    public void setTotalCuota(BigDecimal totalCuota) {
        this.totalCuota = totalCuota;
    }

    public CreDesembolsoDet getDetalle() {
        if (detalle == null) {
            detalle = new CreDesembolsoDet();
            detalle.setCreDesembolsosCab(new CreDesembolsosCab());
        }
        return detalle;
    }

    public void setDetalle(CreDesembolsoDet detalle) {
        this.detalle = detalle;
    }

    public List<CreDesembolsoDet> getListaDetalle() {
        if (listaDetalle == null) {
            listaDetalle = new ArrayList<>();
        }
        for (CreDesembolsoDet it : listaDetalle) {
            if (it.getCreDesembolsosCab() == null) {
                it.setCreDesembolsosCab(new CreDesembolsosCab());
            }
        }
        return listaDetalle;
    }

    public void setListaDetalle(List<CreDesembolsoDet> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public BsTalonarios getTalonario() {
        if (talonario == null) {
            talonario = new BsTalonarios();
            talonario.setBsTipComprobantes(new BsTipComprobantes());
            talonario.setBsSucursal(new BsSucursal());
            talonario.setBsTimbrados(new BsTimbrados());
        }
        return talonario;
    }

    public void setTalonario(BsTalonarios talonario) {
        this.talonario = talonario;
    }

    public CreSolicitudes getSolicitud() {
        if (solicitud == null) {
            solicitud = new CreSolicitudes();
            solicitud.setBsEmpresas(new BsEmpresas());
            solicitud.setBsMonedas(new BsMonedas());
            solicitud.setBsSucursal(new BsSucursal());
            solicitud.setBsTalonarios(new BsTalonarios());
            solicitud.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            solicitud.setCobClientes(new CobClientes());
            solicitud.getCobClientes().setBsPersonas(new BsPersonas());
            solicitud.setVenVendedor(new VenVendedor());
            solicitud.getVenVendedor().setBsPersonas(new BsPersonas());
            solicitud.setCrMotivosPrestamos(new CrMotivosPrestamos());
        }
        return solicitud;
    }

    public void setSolicitud(CreSolicitudes solicitud) {
        this.solicitud = solicitud;
    }

    public CreTipoAmortizacion getTipoAmort() {
        if (tipoAmort == null) {
            tipoAmort = new CreTipoAmortizacion();
        }
        return tipoAmort;
    }

    public void setTipoAmort(CreTipoAmortizacion tipoAmort) {
        this.tipoAmort = tipoAmort;
    }

    public CreDesembolsosCab getDesembolso() {
        if (desembolso == null) {
            desembolso = new CreDesembolsosCab();
            desembolso.setBsTalonarios(new BsTalonarios());
            desembolso.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            desembolso.setCreTipoAmortizacion(new CreTipoAmortizacion());
            desembolso.setCreSolicitudes(new CreSolicitudes());
        }
        return desembolso;
    }

    public void setDesembolso(CreDesembolsosCab desembolso) {
        this.desembolso = desembolso;
    }

    public CreDesembolsosCab getDesembolsoSelected() {
        if (desembolsoSelected == null) {
            desembolsoSelected = new CreDesembolsosCab();
            desembolsoSelected.setBsTalonarios(new BsTalonarios());
            desembolsoSelected.getBsTalonarios().setBsTipComprobantes(new BsTipComprobantes());
            desembolsoSelected.setCreTipoAmortizacion(new CreTipoAmortizacion());
            desembolsoSelected.setCreSolicitudes(new CreSolicitudes());
        }
        return desembolsoSelected;
    }

    public void setDesembolsoSelected(CreDesembolsosCab desembolsoSelected) {
        this.desembolsoSelected = desembolsoSelected;
        if (desembolsoSelected != null) {
            desembolso = desembolsoSelected;
            Query detalle = em.createNamedQuery("CreDesembolsoDet.findByIdCab", CreDesembolsoDet.class);
            detalle.setParameter("idCab", desembolso.getId());
            listaDetalle = detalle.getResultList();
            if (listaDetalle != null) {
                for (CreDesembolsoDet det : listaDetalle) {
                    totalCapital = totalCapital.add(det.getMontoCapital());
                    totalInteres = totalInteres.add(det.getMontoInteres());
                    totalIVA = totalIVA.add(det.getMontoIva());
                    totalCuota = totalCuota.add(det.getMontoCuota());
                }
            }
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Lazys">
    public LazyDataModel getLazySolcitudDG() {
        if (lazySolcitudDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("estado", "A");
            campos.put("indDesembolsado", "N");
            lazySolcitudDG = genericLazy.getLazyDataModel(CreSolicitudes.class, campos);
        }
        return lazySolcitudDG;
    }

    public void setLazySolcitudDG(LazyDataModel lazySolcitudDG) {
        this.lazySolcitudDG = lazySolcitudDG;
    }

    public LazyDataModel getLazyTalonarioDG() {
        if (lazyTalonarioDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            campos.put("bsTipComprobantes.bsModulos.codModulo", "CRE");
            campos.put("bsTipComprobantes.codTipComp", "DES");
            lazyTalonarioDG = genericLazy.getLazyDataModel(BsTalonarios.class, campos);
        }
        return lazyTalonarioDG;
    }

    public void setLazyTalonarioDG(LazyDataModel lazyTalonarioDG) {
        this.lazyTalonarioDG = lazyTalonarioDG;
    }

    public LazyDataModel getLazyTipoAmortDG() {
        if (lazyTipoAmortDG == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyTipoAmortDG = genericLazy.getLazyDataModel(CreTipoAmortizacion.class, campos);
        }
        return lazyTipoAmortDG;
    }

    public void setLazyTipoAmortDG(LazyDataModel lazyTipoAmortDG) {
        this.lazyTipoAmortDG = lazyTipoAmortDG;
    }

    public LazyDataModel getLazyDesembolsoDT() {
        if (lazyDesembolsoDT == null) {
            genericLazy = new GenericBigLazyList(em);
            campos = new HashMap();
            lazyDesembolsoDT = genericLazy.getLazyDataModel(CreDesembolsosCab.class, campos);
        }
        return lazyDesembolsoDT;
    }

    public void setLazyDesembolsoDT(LazyDataModel lazyDesembolsoDT) {
        this.lazyDesembolsoDT = lazyDesembolsoDT;
    }

//</editor-fold>
}
