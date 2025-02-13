package com.example.sensors;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String rutaAudio;
    private boolean estaGravant = false;
    private boolean estaReproduint = false;
    private Button iniParaGravacio, reproduirParar, sortir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Asegúrate de tener el layout correctamente asignado

        // Se utiliza el directorio propio de la app para almacenar el audio
        rutaAudio = getExternalFilesDir(null).getAbsolutePath() + "/audio.3gp";

        iniParaGravacio = findViewById(R.id.btn_inici_para);
        reproduirParar = findViewById(R.id.btn_reproduir);
        sortir = findViewById(R.id.btn_sortir);

        // Acción para el botón de salir
        sortir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        // Acción para el botón de iniciar/parar grabación
        iniParaGravacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estaGravant) {
                    stopRecording();
                    iniParaGravacio.setText("Iniciar Gravació");
                } else {
                    comencarGravacio();
                    iniParaGravacio.setText("Parar Gravació");
                }
            }
        });

        // Acción para el botón de reproducción/pausa
        reproduirParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    instanciarMediaplayer();
                }
                if (estaReproduint) {
                    pausar();
                    reproduirParar.setText("Reproduir");
                } else {
                    reproduir();
                    reproduirParar.setText("Pausar");
                }
            }
        });
    }

    private void comencarGravacio() {
        if (estaGravant) {
            Toast.makeText(this, "Ja estàs gravant!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(rutaAudio);
            mediaRecorder.prepare();
            mediaRecorder.start();
            estaGravant = true;
            Toast.makeText(this, "Gravació iniciada!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error en iniciar la gravació!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (estaGravant) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            estaGravant = false;
            Toast.makeText(this, "Gravació aturada.", Toast.LENGTH_SHORT).show();
        }
    }

    private void instanciarMediaplayer() {
        mediaPlayer = MediaPlayer.create(this, Uri.parse(rutaAudio));
    }

    private void reproduir() {
        if (!estaReproduint) {
            estaReproduint = true;
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    estaReproduint = false;
                    reproduirParar.setText("Reproduir");
                }
            });
        }
    }

    private void pausar() {
        if (estaReproduint) {
            estaReproduint = false;
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
