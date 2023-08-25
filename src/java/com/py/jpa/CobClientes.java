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
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_clientes")
@NamedQueries({
    @NamedQuery(name = "CobClientes.findAll", query = "SELECT c FROM CobClientes c")
    , @NamedQuery(name = "CobClientes.findById", query = "SELECT c FROM CobClientes c WHERE c.id = :id")
    , @NamedQuery(name = "CobClientes.findByCodCliente", query = "SELECT c FROM CobClientes c WHERE c.codCliente = :codCliente")
    , @NamedQuery(name = "CobClientes.findByEstado", query = "SELECT c FROM CobClientes c WHERE c.estado = :estado")})
public class CobClientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 15)
    @Column(name = "cod_cliente")
    private String codCliente;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;

    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    @JoinColumn(name = "BS_PERSONAS_ID", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;

    @JoinColumn(name = "cob_tipo_cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobTipoCliente cobTipoCliente;

    public CobClientes() {
    }

    public CobClientes(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public BsPersonas getBsPersonas() {
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }

    public CobTipoCliente getCobTipoCliente() {
        return cobTipoCliente;
    }

    public void setCobTipoCliente(CobTipoCliente cobTipoCliente) {
        this.cobTipoCliente = cobTipoCliente;
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
        if (!(object instanceof CobClientes)) {
            return false;
        }
        CobClientes other = (CobClientes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobClientes[ id=" + id + " ]";
    }
    
}
