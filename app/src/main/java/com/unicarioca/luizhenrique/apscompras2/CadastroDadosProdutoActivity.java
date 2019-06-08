package com.unicarioca.luizhenrique.apscompras2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.unicarioca.luizhenrique.apscompras2.dao.ComprasDAO;
import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;
import com.unicarioca.luizhenrique.apscompras2.modelo.Produto;

public class CadastroDadosProdutoActivity extends AppCompatActivity {

    private EditText nome_produto;
    private EditText qtd_produto;
    private EditText valor_produto;
    private Button btn_add_produto;

    private String id_compra;

    private Compra compra;
    private Produto produto;
    private ComprasDAO dao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dados_produto);

        nome_produto = (EditText) findViewById(R.id.cadastro_produto_nome);
        qtd_produto = (EditText) findViewById(R.id.cadastro_produto_qtd);
        valor_produto = (EditText) findViewById(R.id.cadastro_produto_valor);
        btn_add_produto = (Button) findViewById(R.id.btn_add_produto);
        produto = new Produto();
        dao = new ComprasDAO(this);

        Intent intent = getIntent();
        Produto produto1 = (Produto) intent.getSerializableExtra("produto");
        if(produto1 != null){
            nome_produto.setText(produto1.getNome_produto());
            qtd_produto.setText(String.valueOf(produto1.getQtd()));
            valor_produto.setText(String.valueOf(produto1.getValor_uni()));
            produto = produto1;
        }

        btn_add_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                compra = (Compra) intent.getSerializableExtra("compra");
                id_compra = intent.getStringExtra("idCompra");

                produto.setId_compra(Long.valueOf(id_compra));
                produto.setNome_produto(nome_produto.getText().toString());
                produto.setQtd(Double.valueOf(qtd_produto.getText().toString()));
                produto.setValor_uni(Double.valueOf(valor_produto.getText().toString()));
                produto.setTotal_produto(produto.getQtd() * produto.getValor_uni());

                if(produto.getId_produto() != null){
                    dao.alteraProduto(produto);
                }else {
                    dao.insereProduto(produto);
                }
                dao.close();

                Intent intentVoltaListaProdutos = new Intent(CadastroDadosProdutoActivity.this, CadastroProdutosActivity.class);
                intentVoltaListaProdutos.putExtra("compra", compra);
                startActivity(intentVoltaListaProdutos);
            }
        });

    }
}
