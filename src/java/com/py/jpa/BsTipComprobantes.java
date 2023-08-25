/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
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
@Table(name = "bs_tip_comprobantes")
@NamedQueries({
    @NamedQuery(name = "BsTipComprobantes.findAll", query = "SELECT b FROM BsTipComprobantes b")
    , @NamedQuery(name = "BsTipComprobantes.findById", query = "SELECT b FROM BsTipComprobantes b WHERE b.id = :id")
    , @NamedQuery(name = "BsTipComprobantes.findByCodTipComp", query = "SELECT b FROM BsTipComprobantes b WHERE b.codTipComp = :codTipComp")
    , @NamedQuery(name = "BsTipComprobantes.findByDescripcion", query = "SELECT b FROM BsTipComprobantes b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsTipComprobantes.findByIndSaldo", query = "SELECT b FROM BsTipComprobantes b WHERE b.indSaldo = :indSaldo")
    , @NamedQuery(name = "BsTipComprobantes.findByIndStock", query = "SELECT b FROM BsTipComprobantes b WHERE b.indStock = :indStock")
    , @NamedQuery(name = "BsTipComprobantes.findByActivo", query = "SELECT b FROM BsTipComprobantes b WHERE b.activo = :activo")})
public class BsTipComprobantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_tip_comp")
    private String codTipComp;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "ind_saldo")
    private String indSaldo;
    @Size(max = 1)
    @Column(name = "ind_stock")
    private String indStock;
    @Size(max = 1)
    @Column(name = "activo")
    private String activo;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    @JoinColumn(name = "bs_modulos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsModulos bsModulos;
    
    @Transient
    private boolean indStockAux;
    @Transient
    private boolean indSaldoAux;
    
    
    public BsTipComprobantes() {
    }

    public BsTipComprobantes(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodTipComp() {
        return codTipComp;
    }

    public void setCodTipComp(String codTipComp) {
        this.codTipComp = codTipComp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIndSaldo() {
        return indSaldo;
    }

    public void setIndSaldo(String indSaldo) {
        this.indSaldo = indSaldo;
    }

    public String getIndStock() {
        return indStock;
    }

    public void setIndStock(String indStock) {
        this.indStock = indStock;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public BsModulos getBsModulos() {
        return bsModulos;
    }

    public void setBsModulos(BsModulos bsModulos) {
        this.bsModulos = bsModulos;
    }

    public boolean isIndStockAux() {
        if (indStock != null) {
            indStockAux = indStock.equals("S");
        }
        return indStockAux;
    }

    public void setIndStockAux(boolean indStockAux) {
        indStock = indStockAux ? "S" : "N";
        this.indStockAux = indStockAux;
    }

    public boolean isIndSaldoAux() {
        if (indSaldo != null) {
            indStockAux = indSaldo.equals("S");
        }
        return indSaldoAux;
    }

    public void setIndSaldoAux(boolean indSaldoAux) {
        indSaldo = indSaldoAux ? "S" : "N";
        this.indSaldoAux = indSaldoAux;
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
        if (!(object instanceof BsTipComprobantes)) {
            return false;
        }
        BsTipComprobantes other = (BsTipComprobantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsTipComprobantes[ id=" + id + " ]";
    }
    
}
