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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "sto_articulos")
@NamedQueries({
    @NamedQuery(name = "StoArticulos.findAll", query = "SELECT s FROM StoArticulos s")
    , @NamedQuery(name = "StoArticulos.findById", query = "SELECT s FROM StoArticulos s WHERE s.id = :id")
    , @NamedQuery(name = "StoArticulos.findByCodArticulo", query = "SELECT s FROM StoArticulos s WHERE s.codArticulo = :codArticulo")
    , @NamedQuery(name = "StoArticulos.findByDescripcion", query = "SELECT s FROM StoArticulos s WHERE s.descripcion = :descripcion")
    , @NamedQuery(name = "StoArticulos.findByPrecioUni", query = "SELECT s FROM StoArticulos s WHERE s.precioUni = :precioUni")
    , @NamedQuery(name = "StoArticulos.findByIndInventario", query = "SELECT s FROM StoArticulos s WHERE s.indInventario = :indInventario")})
public class StoArticulos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_articulo")
    private String codArticulo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_uni")
    private BigDecimal precioUni;
    @Size(max = 1)
    @Column(name = "ind_inventario")
    private String indInventario;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    
    @JoinColumn(name = "id_bs_iva", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsIva bsIva;

    @Transient
    private boolean inventarioAux;

    public StoArticulos() {
    }

    public StoArticulos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isInventarioAux() {
        if (indInventario != null) {
            inventarioAux = indInventario.equals("S");
        }
        return inventarioAux;
    }

    public void setInventarioAux(boolean inventarioAux) {
        indInventario = inventarioAux ? "S" : "N";
        this.inventarioAux = inventarioAux;

    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecioUni() {
        return precioUni;
    }

    public void setPrecioUni(BigDecimal precioUni) {
        this.precioUni = precioUni;
    }

    public String getIndInventario() {
        return indInventario;
    }

    public void setIndInventario(String indInventario) {
        this.indInventario = indInventario;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public BsIva getBsIva() {
        return bsIva;
    }

    public void setBsIva(BsIva bsIva) {
        this.bsIva = bsIva;
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
        if (!(object instanceof StoArticulos)) {
            return false;
        }
        StoArticulos other = (StoArticulos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.StoArticulos[ id=" + id + " ]";
    }

}
