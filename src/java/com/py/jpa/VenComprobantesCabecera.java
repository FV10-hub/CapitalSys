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

/**
 *
 * @author Ac3r
 */
@Entity
@Table(name = "ven_comprobantes_cabecera")
@NamedQueries({
    @NamedQuery(name = "VenComprobantesCabecera.findAll", query = "SELECT v FROM VenComprobantesCabecera v")
    , @NamedQuery(name = "VenComprobantesCabecera.findById", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.id = :id")
    , @NamedQuery(name = "VenComprobantesCabecera.findByFecComprobante", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.fecComprobante = :fecComprobante")
    , @NamedQuery(name = "VenComprobantesCabecera.findByFecAlta", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.fecAlta = :fecAlta")
    , @NamedQuery(name = "VenComprobantesCabecera.findByUsuarioAlta", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.usuarioAlta = :usuarioAlta")
    , @NamedQuery(name = "VenComprobantesCabecera.findByTipoCambio", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.tipoCambio = :tipoCambio")
    , @NamedQuery(name = "VenComprobantesCabecera.findByMontoTotal", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.montoTotal = :montoTotal")
    , @NamedQuery(name = "VenComprobantesCabecera.findByTotalGravada", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.totalGravada = :totalGravada")
    , @NamedQuery(name = "VenComprobantesCabecera.findByTotalIva", query = "SELECT v FROM VenComprobantesCabecera v WHERE v.totalIva = :totalIva")})
public class VenComprobantesCabecera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "FACTURACAB_SEQ_GENERATOR", sequenceName = "ven_comprobantes_cabecera_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FACTURACAB_SEQ_GENERATOR")
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "fec_comprobante")
    @Temporal(TemporalType.DATE)
    private Date fecComprobante;
    @Column(name = "fec_alta")
    @Temporal(TemporalType.DATE)
    private Date fecAlta;
    @Column(name = "usuario_alta")
    private Integer usuarioAlta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;
    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    @Column(name = "total_gravada")
    private BigDecimal totalGravada;
    @Column(name = "total_iva")
    private BigDecimal totalIva;
    @Column(name = "nro_comprobante")
    private BigDecimal nroComprobante;
    @Column(name = "ser_timbrado")
    private String serTimbtado;
    @Column(name = "estado")
    private String estado;

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

    @JoinColumn(name = "cob_cobros_cab_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobCobrosCab cobCobrosCab;

    @JoinColumn(name = "cob_habilitaciones_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobHabilitaciones cobHabilitaciones;

    @JoinColumn(name = "ven_condicion_venta_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VenCondicionVenta venCondicionVenta;

    @JoinColumn(name = "ven_vendedor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VenVendedor venVendedor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venComprobantesCabecera", fetch = FetchType.LAZY)
    private List<VenComprobantesDetalle> venComprobantesDetalleList;

    public VenComprobantesCabecera() {
    }

    public VenComprobantesCabecera(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecComprobante() {
        return fecComprobante;
    }

    public void setFecComprobante(Date fecComprobante) {
        this.fecComprobante = fecComprobante;
    }

    public Date getFecAlta() {
        return fecAlta;
    }

    public void setFecAlta(Date fecAlta) {
        this.fecAlta = fecAlta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Integer getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Integer usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getTotalGravada() {
        return totalGravada;
    }

    public void setTotalGravada(BigDecimal totalGravada) {
        this.totalGravada = totalGravada;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public List<VenComprobantesDetalle> getVenComprobantesDetalleList() {
        return venComprobantesDetalleList;
    }

    public void setVenComprobantesDetalleList(List<VenComprobantesDetalle> venComprobantesDetalleList) {
        this.venComprobantesDetalleList = venComprobantesDetalleList;
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

    public CobCobrosCab getCobCobrosCab() {
        return cobCobrosCab;
    }

    public void setCobCobrosCab(CobCobrosCab cobCobrosCab) {
        this.cobCobrosCab = cobCobrosCab;
    }

    public CobHabilitaciones getCobHabilitaciones() {
        return cobHabilitaciones;
    }

    public void setCobHabilitaciones(CobHabilitaciones cobHabilitaciones) {
        this.cobHabilitaciones = cobHabilitaciones;
    }

    public VenCondicionVenta getVenCondicionVenta() {
        return venCondicionVenta;
    }

    public void setVenCondicionVenta(VenCondicionVenta venCondicionVenta) {
        this.venCondicionVenta = venCondicionVenta;
    }

    public VenVendedor getVenVendedor() {
        return venVendedor;
    }

    public void setVenVendedor(VenVendedor venVendedor) {
        this.venVendedor = venVendedor;
    }

    public BigDecimal getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(BigDecimal nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getSerTimbtado() {
        return serTimbtado;
    }

    public void setSerTimbtado(String serTimbtado) {
        this.serTimbtado = serTimbtado;
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
        if (!(object instanceof VenComprobantesCabecera)) {
            return false;
        }
        VenComprobantesCabecera other = (VenComprobantesCabecera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.py.jpa.VenComprobantesCabecera[ id=" + id + " ]";
    }

}
