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
@Table(name = "bs_tipo_valor")
@NamedQueries({
    @NamedQuery(name = "BsTipoValor.findAll", query = "SELECT b FROM BsTipoValor b")
    , @NamedQuery(name = "BsTipoValor.findById", query = "SELECT b FROM BsTipoValor b WHERE b.id = :id")
    , @NamedQuery(name = "BsTipoValor.findByCodTipo", query = "SELECT b FROM BsTipoValor b WHERE b.codTipo = :codTipo")
    , @NamedQuery(name = "BsTipoValor.findByDescripcion", query = "SELECT b FROM BsTipoValor b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsTipoValor.findByUsaEfectivo", query = "SELECT b FROM BsTipoValor b WHERE b.usaEfectivo = :usaEfectivo")
    , @NamedQuery(name = "BsTipoValor.findByEstado", query = "SELECT b FROM BsTipoValor b WHERE b.estado = :estado")})
public class BsTipoValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_tipo")
    private String codTipo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "usa_efectivo")
    private String usaEfectivo;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "bs_modulos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsModulos bsModulos;
    
    @Transient
    private boolean usaDineroAux;

    public BsTipoValor() {
    }

    public BsTipoValor(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BsModulos getBsModulos() {
        return bsModulos;
    }

    public void setBsModulos(BsModulos bsModulos) {
        this.bsModulos = bsModulos;
    }



    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsaEfectivo() {
        return usaEfectivo;
    }

    public void setUsaEfectivo(String usaEfectivo) {
        this.usaEfectivo = usaEfectivo;
    }

    public boolean isUsaDineroAux() {
        if (usaEfectivo != null) {
            usaDineroAux = usaEfectivo.equals("S");
        }
        return usaDineroAux;
    }

    public void setUsaDineroAux(boolean usaDineroAux) {
        usaEfectivo = usaDineroAux ? "S" : "N";
        this.usaDineroAux = usaDineroAux;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        if (!(object instanceof BsTipoValor)) {
            return false;
        }
        BsTipoValor other = (BsTipoValor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsTipoValor[ id=" + id + " ]";
    }
    
}
