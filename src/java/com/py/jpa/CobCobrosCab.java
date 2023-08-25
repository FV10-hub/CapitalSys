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

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_cobros_cab")
@NamedQueries({
    @NamedQuery(name = "CobCobrosCab.findAll", query = "SELECT c FROM CobCobrosCab c")
    , @NamedQuery(name = "CobCobrosCab.findById", query = "SELECT c FROM CobCobrosCab c WHERE c.id = :id")
    , @NamedQuery(name = "CobCobrosCab.findByFechaAlta", query = "SELECT c FROM CobCobrosCab c WHERE c.fechaAlta = :fechaAlta")
    , @NamedQuery(name = "CobCobrosCab.findByUsuarioAlta", query = "SELECT c FROM CobCobrosCab c WHERE c.usuarioAlta = :usuarioAlta")
    , @NamedQuery(name = "CobCobrosCab.findByTipoCambio", query = "SELECT c FROM CobCobrosCab c WHERE c.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "CobCobrosCab.findByFechaCobro", query = "SELECT c FROM CobCobrosCab c WHERE c.fechaCobro = :fechaCobro")})
public class CobCobrosCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "COBROSCAB_SEQ_GENERATOR", sequenceName = "cob_cobros_cab_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COBROSCAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "usuario_alta")
    private Integer usuarioAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "fecha_cobro")
    @Temporal(TemporalType.DATE)
    private Date fechaCobro;
    @Column(name = "nro_cobro")
    private Long nroCobro;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobCobrosCab", fetch = FetchType.LAZY)
    private List<CobCobrosComprobantes> cobCobrosComprobantesList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cobCobrosCab", fetch = FetchType.LAZY)
    private List<CobCobrosValores> cobCobrosValoresList;
    
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;
    
    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;
    
    @JoinColumn(name = "cob_clientes_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;
    
    @JoinColumn(name = "cob_habilitaciones_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobHabilitaciones cobHabilitaciones;
    
    public CobCobrosCab() {
    }

    public CobCobrosCab(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
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

    public Date getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Date fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public List<CobCobrosComprobantes> getCobCobrosComprobantesList() {
        return cobCobrosComprobantesList;
    }

    public void setCobCobrosComprobantesList(List<CobCobrosComprobantes> cobCobrosComprobantesList) {
        this.cobCobrosComprobantesList = cobCobrosComprobantesList;
    }

    public List<CobCobrosValores> getCobCobrosValoresList() {
        return cobCobrosValoresList;
    }

    public void setCobCobrosValoresList(List<CobCobrosValores> cobCobrosValoresList) {
        this.cobCobrosValoresList = cobCobrosValoresList;
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

    public CobClientes getCobClientes() {
        return cobClientes;
    }

    public void setCobClientes(CobClientes cobClientes) {
        this.cobClientes = cobClientes;
    }

    public CobHabilitaciones getCobHabilitaciones() {
        return cobHabilitaciones;
    }

    public void setCobHabilitaciones(CobHabilitaciones cobHabilitaciones) {
        this.cobHabilitaciones = cobHabilitaciones;
    }


    public Long getNroCobro() {
        return nroCobro;
    }

    public void setNroCobro(Long nroCobro) {
        this.nroCobro = nroCobro;
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
        if (!(object instanceof CobCobrosCab)) {
            return false;
        }
        CobCobrosCab other = (CobCobrosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobCobrosCab[ id=" + id + " ]";
    }
    
}
