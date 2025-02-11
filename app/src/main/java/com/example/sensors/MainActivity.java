package com.example.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String rutaAudio;
    private boolean estaGravant;
    private boolean estaReproduint = false;
    private Button iniParaGravacio, reproduir, pausar, sortir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        rutaAudio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";
        sortir.setOnClickListener(v -> finish());

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
    private void reproduir() {
        if (!estaReproduint) {
            estaReproduint = true;
            mediaPlayer.start();
        }
    }

    private void pausar() {
        if (estaReproduint) {
            estaReproduint = false;
            mediaPlayer.stop();
        }
    }

    private void instanciarMediaplayer(){
        mediaPlayer = MediaPlayer.create(this, Uri.parse(rutaAudio));
    }
}
