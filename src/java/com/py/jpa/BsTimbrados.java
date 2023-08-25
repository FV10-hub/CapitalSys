/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "bs_timbrados")
@NamedQueries({
    @NamedQuery(name = "BsTimbrados.findAll", query = "SELECT b FROM BsTimbrados b")
    , @NamedQuery(name = "BsTimbrados.findById", query = "SELECT b FROM BsTimbrados b WHERE b.id = :id")
    , @NamedQuery(name = "BsTimbrados.findByNroTimbrado", query = "SELECT b FROM BsTimbrados b WHERE b.nroTimbrado = :nroTimbrado")
    , @NamedQuery(name = "BsTimbrados.findByFecVigIni", query = "SELECT b FROM BsTimbrados b WHERE b.fecVigIni = :fecVigIni")
    , @NamedQuery(name = "BsTimbrados.findByFecVigFin", query = "SELECT b FROM BsTimbrados b WHERE b.fecVigFin = :fecVigFin")
    , @NamedQuery(name = "BsTimbrados.findByCodEstablecimiento", query = "SELECT b FROM BsTimbrados b WHERE b.codEstablecimiento = :codEstablecimiento")
    , @NamedQuery(name = "BsTimbrados.findByCodExpedicion", query = "SELECT b FROM BsTimbrados b WHERE b.codExpedicion = :codExpedicion")
    , @NamedQuery(name = "BsTimbrados.findByNroIni", query = "SELECT b FROM BsTimbrados b WHERE b.nroIni = :nroIni")
    , @NamedQuery(name = "BsTimbrados.findByNroFin", query = "SELECT b FROM BsTimbrados b WHERE b.nroFin = :nroFin")
    , @NamedQuery(name = "BsTimbrados.findByIndAutoimpresor", query = "SELECT b FROM BsTimbrados b WHERE b.indAutoimpresor = :indAutoimpresor")
    , @NamedQuery(name = "BsTimbrados.findByCodAutorizacion", query = "SELECT b FROM BsTimbrados b WHERE b.codAutorizacion = :codAutorizacion")})
public class BsTimbrados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "nro_timbrado")
    private BigInteger nroTimbrado;
    @Column(name = "fec_vig_ini")
    @Temporal(TemporalType.DATE)
    private Date fecVigIni;
    @Column(name = "fec_vig_fin")
    @Temporal(TemporalType.DATE)
    private Date fecVigFin;
    @Size(max = 10)
    @Column(name = "cod_establecimiento")
    private String codEstablecimiento;
    @Size(max = 10)
    @Column(name = "cod_expedicion")
    private String codExpedicion;
    @Column(name = "nro_ini")
    private BigInteger nroIni;
    @Column(name = "nro_fin")
    private BigInteger nroFin;
    @Size(max = 1)
    @Column(name = "ind_autoimpresor")
    private String indAutoimpresor;
    @Size(max = 100)
    @Column(name = "cod_autorizacion")
    private String codAutorizacion;
    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    @Transient
    private boolean autoimpresorAux;

    public BsTimbrados() {
    }

    public BsTimbrados(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getNroTimbrado() {
        return nroTimbrado;
    }

    public void setNroTimbrado(BigInteger nroTimbrado) {
        this.nroTimbrado = nroTimbrado;
    }

    public Date getFecVigIni() {
        return fecVigIni;
    }

    public void setFecVigIni(Date fecVigIni) {
        this.fecVigIni = fecVigIni;
    }

    public Date getFecVigFin() {
        return fecVigFin;
    }

    public void setFecVigFin(Date fecVigFin) {
        this.fecVigFin = fecVigFin;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getCodExpedicion() {
        return codExpedicion;
    }

    public void setCodExpedicion(String codExpedicion) {
        this.codExpedicion = codExpedicion;
    }

    public BigInteger getNroIni() {
        return nroIni;
    }

    public void setNroIni(BigInteger nroIni) {
        this.nroIni = nroIni;
    }

    public BigInteger getNroFin() {
        return nroFin;
    }

    public void setNroFin(BigInteger nroFin) {
        this.nroFin = nroFin;
    }

    public String getIndAutoimpresor() {
        return indAutoimpresor;
    }

    public void setIndAutoimpresor(String indAutoimpresor) {
        this.indAutoimpresor = indAutoimpresor;
    }

    public String getCodAutorizacion() {
        return codAutorizacion;
    }

    public void setCodAutorizacion(String codAutorizacion) {
        this.codAutorizacion = codAutorizacion;
    }

    public BsEmpresas getBsEmpresas() {
        return bsEmpresas;
    }

    public void setBsEmpresas(BsEmpresas bsEmpresas) {
        this.bsEmpresas = bsEmpresas;
    }

    public boolean isAutoimpresorAux() {
        if (indAutoimpresor != null) {
            autoimpresorAux = indAutoimpresor.equals("S");
        }
        return autoimpresorAux;
    }

    public void setAutoimpresorAux(boolean autoimpresorAux) {
        indAutoimpresor = autoimpresorAux ? "S" : "N";
        this.autoimpresorAux = autoimpresorAux;

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
        if (!(object instanceof BsTimbrados)) {
            return false;
        }
        BsTimbrados other = (BsTimbrados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.BsTimbrados[ id=" + id + " ]";
    }

}
