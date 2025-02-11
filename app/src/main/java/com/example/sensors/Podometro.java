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
    private SensorManager sensorManager;
    private Sensor pressureSensor;

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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final int[] steps = {0};

        if (pressureSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                boolean isStep = false;
                @Override
                public void onSensorChanged(SensorEvent sr) {
                    float y = sr.values[1];
                    float x = sr.values[0];

                    if ((y > 9.5 && !isStep)||(x > 1.5 && !isStep)) {
                        steps[0]++;
                        isStep = true;
                    }
                    if ((y < 9.5)&&(x < 1.5)) {
                        isStep = false;
                    }

                    tv.setText(String.valueOf(steps[0]));
                    if(steps[0] ==100){
                        Toast.makeText(Podometro.this,"Has arribat a 100 passos. Cop a cop, pas a pas, assalt a assetjament",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            }, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "No hi ha accelerometre disponible", Toast.LENGTH_SHORT).show();
        }

        Button buttonReset = findViewById(R.id.btn_reiniciar);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                steps[0] = 0;
                tv.setText(String.valueOf(steps[0]));
            }
        });


    }
}