package com.unicarioca.luizhenrique.apscompras2.modelo;

import java.io.Serializable;

public class Produto implements Serializable {

    private Long id_produto;
    private Long id_compra;
    private String nome_produto;
    private double qtd;
    private double valor_uni;
    private double total_produto;

    public Long getId_produto() {
        return id_produto;
    }

    public void setId_produto(Long id_produto) {
        this.id_produto = id_produto;
    }

    public Long getId_compra() {
        return id_compra;
    }

    public void setId_compra(Long id_compra) {
        this.id_compra = id_compra;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getValor_uni() {
        return valor_uni;
    }

    public void setValor_uni(double valor_uni) {
        this.valor_uni = valor_uni;
    }

    public double getTotal_produto() {
        return total_produto;
    }

    public void setTotal_produto(double total_produto) {
        this.total_produto = total_produto;
    }

    public String toString(){
        return getNome_produto() + " - Qtd.: " + getQtd() + " - R$" + getValor_uni() + " - Total: R$" + getTotal_produto();
    }
}
