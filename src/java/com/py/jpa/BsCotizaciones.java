/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_cotizaciones")
@NamedQueries({
    @NamedQuery(name = "BsCotizaciones.findAll", query = "SELECT b FROM BsCotizaciones b")
    , @NamedQuery(name = "BsCotizaciones.findById", query = "SELECT b FROM BsCotizaciones b WHERE b.id = :id")
    , @NamedQuery(name = "BsCotizaciones.findByFecha", query = "SELECT b FROM BsCotizaciones b WHERE b.fecha = :fecha")
    , @NamedQuery(name = "BsCotizaciones.findByTipo", query = "SELECT b FROM BsCotizaciones b WHERE b.tipo = :tipo")
    , @NamedQuery(name = "BsCotizaciones.findByCambioCompra", query = "SELECT b FROM BsCotizaciones b WHERE b.cambioCompra = :cambioCompra")
    , @NamedQuery(name = "BsCotizaciones.findByCambioVenta", query = "SELECT b FROM BsCotizaciones b WHERE b.cambioVenta = :cambioVenta")})
public class BsCotizaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 5)
    @Column(name = "tipo")
    private String tipo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cambio_compra")
    private BigDecimal cambioCompra;
    @Column(name = "cambio_venta")
    private BigDecimal cambioVenta;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;

    public BsCotizaciones() {
    }

    public BsCotizaciones(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getCambioCompra() {
        return cambioCompra;
    }

    public void setCambioCompra(BigDecimal cambioCompra) {
        this.cambioCompra = cambioCompra;
    }

    public BigDecimal getCambioVenta() {
        return cambioVenta;
    }

    public void setCambioVenta(BigDecimal cambioVenta) {
        this.cambioVenta = cambioVenta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsCotizaciones)) {
            return false;
        }
        BsCotizaciones other = (BsCotizaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsCotizaciones[ id=" + id + " ]";
    }
    
}
