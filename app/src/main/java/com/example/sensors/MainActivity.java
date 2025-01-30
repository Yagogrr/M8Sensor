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

    private double P;
    private final double T = 288.15;
    private final double Po = 1013.25;
    private double h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        TextView textPantalla = findViewById(R.id.text);
        if (pressureSensor != null) {
            calculAlcada();
            textPantalla.setText(String.valueOf(h));
        } else {
            Toast.makeText(this, "GAYGAYGAYGAYGAY", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent sr){
        float preesureValue = sr.values[0];
        this.P = preesureValue;
    }

    @Override
    public void onAccuracyChanged(Sensor sr, int a){
        return;
    }
    public void calculAlcada(){
        h = T/0.0065*(1-(Math.pow(P/Po,0.1903)));
    }

}