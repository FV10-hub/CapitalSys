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
@Table(name = "ven_comprobantes_detalle")
@NamedQueries({
    @NamedQuery(name = "VenComprobantesDetalle.findAll", query = "SELECT v FROM VenComprobantesDetalle v")
    , @NamedQuery(name = "VenComprobantesDetalle.findById", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.id = :id")
    , @NamedQuery(name = "VenComprobantesDetalle.findByCab", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.venComprobantesCabecera.id = :idCab")
    , @NamedQuery(name = "VenComprobantesDetalle.findByCantidad", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.cantidad = :cantidad")
    , @NamedQuery(name = "VenComprobantesDetalle.findByPrecioUnitario", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.precioUnitario = :precioUnitario")
    , @NamedQuery(name = "VenComprobantesDetalle.findByMontoExenta", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.montoExenta = :montoExenta")
    , @NamedQuery(name = "VenComprobantesDetalle.findByGravada", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.gravada = :gravada")
    , @NamedQuery(name = "VenComprobantesDetalle.findByMontoIva", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.montoIva = :montoIva")
    , @NamedQuery(name = "VenComprobantesDetalle.findByTotalLinea", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.totalLinea = :totalLinea")
    , @NamedQuery(name = "VenComprobantesDetalle.findByGravada10", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.gravada10 = :gravada10")
    , @NamedQuery(name = "VenComprobantesDetalle.findByGravada5", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.gravada5 = :gravada5")
    , @NamedQuery(name = "VenComprobantesDetalle.findByIva10", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.iva10 = :iva10")
    , @NamedQuery(name = "VenComprobantesDetalle.findByIva5", query = "SELECT v FROM VenComprobantesDetalle v WHERE v.iva5 = :iva5")})
public class VenComprobantesDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "FACTURADET_SEQ_GENERATOR", sequenceName = "ven_comprobantes_detalle_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FACTURADET_SEQ_GENERATOR")
    @Column(name = "ID", nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @Column(name = "monto_exenta")
    private BigDecimal montoExenta;
    @Column(name = "gravada")
    private BigDecimal gravada;
    @Column(name = "monto_iva")
    private BigDecimal montoIva;
    @Column(name = "total_linea")
    private BigDecimal totalLinea;
    @Column(name = "gravada_10")
    private BigDecimal gravada10;
    @Column(name = "gravada_5")
    private BigDecimal gravada5;
    @Column(name = "iva_10")
    private BigDecimal iva10;
    @Column(name = "iva_5")
    private BigDecimal iva5;

    @JoinColumn(name = "bs_iva_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsIva bsIva;

    @JoinColumn(name = "sto_articulos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StoArticulos stoArticulos;

    @JoinColumn(name = "ven_comprobantes_cabecera_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private VenComprobantesCabecera venComprobantesCabecera;

    public VenComprobantesDetalle() {
    }

    public VenComprobantesDetalle(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getMontoExenta() {
        return montoExenta;
    }

    public void setMontoExenta(BigDecimal montoExenta) {
        this.montoExenta = montoExenta;
    }

    public BigDecimal getGravada() {
        return gravada;
    }

    public void setGravada(BigDecimal gravada) {
        this.gravada = gravada;
    }

    public BigDecimal getMontoIva() {
        return montoIva;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public BigDecimal getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(BigDecimal totalLinea) {
        this.totalLinea = totalLinea;
    }

    public BigDecimal getGravada10() {
        return gravada10;
    }

    public void setGravada10(BigDecimal gravada10) {
        this.gravada10 = gravada10;
    }

    public BigDecimal getGravada5() {
        return gravada5;
    }

    public void setGravada5(BigDecimal gravada5) {
        this.gravada5 = gravada5;
    }

    public BigDecimal getIva10() {
        return iva10;
    }

    public void setIva10(BigDecimal iva10) {
        this.iva10 = iva10;
    }

    public BigDecimal getIva5() {
        return iva5;
    }

    public void setIva5(BigDecimal iva5) {
        this.iva5 = iva5;
    }

    public BsIva getBsIva() {
        return bsIva;
    }

    public void setBsIva(BsIva bsIva) {
        this.bsIva = bsIva;
    }

    public StoArticulos getStoArticulos() {
        return stoArticulos;
    }

    public void setStoArticulos(StoArticulos stoArticulos) {
        this.stoArticulos = stoArticulos;
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
        if (!(object instanceof VenComprobantesDetalle)) {
            return false;
        }
        VenComprobantesDetalle other = (VenComprobantesDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.VenComprobantesDetalle[ id=" + id + " ]";
    }

}
