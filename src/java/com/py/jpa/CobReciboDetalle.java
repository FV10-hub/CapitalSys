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
@Table(name = "cob_recibo_detalle")
@NamedQueries({
    @NamedQuery(name = "CobReciboDetalle.findAll", query = "SELECT c FROM CobReciboDetalle c")
    , @NamedQuery(name = "CobReciboDetalle.findByCab", query = "SELECT c FROM CobReciboDetalle c WHERE c.cobRecibosCab.id = :idCab")
    , @NamedQuery(name = "CobReciboDetalle.findById", query = "SELECT c FROM CobReciboDetalle c WHERE c.id = :id")
    , @NamedQuery(name = "CobReciboDetalle.findByMontoCuota", query = "SELECT c FROM CobReciboDetalle c WHERE c.montoCuota = :montoCuota")
    , @NamedQuery(name = "CobReciboDetalle.findByMontoCobro", query = "SELECT c FROM CobReciboDetalle c WHERE c.montoCobro = :montoCobro")
    , @NamedQuery(name = "CobReciboDetalle.findByTipCambio", query = "SELECT c FROM CobReciboDetalle c WHERE c.tipCambio = :tipCambio")})
public class CobReciboDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "RECIBOSDET_SEQ_GENERATOR", sequenceName = "cob_recibo_detalle_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECIBOSDET_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_cuota")
    private BigDecimal montoCuota;
    @Column(name = "monto_cobro")
    private BigDecimal montoCobro;
    @Column(name = "tip_cambio")
    private BigDecimal tipCambio;
    @Column(name = "nro_cuota")
    private BigDecimal nroCuota;

    @JoinColumn(name = "cob_recibos_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobRecibosCab cobRecibosCab;

    @JoinColumn(name = "cob_saldos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobSaldos cobSaldos;

    public CobReciboDetalle() {
    }

    public CobReciboDetalle(Long id) {
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

    public BigDecimal getTipCambio() {
        return tipCambio;
    }

    public void setTipCambio(BigDecimal tipCambio) {
        this.tipCambio = tipCambio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CobRecibosCab getCobRecibosCab() {
        return cobRecibosCab;
    }

    public void setCobRecibosCab(CobRecibosCab cobRecibosCab) {
        this.cobRecibosCab = cobRecibosCab;
    }

    public CobSaldos getCobSaldos() {
        return cobSaldos;
    }

    public void setCobSaldos(CobSaldos cobSaldos) {
        this.cobSaldos = cobSaldos;
    }

    public BigDecimal getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(BigDecimal nroCuota) {
        this.nroCuota = nroCuota;
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
        if (!(object instanceof CobReciboDetalle)) {
            return false;
        }
        CobReciboDetalle other = (CobReciboDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobReciboDetalle[ id=" + id + " ]";
    }

}
