/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.py.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Edudzl
 * @param <T>
 */
public class GenericBigLazyList<T> extends LazyDataModel<T> implements Serializable {

    protected Class<T> clase;
    private EntityManager em;
    private List<T> lazyList;
    private Query q;
    private Map<String, Object> filtros;
    private StringBuilder query, query1, w, ob, gb;
    private Field field;
    boolean where, or;

    public GenericBigLazyList() {

    }

    public GenericBigLazyList(EntityManager em) {
        this.em = em;
    }

    public LazyDataModel getLazyDataModel(Class c, Map<String, Object> filtros) {
        this.clase = c;
        this.filtros = filtros;
        generarSelect();
        return this;
    }

    @Override
    public T getRowData(String rowKey) {
        for (T o : lazyList) {
            if (getFieldData(o, "id").equals(rowKey)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        generarSelect();
        if (filters != null) {
            for (Map.Entry<String, Object> pairs : filters.entrySet()) {
                if (where) {
                    w.append(" WHERE ");
                    where = false;
                } else {
                    w.append(" AND ");
                }
                if (pairs.getKey().equalsIgnoreCase("where")) {
                    w.append(pairs.getValue());
                } else if (pairs.getValue() instanceof String) {
                    w.append("UPPER(b.").append(pairs.getKey()).append(") LIKE '%");
                    w.append(pairs.getValue().toString().toUpperCase()).append("%'");
                } else if (pairs.getValue() instanceof Number) {
                    w.append("b.").append(pairs.getKey()).append(" LIKE %");
                    w.append(pairs.getValue()).append("%");
                }
            }
        }
        if (sortField != null) {
//            if(orderby){
//                if(ob.toString().contains(sortField)){
//                    ob.replace(ob.toString().lastIndexOf(rowKey), ob.substring(ob.toString().lastIndexOf(rowKey),ob.length()).contains(",")?ob.substring(ob.toString().lastIndexOf(rowKey),ob.length()).indexOf(",")-1:ob.length(), " ".concat(sortOrder.equals(sortOrder.ASCENDING)?"ASC":"DESC"));
//                }else{
//                    ob.append(", b.").append(sortField).append(" ").append(sortOrder.equals(sortOrder.ASCENDING)?"ASC":"DESC");
//                }
//            }else{
            ob = new StringBuilder();
            ob.append(" ORDER BY b.").append(sortField).append(" ").append(sortOrder.equals(sortOrder.ASCENDING) ? "ASC" : "DESC");
//            }
        }
        q = em.createQuery(query.append(w).append(ob).append(gb).toString(), clase).setHint(QueryHints.REFRESH, true);
        setRowCount(((Long) em.createQuery(query1.append(w).append(gb).toString()).getSingleResult()).intValue());
        q.setFirstResult(first);
        q.setMaxResults(pageSize);
        lazyList = q.getResultList();
        System.out.println("QUERY " + query);
        return lazyList;
    }

    public void generarSelect() {
        where = true;
        ob = new StringBuilder();
        gb = new StringBuilder();
        w = new StringBuilder();
        query = new StringBuilder("SELECT b FROM ").append(clase.getSimpleName()).append(" b");
        query1 = new StringBuilder("SELECT COUNT(b) FROM ").append(clase.getSimpleName()).append(" b");
        for (Map.Entry<String, Object> pairs : filtros.entrySet()) {
            if (!pairs.getKey().equalsIgnoreCase("ORDER BY") && !pairs.getKey().equalsIgnoreCase("OR")) {
                if (where) {
                    w.append(" WHERE ");
                    where = false;
                } else {
                    w.append(" AND ");
                }
                if (pairs.getKey().equalsIgnoreCase("where")) {
                    w.append(pairs.getValue());
                } else if (pairs.getValue() instanceof String) {
                    w.append("b.").append(pairs.getKey()).append(" = '");
                    w.append(pairs.getValue()).append("'");
                } else if (pairs.getValue() instanceof Number) {
                    w.append("b.").append(pairs.getKey()).append(" = ");
                    w.append(pairs.getValue()).append("");
                } else if (pairs.getValue() instanceof Date) {
                    w.append("b.").append(pairs.getKey()).append(" >= ");
                    w.append(pairs.getValue()).append("");
                    w.append(" AND b.").append(pairs.getKey()).append(" <= ");
                    w.append(pairs.getValue()).append("");
                } else if ((pairs.getValue() instanceof List)) {
                    w.append("b.").append(pairs.getKey()).append(" IN (");
                    w.append(pairs.getValue().toString()).append(")");
                }
            } else if (pairs.getKey().equalsIgnoreCase("OR")) {
                w.append(" ").append(pairs.getKey()).append(" ");
                w.append(pairs.getValue());
            } else if (pairs.getKey().equalsIgnoreCase("ORDER BY")) {
                ob.append(" ").append(pairs.getKey()).append(" ");
                ob.append(pairs.getValue());
            } else if (pairs.getKey().equalsIgnoreCase("GROUP BY")) {
                gb.append(" ").append(pairs.getKey()).append(" ");
                gb.append(pairs.getValue());
            }
        }
    }

    public String getFieldData(Object o, String key) {
        String g = null;
        try {
            if (key.contains(".")) {
                for (String s : key.split("\\.")) {
                    field = o.getClass().getDeclaredField(s);
                    field.setAccessible(true);
                    o = field.get(o);
                }
                g = String.valueOf(o);
            } else {
                field = o.getClass().getDeclaredField(key);
                field.setAccessible(true);
                g = String.valueOf(field.get(o));
            }
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace(System.out);
        }
        return g;
    }
}
