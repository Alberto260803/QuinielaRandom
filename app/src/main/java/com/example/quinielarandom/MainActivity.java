package com.example.quinielarandom;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnGenerate;
    private TextView tvResult;
    private ImageButton btnSilenciar;
    private Button btnPlenoal15;
    private boolean isChangingActivity = false; // Variable para controlar si estás cambiando de actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGenerate = findViewById(R.id.btnGenerate);
        tvResult = findViewById(R.id.tvResult);
        btnSilenciar = findViewById(R.id.btnSilenciar);
        btnPlenoal15 = findViewById(R.id.btnPlenoAl15);

        MusicPlayerUtil.iniciarMusica(this);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarQuiniela();
            }
        });

        btnSilenciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSilenciar();
            }
        });

        btnPlenoal15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogPlenoAl15();
            }
        });
    }

    private void generarQuiniela() {
        tvResult.setText(resultadoQuiniela());
    }

    private String resultadoQuiniela(){
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

    private void toggleSilenciar() {
        if (MusicPlayerUtil.estaSonando()) {
            MusicPlayerUtil.detenerMusica(); // Pausar la música si está reproduciéndose
            btnSilenciar.setImageResource(R.drawable.nomusica);
        } else {
            MusicPlayerUtil.resumirMusica(); // Reanudar la reproducción si está pausada
            btnSilenciar.setImageResource(R.drawable.musica);
        }
    }

    private void mostrarDialogPlenoAl15() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_numero_columnas, null);

        // Crear el Dialog y establecer el diseño personalizado
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        CheckBox checkBoxElige8 = dialogView.findViewById(R.id.checkBoxElige8);


        // Obtener las referencias de las vistas dentro del diálogo
        final EditText etNumeroColumnas = dialogView.findViewById(R.id.etNumeroColumnas);
        Button btnAceptar = dialogView.findViewById(R.id.btnAceptar);
        TextView tvError = dialogView.findViewById(R.id.tvError);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el número de columnas ingresado por el usuario
                String numeroColumnasStr = etNumeroColumnas.getText().toString();

                if (!numeroColumnasStr.isEmpty()) {
                    int numeroColumnas = Integer.parseInt(numeroColumnasStr);

                    if (numeroColumnas < 15) {
                        // Mostrar la información en una nueva actividad o en un Dialog, como prefieras
                        mostrarInformacionPlenoAl15(numeroColumnas, checkBoxElige8.isChecked());

                        // Cerrar el Dialog
                        dialog.dismiss();
                    } else {
                        tvError.setVisibility(View.VISIBLE);
                        tvError.setText("El número no puede ser mayor a 15.");
                    }
                } else {
                    // Mostrar el mensaje de error
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Por favor, ingrese un valor válido");
                }
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Mostrar el diálogo
        dialog.show();
    }


    private void mostrarInformacionPlenoAl15(int numeroColumnas, boolean conElige8) {
        isChangingActivity = true;
        Intent intent = new Intent(MainActivity.this, PlenoAl15Activity.class);
        intent.putExtra("NUMERO_COLUMNAS", numeroColumnas);
        intent.putExtra("CON_ELIGE_8", conElige8);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicPlayerUtil.resumirMusica();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isChangingActivity) {
            MusicPlayerUtil.detenerMusica();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayerUtil.liberarRecursos();
    }
}