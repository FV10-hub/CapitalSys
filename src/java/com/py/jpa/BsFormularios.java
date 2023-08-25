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
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_formularios")
@NamedQueries({
    @NamedQuery(name = "BsFormularios.findAll", query = "SELECT b FROM BsFormularios b")
    , @NamedQuery(name = "BsFormularios.findById", query = "SELECT b FROM BsFormularios b WHERE b.id = :id")
    , @NamedQuery(name = "BsFormularios.findByTitulo", query = "SELECT b FROM BsFormularios b WHERE b.titulo = :titulo")
    , @NamedQuery(name = "BsFormularios.findByUrl", query = "SELECT b FROM BsFormularios b WHERE b.url = :url")})
public class BsFormularios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 150)
    @Column(name = "titulo")
    private String titulo;
    @Size(max = 150)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "bs_modulos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsModulos bsModulos;

    public BsFormularios() {
    }

    public BsFormularios(Long id) {
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        if (!(object instanceof BsFormularios)) {
            return false;
        }
        BsFormularios other = (BsFormularios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsFormularios[ id=" + id + " ]";
    }
    
}
