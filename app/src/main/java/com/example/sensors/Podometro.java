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

public class Podometro extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_podometro);
        TextView tv = findViewById(R.id.passos);
        Button btn = findViewById(R.id.btn_reiniciar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("0");
            }
        });

        Button btn2 = findViewById(R.id.btnToaltimetro);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Podometro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if (accelerometer != null) {
            sensorManager.registerListener(new SensorEventListener() {
                int steps = 0;
                boolean isStep = false;
                @Override
                public void onSensorChanged(SensorEvent sr) {
                    float y = sr.values[1];
                    float x = sr.values[2];

                    if ((y > 9.5 && !isStep)||(x > 1.5 && !isStep)) {
                        steps++;
                        isStep = true;
                    }
                    if ((y < 9.5)&&(x < 1.5)) {
                        isStep = false;
                    }

                    tv.setText("Pasos: " + String.valueOf(steps));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No hay acelerómetro disponible", Toast.LENGTH_SHORT).show();
        }

    }
}