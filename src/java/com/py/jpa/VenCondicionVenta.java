/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "ven_condicion_venta")
@NamedQueries({
    @NamedQuery(name = "VenCondicionVenta.findAll", query = "SELECT v FROM VenCondicionVenta v")
    , @NamedQuery(name = "VenCondicionVenta.findById", query = "SELECT v FROM VenCondicionVenta v WHERE v.id = :id")
    , @NamedQuery(name = "VenCondicionVenta.findByCodCondicion", query = "SELECT v FROM VenCondicionVenta v WHERE v.codCondicion = :codCondicion")
    , @NamedQuery(name = "VenCondicionVenta.findByDescripcion", query = "SELECT v FROM VenCondicionVenta v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "VenCondicionVenta.findByPlazo", query = "SELECT v FROM VenCondicionVenta v WHERE v.plazo = :plazo")
    , @NamedQuery(name = "VenCondicionVenta.findByIntervalo", query = "SELECT v FROM VenCondicionVenta v WHERE v.intervalo = :intervalo")})
public class VenCondicionVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id",nullable = false)
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_condicion")
    private String codCondicion;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "plazo")
    private BigDecimal plazo;
    @Column(name = "intervalo")
    private BigDecimal intervalo;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id",nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    public VenCondicionVenta() {
    }

    public VenCondicionVenta(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCondicion() {
        return codCondicion;
    }

    public void setCodCondicion(String codCondicion) {
        this.codCondicion = codCondicion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    public BigDecimal getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(BigDecimal intervalo) {
        this.intervalo = intervalo;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
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
        if (!(object instanceof VenCondicionVenta)) {
            return false;
        }
        VenCondicionVenta other = (VenCondicionVenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.VenCondicionVenta[ id=" + id + " ]";
    }

}
