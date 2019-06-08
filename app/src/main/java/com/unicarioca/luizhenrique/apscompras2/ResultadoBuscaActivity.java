package com.unicarioca.luizhenrique.apscompras2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.unicarioca.luizhenrique.apscompras2.dao.ComprasDAO;
import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;

import java.util.List;

public class ResultadoBuscaActivity extends AppCompatActivity {

    private TextView soma_total_compras;
    private ListView lista_resultado;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busca);

        soma_total_compras = (TextView) findViewById(R.id.resultado_total);
        lista_resultado = (ListView) findViewById(R.id.resultado_lista);

        Intent intent = getIntent();
        String dataInicial = intent.getStringExtra("dataInicial");
        String dataFinal = intent.getStringExtra("dataFinal");
        String dataInicialView = intent.getStringExtra("dataInicialView");
        String dataFinalView = intent.getStringExtra("dataFinalView");
        String local = intent.getStringExtra("local");

        ComprasDAO dao = new ComprasDAO(this);

        soma_total_compras.setText("Total gasto no "+local+" entre os dias "+dataInicialView+" e "+dataFinalView+": R$ "
                +dao.resultBuscaTotal(dataInicial, dataFinal, local));

       List<Compra> compras = dao.resultBuscaLista(dataInicial, dataFinal, local);

        dao.close();

        ArrayAdapter<Compra> adapter = new ArrayAdapter<Compra>(this,
                android.R.layout.simple_list_item_1, compras);
        lista_resultado.setAdapter(adapter);

        lista_resultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Compra compra = (Compra) lista_resultado.getItemAtPosition(position);
                Intent intentVaiListaProdutos = new Intent(ResultadoBuscaActivity.this, CadastroProdutosActivity.class);
                intentVaiListaProdutos.putExtra("compra", compra);
                startActivity(intentVaiListaProdutos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_resultado_busca, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intentVoltaPraMain = new Intent(ResultadoBuscaActivity.this,
                MainActivity.class);
        startActivity(intentVoltaPraMain);

        return super.onOptionsItemSelected(item);
    }
}
