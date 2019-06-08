package com.unicarioca.luizhenrique.apscompras2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.unicarioca.luizhenrique.apscompras2.dao.ComprasDAO;
import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;
import com.unicarioca.luizhenrique.apscompras2.modelo.Produto;

import java.util.List;


public class CadastroProdutosActivity extends AppCompatActivity {

    private TextView local_compra;
    private TextView data_compra;
    private ListView lista_produtos;
    private FloatingActionButton novo_produto;

    private String localCompra;
    private String dataDB;
    private String dataView;

    private String id_compra;

    private Compra compra;
    private ComprasDAO dao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        local_compra = (TextView) findViewById(R.id.txt_compra_info_local);
        data_compra = (TextView) findViewById(R.id.txt_compra_info_data);
        lista_produtos = (ListView) findViewById(R.id.lista_de_produtos);
        novo_produto = (FloatingActionButton) findViewById(R.id.cadastro_produto_add);
        compra = new Compra();
        dao = new ComprasDAO(this);

        Intent intent = getIntent();
        Compra compra1 = (Compra) intent.getSerializableExtra("compra");
        compra = compra1;

        localCompra = compra.getLocal_compra();
        dataDB = compra.getData_db();
        dataView = compra.getData_view();

        id_compra = dao.getIdDaCompra(localCompra, dataDB);

        novo_produto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastroProduto = new Intent(CadastroProdutosActivity.this, CadastroDadosProdutoActivity.class);
                intentCadastroProduto.putExtra("compra", compra);
                intentCadastroProduto.putExtra("idCompra", id_compra);
                startActivity(intentCadastroProduto);
            }
        });

        lista_produtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produto = (Produto) lista_produtos.getItemAtPosition(position);
                Intent intentCadastroProduto = new Intent(CadastroProdutosActivity.this, CadastroDadosProdutoActivity.class);
                intentCadastroProduto.putExtra("produto", produto);
                intentCadastroProduto.putExtra("compra", compra);
                intentCadastroProduto.putExtra("idCompra", id_compra);
                startActivity(intentCadastroProduto);
            }
        });

        registerForContextMenu(lista_produtos);
        dao.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaDadosCompra();
        carregaLista();
    }

    private void carregaLista() {
        List<Produto> produtos = dao.buscaListaProdutos(id_compra);
        dao.close();

        ArrayAdapter<Produto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtos);
        lista_produtos.setAdapter(adapter);

    }

    private void carregaDadosCompra(){
        local_compra.setText(localCompra);
        data_compra.setText(dataView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_produtos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String total = dao.somaProdutos(id_compra);
        compra.setId_compra(Long.valueOf(id_compra));
        compra.setTotal_compra(Double.valueOf(total));
        compra.setData_db(dataDB);
        compra.setData_view(dataView);
        compra.setLocal_compra(localCompra);

        dao.attTotalCompra(compra);
        dao.close();

        Intent intentVoltaListaCompras = new Intent(CadastroProdutosActivity.this, MainActivity.class);
        startActivity(intentVoltaListaCompras);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar Produto");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Produto produto = (Produto) lista_produtos.getItemAtPosition(info.position);

                dao.deletaProduto(produto);
                dao.close();

                Toast.makeText(CadastroProdutosActivity.this,
                        "Produto: " + produto.getNome_produto() + " deletado", Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        });
    }
}
