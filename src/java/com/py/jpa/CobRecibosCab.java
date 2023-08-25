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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_recibos_cab")
@NamedQueries({
    @NamedQuery(name = "CobRecibosCab.findAll", query = "SELECT c FROM CobRecibosCab c")
    , @NamedQuery(name = "CobRecibosCab.findById", query = "SELECT c FROM CobRecibosCab c WHERE c.id = :id")
    , @NamedQuery(name = "CobRecibosCab.findByFecAlta", query = "SELECT c FROM CobRecibosCab c WHERE c.fecAlta = :fecAlta")
    , @NamedQuery(name = "CobRecibosCab.findByUsuarioAlta", query = "SELECT c FROM CobRecibosCab c WHERE c.usuarioAlta = :usuarioAlta")
    , @NamedQuery(name = "CobRecibosCab.findByTipoCambio", query = "SELECT c FROM CobRecibosCab c WHERE c.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "CobRecibosCab.findByObservacion", query = "SELECT c FROM CobRecibosCab c WHERE c.observacion = :observacion")})
public class CobRecibosCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "RECIBOSCAB_SEQ_GENERATOR", sequenceName = "cob_cobros_cab_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIBOSCAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usuario_alta")
    private Integer usuarioAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "nro_recibo")
    private BigDecimal nroRecibo;

    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;

    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;

    @JoinColumn(name = "bs_talonarios_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTalonarios bsTalonarios;

    @JoinColumn(name = "cob_clientes_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;

    @JoinColumn(name = "cob_cobrador_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrador cobCobrador;

    @JoinColumn(name = "cob_cobros_cab_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrosCab cobCobrosCab;

    @JoinColumn(name = "cob_habilitaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobHabilitaciones cobHabilitaciones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobRecibosCab", fetch = FetchType.LAZY)
    private List<CobReciboDetalle> cobReciboDetalleList;

    public CobRecibosCab() {
    }

    public CobRecibosCab(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CobCobrador getCobCobrador() {
        return cobCobrador;
    }

    public void setCobCobrador(CobCobrador cobCobrador) {
        this.cobCobrador = cobCobrador;
    }

    public CobCobrosCab getCobCobrosCab() {
        return cobCobrosCab;
    }

    public void setCobCobrosCab(CobCobrosCab cobCobrosCab) {
        this.cobCobrosCab = cobCobrosCab;
    }

    public CobHabilitaciones getCobHabilitaciones() {
        return cobHabilitaciones;
    }

    public void setCobHabilitaciones(CobHabilitaciones cobHabilitaciones) {
        this.cobHabilitaciones = cobHabilitaciones;
    }

    public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public Integer getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Integer usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<CobReciboDetalle> getCobReciboDetalleList() {
        return cobReciboDetalleList;
    }

    public void setCobReciboDetalleList(List<CobReciboDetalle> cobReciboDetalleList) {
        this.cobReciboDetalleList = cobReciboDetalleList;
    }

    public BigDecimal getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(BigDecimal nroRecibo) {
        this.nroRecibo = nroRecibo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof CobRecibosCab)) {
            return false;
        }
        CobRecibosCab other = (CobRecibosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobRecibosCab[ id=" + id + " ]";
    }

}
