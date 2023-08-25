/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "tes_pago_valor")
@NamedQueries({
    @NamedQuery(name = "TesPagoValor.findAll", query = "SELECT t FROM TesPagoValor t")
    , @NamedQuery(name = "TesPagoValor.findById", query = "SELECT t FROM TesPagoValor t WHERE t.id = :id")
    , @NamedQuery(name = "TesPagoValor.findByFechaValor", query = "SELECT t FROM TesPagoValor t WHERE t.fechaValor = :fechaValor")
    , @NamedQuery(name = "TesPagoValor.findByFechaVencimiento", query = "SELECT t FROM TesPagoValor t WHERE t.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "TesPagoValor.findByNroVaor", query = "SELECT t FROM TesPagoValor t WHERE t.nroVaor = :nroVaor")
    , @NamedQuery(name = "TesPagoValor.findByTipoCambio", query = "SELECT t FROM TesPagoValor t WHERE t.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "TesPagoValor.findByNroCuenta", query = "SELECT t FROM TesPagoValor t WHERE t.nroCuenta = :nroCuenta")
    , @NamedQuery(name = "TesPagoValor.findByEstado", query = "SELECT t FROM TesPagoValor t WHERE t.estado = :estado")})
public class TesPagoValor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha_valor")
    @Temporal(TemporalType.DATE)
    private Date fechaValor;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Size(max = 50)
    @Column(name = "nro_vaor")
    private String nroVaor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Size(max = 50)
    @Column(name = "nro_cuenta")
    private String nroCuenta;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedasId;
    @JoinColumn(name = "bs_tipo_valor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTipoValor bsTipoValorId;
    @JoinColumn(name = "tes_pago_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesPagoCab tesPagoCabId;

    public TesPagoValor() {
    }

    public TesPagoValor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaValor() {
        return fechaValor;
    }

    public void setFechaValor(Date fechaValor) {
        this.fechaValor = fechaValor;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNroVaor() {
        return nroVaor;
    }

    public void setNroVaor(String nroVaor) {
        this.nroVaor = nroVaor;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
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

    public BsMonedas getBsMonedasId() {
        return bsMonedasId;
    }

    public void setBsMonedasId(BsMonedas bsMonedasId) {
        this.bsMonedasId = bsMonedasId;
    }

    public BsTipoValor getBsTipoValorId() {
        return bsTipoValorId;
    }

    public void setBsTipoValorId(BsTipoValor bsTipoValorId) {
        this.bsTipoValorId = bsTipoValorId;
    }

    public TesPagoCab getTesPagoCabId() {
        return tesPagoCabId;
    }

    public void setTesPagoCabId(TesPagoCab tesPagoCabId) {
        this.tesPagoCabId = tesPagoCabId;
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
        if (!(object instanceof TesPagoValor)) {
            return false;
        }
        TesPagoValor other = (TesPagoValor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesPagoValor[ id=" + id + " ]";
    }
    
}
