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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "tes_depositos_cab")
@NamedQueries({
    @NamedQuery(name = "TesDepositosCab.findAll", query = "SELECT t FROM TesDepositosCab t")
    , @NamedQuery(name = "TesDepositosCab.findById", query = "SELECT t FROM TesDepositosCab t WHERE t.id = :id")
    , @NamedQuery(name = "TesDepositosCab.findByTipCambio", query = "SELECT t FROM TesDepositosCab t WHERE t.tipCambio = :tipCambio")
    , @NamedQuery(name = "TesDepositosCab.findByTotCredito", query = "SELECT t FROM TesDepositosCab t WHERE t.totCredito = :totCredito")
    , @NamedQuery(name = "TesDepositosCab.findByTotDebito", query = "SELECT t FROM TesDepositosCab t WHERE t.totDebito = :totDebito")
    , @NamedQuery(name = "TesDepositosCab.findByFechaDeposito", query = "SELECT t FROM TesDepositosCab t WHERE t.fechaDeposito = :fechaDeposito")
    , @NamedQuery(name = "TesDepositosCab.findByFecAlta", query = "SELECT t FROM TesDepositosCab t WHERE t.fecAlta = :fecAlta")
    , @NamedQuery(name = "TesDepositosCab.findByUsuAlta", query = "SELECT t FROM TesDepositosCab t WHERE t.usuAlta = :usuAlta")})
public class TesDepositosCab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tip_cambio")
    private BigDecimal tipCambio;
    @Column(name = "tot_credito")
    private BigDecimal totCredito;
    @Column(name = "tot_debito")
    private BigDecimal totDebito;
    @Column(name = "fecha_deposito")
    @Temporal(TemporalType.DATE)
    private Date fechaDeposito;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usu_alta")
    private Integer usuAlta;
    @Column(name = "nro_deposito")
    private BigDecimal nroDeposito;
    @Column(name = "nro_boleta")
    private Integer nroBoleta;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tesDepositosCab", fetch = FetchType.LAZY)
    private List<TesDepositoDet> tesDepositoDetList;

    @JoinColumn(name = "bs_empresas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsEmpresas bsEmpresas;

    @JoinColumn(name = "bs_monedas_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsMonedas bsMonedas;

    @JoinColumn(name = "bs_sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsSucursal bsSucursal;

    @JoinColumn(name = "bs_talonarios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BsTalonarios bsTalonarios;

    @JoinColumn(name = "cob_clientes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;

    @JoinColumn(name = "cob_habilitaciones_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobHabilitaciones cobHabilitaciones;

    @JoinColumn(name = "tes_cuenta_bancaria_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TesCuentaBancaria tesCuentaBancaria;

    public TesDepositosCab() {
    }

    public TesDepositosCab(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTipCambio() {
        return tipCambio;
    }

    public void setTipCambio(BigDecimal tipCambio) {
        this.tipCambio = tipCambio;
    }

    public BigDecimal getTotCredito() {
        return totCredito;
    }

    public void setTotCredito(BigDecimal totCredito) {
        this.totCredito = totCredito;
    }

    public BigDecimal getTotDebito() {
        return totDebito;
    }

    public void setTotDebito(BigDecimal totDebito) {
        this.totDebito = totDebito;
    }

    public Date getFechaDeposito() {
        return fechaDeposito;
    }

    public void setFechaDeposito(Date fechaDeposito) {
        this.fechaDeposito = fechaDeposito;
    }

    public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public Integer getUsuAlta() {
        return usuAlta;
    }

    public void setUsuAlta(Integer usuAlta) {
        this.usuAlta = usuAlta;
    }

    public List<TesDepositoDet> getTesDepositoDetList() {
        return tesDepositoDetList;
    }

    public void setTesDepositoDetList(List<TesDepositoDet> tesDepositoDetList) {
        this.tesDepositoDetList = tesDepositoDetList;
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

    public BsSucursal getBsSucursal() {
        return bsSucursal;
    }

    public void setBsSucursal(BsSucursal bsSucursal) {
        this.bsSucursal = bsSucursal;
    }

    public BsTalonarios getBsTalonarios() {
        return bsTalonarios;
    }

    public void setBsTalonarios(BsTalonarios bsTalonarios) {
        this.bsTalonarios = bsTalonarios;
    }

    public CobClientes getCobClientes() {
        return cobClientes;
    }

    public void setCobClientes(CobClientes cobClientes) {
        this.cobClientes = cobClientes;
    }

    public CobHabilitaciones getCobHabilitaciones() {
        return cobHabilitaciones;
    }

    public void setCobHabilitaciones(CobHabilitaciones cobHabilitaciones) {
        this.cobHabilitaciones = cobHabilitaciones;
    }

    public TesCuentaBancaria getTesCuentaBancaria() {
        return tesCuentaBancaria;
    }

    public void setTesCuentaBancaria(TesCuentaBancaria tesCuentaBancaria) {
        this.tesCuentaBancaria = tesCuentaBancaria;
    }

    public BigDecimal getNroDeposito() {
        return nroDeposito;
    }

    public void setNroDeposito(BigDecimal nroDeposito) {
        this.nroDeposito = nroDeposito;
    }

    public Integer getNroBoleta() {
        return nroBoleta;
    }

    public void setNroBoleta(Integer nroBoleta) {
        this.nroBoleta = nroBoleta;
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
        if (!(object instanceof TesDepositosCab)) {
            return false;
        }
        TesDepositosCab other = (TesDepositosCab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.TesDepositosCab[ id=" + id + " ]";
    }

}
