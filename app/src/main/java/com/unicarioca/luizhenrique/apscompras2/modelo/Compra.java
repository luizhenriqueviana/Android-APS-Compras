package com.unicarioca.luizhenrique.apscompras2.modelo;

import java.io.Serializable;

public class Compra implements Serializable {

    private Long id_compra;
    private String data_db;
    private String data_view;
    private String local_compra;
    private double total_compra;

    public Long getId_compra() {
        return id_compra;
    }

    public void setId_compra(Long id_compra) {
        this.id_compra = id_compra;
    }

    public String getData_db() {
        return data_db;
    }

    public void setData_db(String data_db) {
        this.data_db = data_db;
    }

    public String getData_view() {
        return data_view;
    }

    public void setData_view(String data_view) {
        this.data_view = data_view;
    }

    public String getLocal_compra() {
        return local_compra;
    }

    public void setLocal_compra(String local_compra) {
        this.local_compra = local_compra;
    }

    public double getTotal_compra() {
        return total_compra;
    }

    public void setTotal_compra(double total_compra) {
        this.total_compra = total_compra;
    }

    public String toString(){
        return "Local: " + getLocal_compra() + " - R$" + getTotal_compra() + " - " + getData_view();
    }
}
