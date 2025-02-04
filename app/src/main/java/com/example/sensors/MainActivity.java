package com.example.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor pressureSensor;
    private TextView textPantalla;
    private float Po = 1013.25f; // Presión al nivel del mar en hPa
    private float T = 288.15f; // Temperatura estándar en Kelvin
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
            //hace que vaya cambiando el valor
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sr) {
                    float P = sr.values[0];
                    h = T/0.0065*(1-(Math.pow(P/Po,1021.5)));
                    textPantalla.setText("Alçada: " + String.valueOf(h));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    //lo sobreesscribo porque sino peta
                }
            }, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No hay sensor de presión disponible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sr) {
        //hay que sobreescribirlo dos veces porque el AndroidStudio es retrasado
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}