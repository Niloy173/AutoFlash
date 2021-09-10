package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textView;
     Float ChangedValue;
    private Sensor lightsensor;
    BroadcastReceiver broadcastReceiver;

    TextView flashOn,flashOff,head,note;
    ImageView flash_image;
    //TextView val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_censor);

        Toast.makeText(this, "Sensor Mode Activated", Toast.LENGTH_SHORT).show();

        flashOn = findViewById(R.id.on_mode);
        flashOff = findViewById(R.id.off_mode);
        flash_image = findViewById(R.id.flashlight);
        head = findViewById(R.id.Head);
        note = findViewById(R.id.Note);
        //val = findViewById(R.id.value);// for getting the value for light sensor

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        new FlashSetup().setupflash(this);

        //Now check sensor in the device

        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!=null)
        {
            lightsensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        else
        {
            Toast.makeText(this, "No Light Sensor Found in the device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //value reading

        ChangedValue = sensorEvent.values[0];
       // val.setText(String.valueOf(ChangedValue));


        //broadcastReceiver = new SensorCheck.MyReceiver();



        if(ChangedValue < 0.1)
        {
            flashOn.setVisibility(View.VISIBLE);
            flashOff.setVisibility(View.GONE);


            findViewById(R.id.layout_censor).setBackgroundColor(Color.WHITE);
            head.setTextColor(Color.BLACK);
            note.setTextColor(Color.BLACK);
            flashOn.setTextColor(Color.BLACK);


           flash_image.setImageResource(R.drawable.lights_on);


            new FlashSetup().FlashOn(this);


        }else {

            flashOn.setVisibility(View.GONE);
            flashOff.setVisibility(View.VISIBLE);

            findViewById(R.id.layout_censor).setBackgroundColor(Color.BLACK);
            head.setTextColor(Color.WHITE);
            note.setTextColor(Color.WHITE);
            flashOff.setTextColor(Color.WHITE);

            int image = getIntent().getIntExtra("image",R.drawable.ligts_off);
            flash_image.setImageResource(image);




            new FlashSetup().FlashOff(this);
        }




    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //sensor registration
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,lightsensor,sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //sensorManager.unregisterListener(this);

    }






}