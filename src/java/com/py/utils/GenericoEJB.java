/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author Edudzl
 */
@Stateless
public class GenericoEJB {

    @PersistenceContext
    private EntityManager em;
    private GenericMensajes msn = new GenericMensajes();

    public boolean insert(Object entidad) {
        try {
            em.persist(entidad);
            em.flush();
            return true;
        } catch (PersistenceException e) {
            messageError(e);
            msn.mensajeError("Error al insertar." + e);
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean delete(Object entidad) {
        try {
            em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.flush();
            return false;
        } catch (PersistenceException e) {
            messageError(e);
            msn.mensajeError("Error al borrar." + e);
            e.printStackTrace(System.err);
            return true;
        }
    }

    public boolean update(Object entidad) {
        try {
            em.merge(entidad);
            em.flush();
            return false;
        } catch (PersistenceException e) {
            messageError(e);
            msn.mensajeError("Error al actualizar." + e);
            e.printStackTrace(System.err);
            return false;
        }
    }

    public boolean borrar(Object entidad) {
        try {
            em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.flush();
            msn.mensajeExito("¡Se elimino correctamente la Sucursal!");
            return false;
        } catch (PersistenceException e) {
            messageError(e);
            msn.mensajeError("Error al borrar.");
            return true;
        }
    }

    protected List<String> getErrorCode(RuntimeException e) {
        List<String> errorList = new ArrayList<>();
        Throwable throwable = e;
        while (throwable != null && !(throwable instanceof SQLException)) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof SQLException) {
            SQLException sqlex = (SQLException) throwable;
            String errorCode = sqlex.getErrorCode() + "";
            errorList.add(errorCode);
            errorList.add(sqlex.getMessage());
            return errorList;
        }
        return null;
    }

    protected void messageError(PersistenceException e) {
        List<String> sqlErrorList = getErrorCode(e);
        System.out.println("LISTA DE ERROR --> " + sqlErrorList);
        switch (sqlErrorList.get(0)) {
            case "20000":
                msn.mensajeError((sqlErrorList.get(1).split("ORA-"))[1].substring(6));
                break;
            case "2292":
                msn.mensajeError("El Registro se encuentra Referenciado en otros Movimientos");
                break;
            case "1400":
                msn.mensajeError("Debe completar los datos Requeridos");
                break;
            default:
                msn.mensajeError("" + e);
        }
        msn.mensajeError((sqlErrorList.get(1).split("ORA-"))[1].substring(6));
//        for (String reg : sqlErrorList) {
//            System.out.println("sqlErrorList --> " + reg);
//        }
//        System.out.println("################## --> " + (sqlErrorList.get(1).split("ORA-"))[1].substring(6));
        e.printStackTrace(System.err);
    }
}
