/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cre_solicitudes")
@NamedQueries({
    @NamedQuery(name = "CreSolicitudes.findAll", query = "SELECT c FROM CreSolicitudes c")
    , @NamedQuery(name = "CreSolicitudes.findById", query = "SELECT c FROM CreSolicitudes c WHERE c.id = :id")
    , @NamedQuery(name = "CreSolicitudes.findByFecAlta", query = "SELECT c FROM CreSolicitudes c WHERE c.fecAlta = :fecAlta")
    , @NamedQuery(name = "CreSolicitudes.findByUsuAlta", query = "SELECT c FROM CreSolicitudes c WHERE c.usuAlta = :usuAlta")
    , @NamedQuery(name = "CreSolicitudes.findByFechaSolicitud", query = "SELECT c FROM CreSolicitudes c WHERE c.fechaSolicitud = :fechaSolicitud")
    , @NamedQuery(name = "CreSolicitudes.findByPrimerVencimiento", query = "SELECT c FROM CreSolicitudes c WHERE c.primerVencimiento = :primerVencimiento")
    , @NamedQuery(name = "CreSolicitudes.findByEstado", query = "SELECT c FROM CreSolicitudes c WHERE c.estado = :estado")
    , @NamedQuery(name = "CreSolicitudes.findByTazaAnual", query = "SELECT c FROM CreSolicitudes c WHERE c.tazaAnual = :tazaAnual")
    , @NamedQuery(name = "CreSolicitudes.findByPlazo", query = "SELECT c FROM CreSolicitudes c WHERE c.plazo = :plazo")
    , @NamedQuery(name = "CreSolicitudes.findByMontoSolicitado", query = "SELECT c FROM CreSolicitudes c WHERE c.montoSolicitado = :montoSolicitado")
    , @NamedQuery(name = "CreSolicitudes.findByMontoAprobado", query = "SELECT c FROM CreSolicitudes c WHERE c.montoAprobado = :montoAprobado")
    , @NamedQuery(name = "CreSolicitudes.findByTipoCambio", query = "SELECT c FROM CreSolicitudes c WHERE c.tipoCambio = :tipoCambio")})
public class CreSolicitudes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usu_alta")
    private Integer usuAlta;
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;
    @Column(name = "primer_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date primerVencimiento;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "taza_anual")
    private BigDecimal tazaAnual;
    @Column(name = "plazo")
    private BigDecimal plazo;
    @Column(name = "monto_solicitado")
    private BigDecimal montoSolicitado;
    @Column(name = "monto_aprobado")
    private BigDecimal montoAprobado;
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "ind_desembolsado")
    private String indDesembolsado;
    
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;
    
    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;
    
    @JoinColumn(name = "bs_talonarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTalonarios bsTalonarios;
    
    @JoinColumn(name = "cob_clientes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;
    
    @JoinColumn(name = "cr_motivos_prestamos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CrMotivosPrestamos crMotivosPrestamos;
    
    @JoinColumn(name = "ven_vendedor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VenVendedor venVendedor;

    @Transient
    private boolean estadoAux;
    @Transient
    private boolean desembolsadoAux;

    public CreSolicitudes() {
    }

    public CreSolicitudes(Long id) {
        this.id = id;
    }

     public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public Integer getUsuAlta() {
        return usuAlta;
    }

    public void setUsuAlta(Integer usuAlta) {
        this.usuAlta = usuAlta;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getPrimerVencimiento() {
        return primerVencimiento;
    }

    public void setPrimerVencimiento(Date primerVencimiento) {
        this.primerVencimiento = primerVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEstadoAux() {
        if (estado != null) {
            estadoAux = estado.equals("A");
        }
        return estadoAux;
    }

    public void setEstadoAux(boolean estadoAux) {
        estado = estadoAux ? "A" : "P";
        this.estadoAux = estadoAux;
    }

    public String getIndDesembolsado() {
        return indDesembolsado;
    }

    public void setIndDesembolsado(String indDesembolsado) {
        this.indDesembolsado = indDesembolsado;
    }

    public boolean isDesembolsadoAux() {
        if (indDesembolsado != null) {
            desembolsadoAux = indDesembolsado.equals("S");
        }
        return desembolsadoAux;
    }

    public void setDesembolsadoAux(boolean desembolsadoAux) {
        indDesembolsado = desembolsadoAux ? "S" : "N";
        this.desembolsadoAux = desembolsadoAux;
    }
    
    
    
    public BigDecimal getTazaAnual() {
        return tazaAnual;
    }

    public void setTazaAnual(BigDecimal tazaAnual) {
        this.tazaAnual = tazaAnual;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public BigDecimal getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(BigDecimal montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public BsMonedas getBsMonedas() {
        return bsMonedas;
    }

    public void setBsMonedas(BsMonedas bsMonedas) {
        this.bsMonedas = bsMonedas;
    }

    public BsSucursal getBsSucursal() {
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public BsTalonarios getBsTalonarios() {
        return bsTalonarios;
    }

    public void setBsTalonarios(BsTalonarios bsTalonarios) {
        this.bsTalonarios = bsTalonarios;
    }

    public CobClientes getCobClientes() {
        return cobClientes;
    }

    public void setCobClientes(CobClientes cobClientes) {
        this.cobClientes = cobClientes;
    }

    public CrMotivosPrestamos getCrMotivosPrestamos() {
        return crMotivosPrestamos;
    }

    public void setCrMotivosPrestamos(CrMotivosPrestamos crMotivosPrestamos) {
        this.crMotivosPrestamos = crMotivosPrestamos;
    }

    public VenVendedor getVenVendedor() {
        return venVendedor;
    }

    public void setVenVendedor(VenVendedor venVendedor) {
        this.venVendedor = venVendedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CreSolicitudes)) {
            return false;
        }
        CreSolicitudes other = (CreSolicitudes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CreSolicitudes[ id=" + id + " ]";
    }
    
}
