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
import javax.persistence.Table;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "tes_pago_comp")
@NamedQueries({
    @NamedQuery(name = "TesPagoComp.findAll", query = "SELECT t FROM TesPagoComp t")
    , @NamedQuery(name = "TesPagoComp.findById", query = "SELECT t FROM TesPagoComp t WHERE t.id = :id")
    , @NamedQuery(name = "TesPagoComp.findByMontoTotal", query = "SELECT t FROM TesPagoComp t WHERE t.montoTotal = :montoTotal")})
public class TesPagoComp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    @JoinColumn(name = "tes_pago_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesPagoCab tesPagoCabId;

    public TesPagoComp() {
    }

    public TesPagoComp(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public TesPagoCab getTesPagoCabId() {
        return tesPagoCabId;
    }

    public void setTesPagoCabId(TesPagoCab tesPagoCabId) {
        this.tesPagoCabId = tesPagoCabId;
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
        if (!(object instanceof TesPagoComp)) {
            return false;
        }
        TesPagoComp other = (TesPagoComp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesPagoComp[ id=" + id + " ]";
    }
    
}
