/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "cob_cobros_valores")
@NamedQueries({
    @NamedQuery(name = "CobCobrosValores.findAll", query = "SELECT c FROM CobCobrosValores c")
    , @NamedQuery(name = "CobCobrosValores.findByCabid", query = "SELECT c FROM CobCobrosValores c WHERE c.cobCobrosCab.id = :idCab")
    , @NamedQuery(name = "CobCobrosValores.findById", query = "SELECT c FROM CobCobrosValores c WHERE c.id = :id")
    , @NamedQuery(name = "CobCobrosValores.findByFechaValor", query = "SELECT c FROM CobCobrosValores c WHERE c.fechaValor = :fechaValor")
    , @NamedQuery(name = "CobCobrosValores.findByFechaVencimiento", query = "SELECT c FROM CobCobrosValores c WHERE c.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "CobCobrosValores.findByNroValor", query = "SELECT c FROM CobCobrosValores c WHERE c.nroValor = :nroValor")
    , @NamedQuery(name = "CobCobrosValores.findByTipoCambio", query = "SELECT c FROM CobCobrosValores c WHERE c.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "CobCobrosValores.findByMontoValor", query = "SELECT c FROM CobCobrosValores c WHERE c.montoValor = :montoValor")
    , @NamedQuery(name = "CobCobrosValores.findByTipValor", query = "SELECT c FROM CobCobrosValores c WHERE c.tipValor = :tipValor")
    , @NamedQuery(name = "CobCobrosValores.findByNroCuenta", query = "SELECT c FROM CobCobrosValores c WHERE c.nroCuenta = :nroCuenta")
    , @NamedQuery(name = "CobCobrosValores.findByEstado", query = "SELECT c FROM CobCobrosValores c WHERE c.estado = :estado")})
public class CobCobrosValores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "COBROSVAL_SEQ_GENERATOR", sequenceName = "cob_cobros_valores_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COBROSVAL_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fecha_valor")
    @Temporal(TemporalType.DATE)
    private Date fechaValor;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Size(max = 50)
    @Column(name = "nro_valor")
    private String nroValor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "monto_valor")
    private BigDecimal montoValor;
    @Size(max = 10)
    @Column(name = "tip_valor")
    private String tipValor;
    @Size(max = 50)
    @Column(name = "nro_cuenta")
    private String nroCuenta;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    
    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;
    
    @JoinColumn(name = "bs_tipo_valor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTipoValor bsTipoValor;
    
    @JoinColumn(name = "cob_cobros_cab_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrosCab cobCobrosCab;
    
    @JoinColumn(name = "id_cuenta_bancaria", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesCuentaBancaria tesCuentaBancaria;

    public CobCobrosValores() {
    }

    public CobCobrosValores(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getNroValor() {
        return nroValor;
    }

    public void setNroValor(String nroValor) {
        this.nroValor = nroValor;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getMontoValor() {
        return montoValor;
    }

    public void setMontoValor(BigDecimal montoValor) {
        this.montoValor = montoValor;
    }

    public String getTipValor() {
        return tipValor;
    }

    public void setTipValor(String tipValor) {
        this.tipValor = tipValor;
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

    public BsMonedas getBsMonedas() {
        return bsMonedas;
    }

    public void setBsMonedas(BsMonedas bsMonedas) {
        this.bsMonedas = bsMonedas;
    }

    public BsTipoValor getBsTipoValor() {
        return bsTipoValor;
    }

    public void setBsTipoValor(BsTipoValor bsTipoValor) {
        this.bsTipoValor = bsTipoValor;
    }

    public CobCobrosCab getCobCobrosCab() {
        return cobCobrosCab;
    }

    public void setCobCobrosCab(CobCobrosCab cobCobrosCab) {
        this.cobCobrosCab = cobCobrosCab;
    }

    public TesCuentaBancaria getTesCuentaBancaria() {
        return tesCuentaBancaria;
    }

    public void setTesCuentaBancaria(TesCuentaBancaria tesCuentaBancaria) {
        this.tesCuentaBancaria = tesCuentaBancaria;
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
        if (!(object instanceof CobCobrosValores)) {
            return false;
        }
        CobCobrosValores other = (CobCobrosValores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.CobCobrosValores[ id=" + id + " ]";
    }

}
