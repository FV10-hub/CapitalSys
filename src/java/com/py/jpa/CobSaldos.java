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

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_saldos")
@NamedQueries({
    @NamedQuery(name = "CobSaldos.findAll", query = "SELECT c FROM CobSaldos c")
    , @NamedQuery(name = "CobSaldos.findById", query = "SELECT c FROM CobSaldos c WHERE c.id = :id")
    , @NamedQuery(name = "CobSaldos.findByDesCab", query = "SELECT c FROM CobSaldos c WHERE c.creDesembolsosCab.id = :idCreCab")
    , @NamedQuery(name = "CobSaldos.findByUsuarioAud", query = "SELECT c FROM CobSaldos c WHERE c.usuarioAud = :usuarioAud")
    , @NamedQuery(name = "CobSaldos.findByFechaAlta", query = "SELECT c FROM CobSaldos c WHERE c.fechaAlta = :fechaAlta")
    , @NamedQuery(name = "CobSaldos.findByMontoCuota", query = "SELECT c FROM CobSaldos c WHERE c.montoCuota = :montoCuota")
    , @NamedQuery(name = "CobSaldos.findByNroCuota", query = "SELECT c FROM CobSaldos c WHERE c.nroCuota = :nroCuota")
    , @NamedQuery(name = "CobSaldos.findByFecVencimiento", query = "SELECT c FROM CobSaldos c WHERE c.fecVencimiento = :fecVencimiento")
    , @NamedQuery(name = "CobSaldos.findBySaldoCuota", query = "SELECT c FROM CobSaldos c WHERE c.saldoCuota = :saldoCuota")})
public class CobSaldos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "usuario_aud")
    private Integer usuarioAud;
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_cuota")
    private BigDecimal montoCuota;
    @Column(name = "nro_cuota")
    private BigDecimal nroCuota;
    @Column(name = "fec_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fecVencimiento;
    @Column(name = "saldo_cuota")
    private BigDecimal saldoCuota;
    @Column(name = "nro_comprobante")
    private BigDecimal nroComprobante;
    @Column(name = "ser_comprobante")
    private String serComprobante;
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;

    @JoinColumn(name = "cob_clientes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;

    @JoinColumn(name = "cre_desembolso_det_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreDesembolsoDet creDesembolsoDet;

    @JoinColumn(name = "cre_desembolsos_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreDesembolsosCab creDesembolsosCab;

    @JoinColumn(name = "ven_comprobantes_cabecera_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VenComprobantesCabecera venComprobantesCabecera;

    public CobSaldos() {
    }

    public CobSaldos(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioAud() {
        return usuarioAud;
    }

    public void setUsuarioAud(Integer usuarioAud) {
        this.usuarioAud = usuarioAud;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public BigDecimal getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(BigDecimal nroCuota) {
        this.nroCuota = nroCuota;
    }

    public Date getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(Date fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public BigDecimal getSaldoCuota() {
        return saldoCuota;
    }

    public void setSaldoCuota(BigDecimal saldoCuota) {
        this.saldoCuota = saldoCuota;
    }

    public CobClientes getCobClientes() {
        return cobClientes;
    }

    public void setCobClientes(CobClientes cobClientes) {
        this.cobClientes = cobClientes;
    }

    public CreDesembolsoDet getCreDesembolsoDet() {
        return creDesembolsoDet;
    }

    public void setCreDesembolsoDet(CreDesembolsoDet creDesembolsoDet) {
        this.creDesembolsoDet = creDesembolsoDet;
    }

    public CreDesembolsosCab getCreDesembolsosCab() {
        return creDesembolsosCab;
    }

    public void setCreDesembolsosCab(CreDesembolsosCab creDesembolsosCab) {
        this.creDesembolsosCab = creDesembolsosCab;
    }

    public VenComprobantesCabecera getVenComprobantesCabecera() {
        return venComprobantesCabecera;
    }

    public void setVenComprobantesCabecera(VenComprobantesCabecera venComprobantesCabecera) {
        this.venComprobantesCabecera = venComprobantesCabecera;
    }

    public BigDecimal getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(BigDecimal nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getSerComprobante() {
        return serComprobante;
    }

    public void setSerComprobante(String serComprobante) {
        this.serComprobante = serComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
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
        if (!(object instanceof CobSaldos)) {
            return false;
        }
        CobSaldos other = (CobSaldos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobSaldos[ id=" + id + " ]";
    }

}
