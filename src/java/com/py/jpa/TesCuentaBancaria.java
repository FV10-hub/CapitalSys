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
@Table(name = "tes_cuenta_bancaria")
@NamedQueries({
    @NamedQuery(name = "TesCuentaBancaria.findAll", query = "SELECT t FROM TesCuentaBancaria t")
    , @NamedQuery(name = "TesCuentaBancaria.findById", query = "SELECT t FROM TesCuentaBancaria t WHERE t.id = :id")
    , @NamedQuery(name = "TesCuentaBancaria.findByNroCuenta", query = "SELECT t FROM TesCuentaBancaria t WHERE t.nroCuenta = :nroCuenta")
    , @NamedQuery(name = "TesCuentaBancaria.findByEstado", query = "SELECT t FROM TesCuentaBancaria t WHERE t.estado = :estado")
    , @NamedQuery(name = "TesCuentaBancaria.findByReferencia", query = "SELECT t FROM TesCuentaBancaria t WHERE t.referencia = :referencia")})
public class TesCuentaBancaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 80)
    @Column(name = "nro_cuenta")
    private String nroCuenta;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Size(max = 80)
    @Column(name = "referencia")
    private String referencia;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;
    @JoinColumn(name = "bs_personas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsPersonas bsPersonas;
    @JoinColumn(name = "tes_tipo_cuentas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesTipoCuentas tesTipoCuentas;


    @Transient
    private boolean estadoAux;
    public TesCuentaBancaria() {
    }

    public TesCuentaBancaria(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public BsPersonas getBsPersonas() {
        return bsPersonas;
    }

    public void setBsPersonas(BsPersonas bsPersonas) {
        this.bsPersonas = bsPersonas;
    }

    public TesTipoCuentas getTesTipoCuentas() {
        return tesTipoCuentas;
    }

    public void setTesTipoCuentas(TesTipoCuentas tesTipoCuentas) {
        this.tesTipoCuentas = tesTipoCuentas;
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
        if (!(object instanceof TesCuentaBancaria)) {
            return false;
        }
        TesCuentaBancaria other = (TesCuentaBancaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesCuentaBancaria[ id=" + id + " ]";
    }
    
}
