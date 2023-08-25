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
@Table(name = "cre_desembolso_det")
@NamedQueries({
    @NamedQuery(name = "CreDesembolsoDet.findAll", query = "SELECT c FROM CreDesembolsoDet c")
    , @NamedQuery(name = "CreDesembolsoDet.findById", query = "SELECT c FROM CreDesembolsoDet c WHERE c.id = :id")
    , @NamedQuery(name = "CreDesembolsoDet.findByIdCab", query = "SELECT c FROM CreDesembolsoDet c WHERE c.creDesembolsosCab.id = :idCab")
    , @NamedQuery(name = "CreDesembolsoDet.findByNroCuota", query = "SELECT c FROM CreDesembolsoDet c WHERE c.nroCuota = :nroCuota")
    , @NamedQuery(name = "CreDesembolsoDet.findByFecVencimiento", query = "SELECT c FROM CreDesembolsoDet c WHERE c.fecVencimiento = :fecVencimiento")
    , @NamedQuery(name = "CreDesembolsoDet.findByMontoCapital", query = "SELECT c FROM CreDesembolsoDet c WHERE c.montoCapital = :montoCapital")
    , @NamedQuery(name = "CreDesembolsoDet.findByMontoIva", query = "SELECT c FROM CreDesembolsoDet c WHERE c.montoIva = :montoIva")
    , @NamedQuery(name = "CreDesembolsoDet.findByMontoInteres", query = "SELECT c FROM CreDesembolsoDet c WHERE c.montoInteres = :montoInteres")
    , @NamedQuery(name = "CreDesembolsoDet.findByMontoCuota", query = "SELECT c FROM CreDesembolsoDet c WHERE c.montoCuota = :montoCuota")})
public class CreDesembolsoDet implements Serializable {

    private static final long serialVersionUID = 1L;
   @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "DESEMBOLSODET_SEQ_GENERATOR", sequenceName = "cre_desembolso_det_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESEMBOLSODET_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nro_cuota")
    private Integer nroCuota;
    @Column(name = "fec_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fecVencimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_capital")
    private BigDecimal montoCapital;
    @Column(name = "monto_iva")
    private BigDecimal montoIva;
    @Column(name = "monto_interes")
    private BigDecimal montoInteres;
    @Column(name = "monto_cuota")
    private BigDecimal montoCuota;
    @JoinColumn(name = "cre_desembolsos_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CreDesembolsosCab creDesembolsosCab;

    public CreDesembolsoDet() {
    }

    public CreDesembolsoDet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroCuota() {
        return nroCuota;
    }

    public void setNroCuota(Integer nroCuota) {
        this.nroCuota = nroCuota;
    }

    public Date getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(Date fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public BigDecimal getMontoCapital() {
        return montoCapital;
    }

    public void setMontoCapital(BigDecimal montoCapital) {
        this.montoCapital = montoCapital;
    }

    public BigDecimal getMontoIva() {
        return montoIva;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public BigDecimal getMontoInteres() {
        return montoInteres;
    }

    public void setMontoInteres(BigDecimal montoInteres) {
        this.montoInteres = montoInteres;
    }

    public BigDecimal getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(BigDecimal montoCuota) {
        this.montoCuota = montoCuota;
    }

    public CreDesembolsosCab getCreDesembolsosCab() {
        return creDesembolsosCab;
    }

    public void setCreDesembolsosCab(CreDesembolsosCab creDesembolsosCab) {
        this.creDesembolsosCab = creDesembolsosCab;
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
        if (!(object instanceof CreDesembolsoDet)) {
            return false;
        }
        CreDesembolsoDet other = (CreDesembolsoDet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CreDesembolsoDet[ id=" + id + " ]";
    }

}
