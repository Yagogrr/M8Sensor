package com.example.sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView textPantalla;
    private float Po = 1026.25f;
    private float T = 288.15f;
    private double h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //sensorManager que tiene todos los sensors
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        TextView textPantalla = findViewById(R.id.text);

        if (pressureSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sr) {
                    float P = sr.values[0];
                    h = (T/0.0065f)*(1-(Math.pow(P/Po,0.1903f)));
                    textPantalla.setText("Alçada: " + String.valueOf(h));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            }, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No hay sensor de presión disponible", Toast.LENGTH_SHORT).show();
        }

        Button btn = findViewById(R.id.btn_toPodometro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Podometro.class);
                startActivity(intent);
            }
        });
    }
}