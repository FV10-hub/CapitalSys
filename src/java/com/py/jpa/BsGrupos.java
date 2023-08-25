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
@Table(name = "bs_grupos")
@NamedQueries({
    @NamedQuery(name = "BsGrupos.findAll", query = "SELECT b FROM BsGrupos b")
    , @NamedQuery(name = "BsGrupos.findById", query = "SELECT b FROM BsGrupos b WHERE b.id = :id")
    , @NamedQuery(name = "BsGrupos.findByCodGrupo", query = "SELECT b FROM BsGrupos b WHERE b.codGrupo = :codGrupo")
    , @NamedQuery(name = "BsGrupos.findByDescripcion", query = "SELECT b FROM BsGrupos b WHERE b.descripcion = :descripcion")
    , @NamedQuery(name = "BsGrupos.findByEstado", query = "SELECT b FROM BsGrupos b WHERE b.estado = :estado")})
public class BsGrupos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "cod_grupo")
    private String codGrupo;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    
    @Transient
    private boolean estadoAux;

    public BsGrupos() {
    }

    public BsGrupos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isEstadoAux() {
        if (estado != null) {
            estadoAux = estado.equals("S");
        }
        return estadoAux;
    }

    public void setEstadoAux(boolean estadoAux) {
        estado = estadoAux ? "S" : "N";
        this.estadoAux = estadoAux;
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
        if (!(object instanceof BsGrupos)) {
            return false;
        }
        BsGrupos other = (BsGrupos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsGrupos[ id=" + id + " ]";
    }
    
}
