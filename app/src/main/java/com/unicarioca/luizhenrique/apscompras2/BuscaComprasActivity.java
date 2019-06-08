package com.unicarioca.luizhenrique.apscompras2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

public class BuscaComprasActivity extends AppCompatActivity {

    private String dataInicial;
    private String dataFinal;
    private String dataInicialView;
    private String dataFinalView;
    private String local;

    private CalendarView cal_inicio;
    private CalendarView cal_fim;
    private EditText local_compra;
    private Button botao_buscar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_compras);

        cal_inicio = (CalendarView) findViewById(R.id.busca_data_inicial);
        cal_fim = (CalendarView) findViewById(R.id.busca_data_final);
        local_compra = (EditText) findViewById(R.id.busca_local);
        botao_buscar = (Button) findViewById(R.id.busca_buscar);


        cal_inicio.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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

                dataInicial = year + "-" + mes + "-" + dia;
                dataInicialView = dia + "/" + mes + "/" + year;
            }
        });

        cal_fim.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
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

                dataFinal = year + "-" + mes + "-" + dia;
                dataFinalView = dia + "/" + mes + "/" + year;
            }
        });

        botao_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                local = local_compra.getText().toString();

                Intent intentVaiResultado = new Intent(BuscaComprasActivity.this, ResultadoBuscaActivity.class);
                intentVaiResultado.putExtra("dataInicial", dataInicial);
                intentVaiResultado.putExtra("dataFinal", dataFinal);
                intentVaiResultado.putExtra("dataInicialView", dataInicialView);
                intentVaiResultado.putExtra("dataFinalView", dataFinalView);
                intentVaiResultado.putExtra("local", local);
                startActivity(intentVaiResultado);
            }
        });
    }
}
