package com.unicarioca.luizhenrique.apscompras2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.unicarioca.luizhenrique.apscompras2.dao.ComprasDAO;
import com.unicarioca.luizhenrique.apscompras2.modelo.Compra;

public class CadastroCompraActivity extends AppCompatActivity {

    private CalendarView calendario;
    private EditText local_compra;
    private Button cadastrar_produtos;

    private Compra compra;
    private ComprasDAO dao;

    private String dataDB;
    private String dataView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_compra);

        calendario = (CalendarView) findViewById(R.id.cadastro_compra_calendario);
        local_compra = (EditText) findViewById(R.id.cadastro_compra_local);
        cadastrar_produtos = (Button) findViewById(R.id.cadastro_compra_add);
        compra = new Compra();
        dao = new ComprasDAO(this);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView Calendarview, int year, int month, int dayOfMonth) {
                String dia;
                String mes;

                if(dayOfMonth < 10){
                    dia = "0"+dayOfMonth;
                }else{
                    dia = String.valueOf(dayOfMonth);
                }

                if(month < 9){
                    mes = "0"+(month+1);
                }else{
                    mes = String.valueOf(month+1);
                }

                dataDB = year + "-" + mes + "-" + dia;
                dataView = dia + "/" + mes + "/" + year;
            }
        });

        cadastrar_produtos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compra.setData_db(dataDB);
                compra.setData_view(dataView);
                compra.setLocal_compra(local_compra.getText().toString());

                dao.insereCompra(compra);

                Intent intentAddProdutos = new Intent(CadastroCompraActivity.this, CadastroProdutosActivity.class);
                intentAddProdutos.putExtra("compra", compra);
                startActivity(intentAddProdutos);
            }
        });
        dao.close();
    }
}
