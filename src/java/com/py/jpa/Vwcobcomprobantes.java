/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "vwcobcomprobantes")
@NamedQueries({
    @NamedQuery(name = "Vwcobcomprobantes.findAll", query = "SELECT v FROM Vwcobcomprobantes v")
    , @NamedQuery(name = "Vwcobcomprobantes.findById", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.id = :id")
    , @NamedQuery(name = "Vwcobcomprobantes.findByTipoComprobante", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.tipoComprobante = :tipoComprobante")
    , @NamedQuery(name = "Vwcobcomprobantes.findByCodCliente", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.codCliente = :codCliente")
    , @NamedQuery(name = "Vwcobcomprobantes.findByNomCliente", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.nomCliente = :nomCliente")
    , @NamedQuery(name = "Vwcobcomprobantes.findByFecComprobante", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.fecComprobante = :fecComprobante")
    , @NamedQuery(name = "Vwcobcomprobantes.findByNroRecibo", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.nroRecibo = :nroRecibo")
    , @NamedQuery(name = "Vwcobcomprobantes.findByMontoCobro", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.montoCobro = :montoCobro")
    , @NamedQuery(name = "Vwcobcomprobantes.findByFecVencimiento", query = "SELECT v FROM Vwcobcomprobantes v WHERE v.fecVencimiento = :fecVencimiento")})
public class Vwcobcomprobantes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Long id;
    @Size(max = 10)
    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
    @Size(max = 15)
    @Column(name = "cod_cliente")
    private String codCliente;
    @Size(max = 100)
    @Column(name = "nom_cliente")
    private String nomCliente;
    @Size(max = 2147483647)
    @Column(name = "fec_comprobante")
    private String fecComprobante;
    @Size(max = 2147483647)
    @Column(name = "nro_recibo")
    private String nroRecibo;
    @Column(name = "monto_cobro")
    private BigDecimal montoCobro;
    @Size(max = 2147483647)
    @Column(name = "fec_vencimiento")
    private String fecVencimiento;

    @JoinColumn(name = "id_recibo", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobRecibosCab cobReciboCab;

    @JoinColumn(name = "id_comprobante", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private VenComprobantesCabecera venComprobantesCabecera;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CobClientes cobClientes;
    
    public Vwcobcomprobantes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getFecComprobante() {
        return fecComprobante;
    }

    public void setFecComprobante(String fecComprobante) {
        this.fecComprobante = fecComprobante;
    }

    public String getNroRecibo() {
        return nroRecibo;
    }

    public void setNroRecibo(String nroRecibo) {
        this.nroRecibo = nroRecibo;
    }

    public String getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(String fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public BigDecimal getMontoCobro() {
        return montoCobro;
    }

    public void setMontoCobro(BigDecimal montoCobro) {
        this.montoCobro = montoCobro;
    }

    public CobRecibosCab getCobReciboCab() {
        return cobReciboCab;
    }

    public void setCobReciboCab(CobRecibosCab cobReciboCab) {
        this.cobReciboCab = cobReciboCab;
    }

    public VenComprobantesCabecera getVenComprobantesCabecera() {
        return venComprobantesCabecera;
    }

    public void setVenComprobantesCabecera(VenComprobantesCabecera venComprobantesCabecera) {
        this.venComprobantesCabecera = venComprobantesCabecera;
    }

    public CobClientes getCobClientes() {
        return cobClientes;
    }

    public void setCobClientes(CobClientes cobClientes) {
        this.cobClientes = cobClientes;
    }

}
