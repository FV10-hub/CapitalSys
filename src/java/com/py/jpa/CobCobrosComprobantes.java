/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_cobros_comprobantes")
@NamedQueries({
    @NamedQuery(name = "CobCobrosComprobantes.findAll", query = "SELECT c FROM CobCobrosComprobantes c")
    , @NamedQuery(name = "CobCobrosComprobantes.findById", query = "SELECT c FROM CobCobrosComprobantes c WHERE c.id = :id")
    , @NamedQuery(name = "CobCobrosComprobantes.findByCabid", query = "SELECT c FROM CobCobrosComprobantes c WHERE c.cobCobrosCab.id = :idCab")
    , @NamedQuery(name = "CobCobrosComprobantes.findByMontoCuota", query = "SELECT c FROM CobCobrosComprobantes c WHERE c.montoCuota = :montoCuota")
    , @NamedQuery(name = "CobCobrosComprobantes.findByMontoCobro", query = "SELECT c FROM CobCobrosComprobantes c WHERE c.montoCobro = :montoCobro")})
public class CobCobrosComprobantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "COBCOMPCAB_SEQ_GENERATOR", sequenceName = "cob_cobros_comprobantes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COBCOMPCAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_cuota")
    private BigDecimal montoCuota;
    @Column(name = "monto_cobro")
    private BigDecimal montoCobro;
    @JoinColumn(name = "cob_cobros_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrosCab cobCobrosCab;
    @JoinColumn(name = "cob_recibos_cab_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobRecibosCab cobRecibosCab;
    @JoinColumn(name = "ven_comprobantes_cabecera_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VenComprobantesCabecera venComprobantesCabecera;

    public CobCobrosComprobantes() {
    }

    public CobCobrosComprobantes(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public BigDecimal getMontoCobro() {
        return montoCobro;
    }

    public void setMontoCobro(BigDecimal montoCobro) {
        this.montoCobro = montoCobro;
    }

    public CobCobrosCab getCobCobrosCab() {
        return cobCobrosCab;
    }

    public void setCobCobrosCab(CobCobrosCab cobCobrosCab) {
        this.cobCobrosCab = cobCobrosCab;
    }

    public CobRecibosCab getCobRecibosCab() {
        return cobRecibosCab;
    }

    public void setCobRecibosCab(CobRecibosCab cobRecibosCab) {
        this.cobRecibosCab = cobRecibosCab;
    }

    public VenComprobantesCabecera getVenComprobantesCabecera() {
        return venComprobantesCabecera;
    }

    public void setVenComprobantesCabecera(VenComprobantesCabecera venComprobantesCabecera) {
        this.venComprobantesCabecera = venComprobantesCabecera;
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
        if (!(object instanceof CobCobrosComprobantes)) {
            return false;
        }
        CobCobrosComprobantes other = (CobCobrosComprobantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobCobrosComprobantes[ id=" + id + " ]";
    }

}
