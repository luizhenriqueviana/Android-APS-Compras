package com.unicarioca.luizhenrique.apscompras2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;
import com.unicarioca.luizhenrique.apscompras2.modelo.Produto;

import java.util.ArrayList;
import java.util.List;

public class ComprasDAO extends SQLiteOpenHelper {

    public ComprasDAO(Context context){
        super(context, "Lista_Compras", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Compra (id_compra INTEGER PRIMARY KEY, data_db DATE NOT NULL, local_compra TEXT NOT NULL, total_compra REAL, data_view TEXT); ";
        String sql2 ="CREATE TABLE Produto (id_produto INTEGER PRIMARY KEY, id_compra INTEGER NOT NULL, nome_produto TEXT NOT NULL, qtd REAL NOT NULL, valor_uni REAL NOT NULL, total_produto REAL, FOREIGN KEY(id_compra) REFERENCES Compra(id_compra));";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Compra";
        String sql2 = "DROP TABLE IF EXISTS Produto";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    public void insereCompra(Compra compra){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosCompra(compra);

        db.insert("Compra", null, dados);
    }

    public void attTotalCompra(Compra compra){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosCompra(compra);

        String[] params ={compra.getId_compra().toString()};
        db.update("Compra", dados, "id_compra = ?", params);

    }


    @NonNull
    private ContentValues pegaDadosCompra(Compra compra){
        ContentValues dados = new ContentValues();
        dados.put("data_db", compra.getData_db());
        dados.put("local_compra", compra.getLocal_compra());
        dados.put("total_compra", compra.getTotal_compra());
        dados.put("data_view", compra.getData_view());
        return dados;
    }

    public void insereProduto(Produto produto){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosProduto(produto);

        db.insert("Produto", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosProduto(Produto produto){
        ContentValues dados = new ContentValues();
        dados.put("id_compra", produto.getId_compra());
        dados.put("nome_produto", produto.getNome_produto());
        dados.put("qtd", produto.getQtd());
        dados.put("valor_uni", produto.getValor_uni());
        dados.put("total_produto", produto.getTotal_produto());
        return dados;
    }

    public String getIdDaCompra(String local, String data){
        String idCompra = null;

        String sql = "SELECT id_compra FROM Compra WHERE local_compra = '"+local+"' AND data_db = '"+data+"';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            idCompra = c.getString(c.getColumnIndex("id_compra"));
        }
        c.close();
        return idCompra;
    }

    public List<Produto> buscaListaProdutos(String id){
        String sql = "SELECT * FROM Produto WHERE id_compra = '"+id+"';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Produto> produtos = new ArrayList<>();
        while (c.moveToNext()){
            Produto produto = new Produto();
            produto.setId_produto(c.getLong(c.getColumnIndex("id_produto")));
            produto.setId_compra(c.getLong(c.getColumnIndex("id_compra")));
            produto.setNome_produto(c.getString(c.getColumnIndex("nome_produto")));
            produto.setQtd(c.getDouble(c.getColumnIndex("qtd")));
            produto.setValor_uni(c.getDouble(c.getColumnIndex("valor_uni")));
            produto.setTotal_produto(c.getDouble(c.getColumnIndex("total_produto")));

            produtos.add(produto);

        }
        c.close();
        return produtos;
    }

    public String somaProdutos (String id){
        String soma = null;

        String sql = "SELECT SUM(total_produto) as Soma FROM Produto WHERE id_compra = '"+id+"';";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            soma = String.valueOf(c.getDouble(c.getColumnIndex("Soma")));
        }
        return soma;
    }

    public List<Compra> buscaListaCompras(){
        String sql = "SELECT * FROM Compra;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Compra> compras = new ArrayList<>();
        while (c.moveToNext()){
            Compra compra = new Compra();
            compra.setId_compra(c.getLong(c.getColumnIndex("id_compra")));
            compra.setData_db(c.getString(c.getColumnIndex("data_db")));
            compra.setLocal_compra(c.getString(c.getColumnIndex("local_compra")));
            compra.setTotal_compra(c.getDouble(c.getColumnIndex("total_compra")));
            compra.setData_view(c.getString(c.getColumnIndex("data_view")));

            compras.add(compra);
        }
        c.close();
        return compras;
    }

    public void alteraProduto(Produto produto){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosProduto(produto);

        String[] params ={produto.getId_produto().toString()};
        db.update("Produto", dados, "id_produto = ?", params);
    }

    public void deletaProduto(Produto produto){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {produto.getId_produto().toString()};
        db.delete("Produto", "id_produto = ?", params);
    }

    public void deletaCompra(Compra compra){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {compra.getId_compra().toString()};
        db.delete("Produto", "id_compra = ?", params);
        db.delete("Compra", "id_compra = ?", params);
    }

    public List<Compra> resultBuscaLista(String dataInicio, String dataFinal, String local){

        String sql = "SELECT * FROM Compra WHERE (local_compra = '" + local + "') AND (data_db BETWEEN '" + dataInicio + "' AND '" + dataFinal + "');";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);

        List<Compra> compras = new ArrayList<>();
        while (c.moveToNext()){
            Compra compra = new Compra();
            compra.setId_compra(c.getLong(c.getColumnIndex("id_compra")));
            compra.setData_db(c.getString(c.getColumnIndex("data_db")));
            compra.setLocal_compra(c.getString(c.getColumnIndex("local_compra")));
            compra.setTotal_compra(c.getDouble(c.getColumnIndex("total_compra")));
            compra.setData_view(c.getString(c.getColumnIndex("data_view")));

            compras.add(compra);
        }
        c.close();
        return compras;
    }

    public String resultBuscaTotal(String dataInicio, String dataFinal, String local){
        String somaTotal = null;

        String sql = "SELECT SUM(total_compra) as Total FROM Compra WHERE (local_compra = '" + local + "') AND (data_db BETWEEN '" + dataInicio + "' AND '" + dataFinal + "');";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()){
            somaTotal = String.valueOf(c.getDouble(c.getColumnIndex("Total")));
        }
        c.close();
        return somaTotal;
    }
}
