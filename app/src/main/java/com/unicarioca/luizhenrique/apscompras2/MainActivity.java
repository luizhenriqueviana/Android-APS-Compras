package com.unicarioca.luizhenrique.apscompras2;

//165 - TÓPICOS AVANÇADOS EM DESENVOLVIMENTO DE SISTEMAS 2019/1
//Luiz Henrique Brum Viana

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unicarioca.luizhenrique.apscompras2.dao.ComprasDAO;
import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;
import com.unicarioca.luizhenrique.apscompras2.modelo.Produto;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista_compras;
    private FloatingActionButton nova_compra;
    private FloatingActionButton botao_busca;

    private ComprasDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista_compras = (ListView) findViewById(R.id.lista_de_compras);
        nova_compra =(FloatingActionButton) findViewById(R.id.btn_nova_compra);
        botao_busca = (FloatingActionButton) findViewById(R.id.btn_buscar_compras);
        dao = new ComprasDAO(this);


        nova_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddCompras = new Intent(MainActivity.this, CadastroCompraActivity.class);
                startActivity(intentAddCompras);
            }
        });

        botao_busca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBuscarCompras = new Intent(MainActivity.this, BuscaComprasActivity.class);
                startActivity(intentBuscarCompras);
            }
        });

        lista_compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Compra compra = (Compra) lista_compras.getItemAtPosition(position);
                Intent intentVaiListaProdutos = new Intent(MainActivity.this, CadastroProdutosActivity.class);
                intentVaiListaProdutos.putExtra("compra", compra);
                startActivity(intentVaiListaProdutos);
            }
        });

        registerForContextMenu(lista_compras);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        List<Compra> compras = dao.buscaListaCompras();
        dao.close();

        ArrayAdapter<Compra> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, compras);
        lista_compras.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar Compra");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Compra compra = (Compra) lista_compras.getItemAtPosition(info.position);

                dao.deletaCompra(compra);
                dao.close();

                Toast.makeText(MainActivity.this,
                        "Produto: " + compra.getLocal_compra() + " deletado", Toast.LENGTH_SHORT).show();
                carregaLista();
                return false;
            }
        });

    }
}
