package com.example.quinielarandom;

import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PlenoAl15Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pleno_al_15);

        // Obtener el número de columnas del intent
        int numeroColumnas = getIntent().getIntExtra("NUMERO_COLUMNAS", 0);
        boolean conElige8 = getIntent().getBooleanExtra("CON_ELIGE_8", false);

        // Encontrar la TableLayout en el layout
        TableLayout tableLayout = findViewById(R.id.tableLayoutPlenoAl15);

        // Añadir encabezado de columna
        TableRow headerRow = new TableRow(this);
        TextView emptyHeader = new TextView(this); // Espacio en blanco para el índice de fila
        headerRow.addView(emptyHeader);

        for (int j = 0; j < numeroColumnas; j++) {
            TextView headerTextView = new TextView(this);
            headerTextView.setText(String.valueOf(j + 1));
            headerTextView.setGravity(Gravity.CENTER);
            headerTextView.setPadding(8, 8, 8, 8);
            headerTextView.setTextColor(getColor(R.color.black));
            headerTextView.setTypeface(Typeface.DEFAULT_BOLD);
            headerRow.addView(headerTextView);
        }

        // Añadir la fila de encabezado a la tabla
        tableLayout.addView(headerRow);

        // Generar automáticamente 14 resultados para cada columna y añadirlos a la tabla
        for (int i = 0; i < 14; i++) {
            // Crear una nueva fila para cada resultado
            TableRow row = new TableRow(this);

            // Añadir TextView para el índice de fila
            TextView indexTextView = new TextView(this);
            indexTextView.setText(String.valueOf(i + 1));
            indexTextView.setGravity(Gravity.CENTER);
            indexTextView.setPadding(8, 8, 8, 8);
            indexTextView.setTextColor(getColor(R.color.black));
            indexTextView.setTypeface(Typeface.DEFAULT_BOLD);
            row.addView(indexTextView);

            // Generar resultados y añadir celdas a la fila
            for (int j = 0; j < numeroColumnas; j++) {
                String resultado = resultadoQuiniela();
                TextView textView = new TextView(this);
                textView.setText(resultado);
                textView.setGravity(Gravity.CENTER);
                textView.setPadding(8, 8, 8, 8);
                textView.setTextColor(getColor(R.color.black));
                row.addView(textView);
            }

            // Añadir la fila a la tabla
            tableLayout.addView(row);
        }

        // Añadir la fila "PLENO AL 15" al final
        TableRow plenoAl15Row = new TableRow(this);

        // Generar resultado y añadir celda a la fila "PLENO AL 15"
        String resultadoPlenoAl15 = "\nPLENO AL 15\n" + obtenerOpcionAleatoria() + "-" + obtenerOpcionAleatoria();
        TextView resultadoPlenoAl15TextView = new TextView(this);
        resultadoPlenoAl15TextView.setText(resultadoPlenoAl15);
        resultadoPlenoAl15TextView.setGravity(Gravity.CENTER);
        resultadoPlenoAl15TextView.setTextSize(20);
        resultadoPlenoAl15TextView.setPadding(8, 8, 8, 8);
        resultadoPlenoAl15TextView.setTextColor(getColor(R.color.black));

        plenoAl15Row.addView(resultadoPlenoAl15TextView);

        // Envolver la fila en un LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.addView(plenoAl15Row);
        linearLayout.setGravity(Gravity.CENTER);

        // Añadir el LinearLayout al TableLayout
        tableLayout.addView(linearLayout);



        if (conElige8) {
            TableRow elige8Row = new TableRow(this);
            TextView elige8TextView = new TextView(this);
            elige8TextView.setText("\nElige 8:");
            elige8TextView.setGravity(Gravity.CENTER);
            elige8TextView.setPadding(8, 8, 8, 8);
            elige8TextView.setTextColor(getColor(R.color.black));
            elige8TextView.setTextSize(20);
            elige8TextView.setTypeface(Typeface.DEFAULT_BOLD);
            elige8Row.addView(elige8TextView);

            // Crear un array con los números del 1 al 14
            ArrayList<Integer> numerosDisponibles = new ArrayList<>();
            for (int i = 1; i <= 14; i++) {
                numerosDisponibles.add(i);
            }

            // Array para almacenar los números seleccionados
            ArrayList<Integer> numerosSeleccionados = new ArrayList<>();

            // Seleccionar aleatoriamente 8 posiciones sin repetir
            Random random = new Random();
            for (int i = 0; i < 8; i++) {
                int indiceAleatorio = random.nextInt(numerosDisponibles.size());
                int numeroSeleccionado = numerosDisponibles.remove(indiceAleatorio);
                numerosSeleccionados.add(numeroSeleccionado);
            }
            // Ordenar los números seleccionados
            Collections.sort(numerosSeleccionados);

            // Mostrar los números en TextViews
            for (int numeroSeleccionado : numerosSeleccionados) {
                TextView numeroTextView = new TextView(this);
                numeroTextView.setText("\n" + String.valueOf(numeroSeleccionado));
                numeroTextView.setGravity(Gravity.CENTER);
                numeroTextView.setPadding(8, 8, 8, 8);
                numeroTextView.setTextSize(20);
                numeroTextView.setTextColor(getColor(R.color.black));
                elige8Row.addView(numeroTextView);
            }

            // Añadir la fila "Elige 8" al TableLayout
            tableLayout.addView(elige8Row);
        }

        // Encontrar el botón Cerrar en el layout
        Button btnCerrar = findViewById(R.id.btnCerrar);
        btnCerrar.setOnClickListener(view -> finish());
    }

    private String resultadoQuiniela() {
        Random random = new Random();
        int resultado = random.nextInt(3); // 0, 1 o 2

        String opcion;
        switch (resultado) {
            case 0:
                opcion = "1";
                break;
            case 1:
                opcion = "X";
                break;
            case 2:
                opcion = "2";
                break;
            default:
                opcion = "";
        }
        return opcion;
    }

    private String obtenerOpcionAleatoria() {
        Random random = new Random();
        int resultado = random.nextInt(4); // 0, 1, 2 o M

        switch (resultado) {
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "M";
            default:
                return "";
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicPlayerUtil.detenerMusica();
    }
}
