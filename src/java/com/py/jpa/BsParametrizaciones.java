/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_parametrizaciones")
@NamedQueries({
    @NamedQuery(name = "BsParametrizaciones.findAll", query = "SELECT b FROM BsParametrizaciones b")
    , @NamedQuery(name = "BsParametrizaciones.findById", query = "SELECT b FROM BsParametrizaciones b WHERE b.id = :id")
    , @NamedQuery(name = "BsParametrizaciones.findByParametro", query = "SELECT b FROM BsParametrizaciones b WHERE b.parametro = :parametro")
    , @NamedQuery(name = "BsParametrizaciones.findByDescripcion", query = "SELECT b FROM BsParametrizaciones b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsParametrizaciones.findByValor", query = "SELECT b FROM BsParametrizaciones b WHERE b.valor = :valor")})
public class BsParametrizaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "parametro")
    private String parametro;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private BsEmpresas bsEmpresas;
    @JoinColumn(name = "bs_modulos_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private BsModulos bsModulos;

    public BsParametrizaciones() {
    }

    public BsParametrizaciones(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BsParametrizaciones)) {
            return false;
        }
        BsParametrizaciones other = (BsParametrizaciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsParametrizaciones[ id=" + id + " ]";
    }

}
