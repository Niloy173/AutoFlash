package com.example.finalexam;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class WifiActivity extends AppCompatActivity {

    private CameraManager cameraManager;
    private  String cameraID;
    SeekBar seekBar;
    TextView seconds,time_count,timer_zero;
    int minutes,second;
    String format;
    SwitchCompat switchCompat;
    BroadcastReceiver broadcastReceiver;

    private static Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        seekBar = findViewById(R.id.seekbar_id);
        seconds = findViewById(R.id.seconds_count);
        time_count = findViewById(R.id.timer_watch);
        switchCompat = findViewById(R.id.switchbtn);



        seekBar.setProgress(0);



        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!switchCompat.isChecked())
                {
                    unregisteredNetwork();
                    findViewById(R.id.seconds_count).setVisibility(View.VISIBLE);
                    findViewById(R.id.seekbar_id).setVisibility(View.VISIBLE);
                    findViewById(R.id.timer_watch).setVisibility(View.GONE);
                    findViewById(R.id.timer_picture).setVisibility(View.GONE);
                    time_count.setVisibility(View.GONE);
                    new FlashSetup().FlashOff(WifiActivity.this);


                }else
                {

                    if(new WIFiState().isConnected(WifiActivity.this))
                    {
                       // Toast.makeText(WifiActivity.this, "WiFi Mode Activated", Toast.LENGTH_SHORT).show();


                        broadcastReceiver = new ConnectivityCheck(seekBar.getProgress());
                        registeredNetworkBrodcast();





                        if(seekBar.getProgress()==0) {


                            findViewById(R.id.timer_picture).setVisibility(View.GONE);
                            time_count.setVisibility(View.GONE);

                            AlertDialog.Builder builder = new AlertDialog.Builder(WifiActivity.this);
                            builder.setTitle("Important");
                            builder.setMessage("\nWhen WiFi is Disconnected Flash will remain on Until you turn it off by Yourself\n");
                            builder.setCancelable(false);
                            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    findViewById(R.id.seekbar_id).setVisibility(View.GONE);
                                    findViewById(R.id.seconds_count).setVisibility(View.GONE);
                                    dialogInterface.dismiss();

                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                            dialog.show();
                        }else
                        {
                            updateTime();
                            findViewById(R.id.seconds_count).setVisibility(View.GONE);
                            findViewById(R.id.seekbar_id).setVisibility(View.GONE);
                            findViewById(R.id.timer_picture).setVisibility(View.VISIBLE);
                            time_count.setVisibility(View.VISIBLE);
                        }





                    }else
                    {
                        dialogBox();
                        switchCompat.setChecked(false);
                        findViewById(R.id.seekbar_id).setVisibility(View.VISIBLE);
                        findViewById(R.id.seconds_count).setVisibility(view.VISIBLE);

                    }
                }
            }
        });


        seekBar.setOnSeekBarChangeListener(

                new SeekBar.OnSeekBarChangeListener() {
                int progress = seekBar.getProgress();



            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                progress = progressValue;





            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                seconds.setText("The torch will remain on for "+progress+" seconds after the WiFi leaves");
                updateTime();
                if(progress ==0)
                {

                    seconds.setText("Until I Turn OFF");

                }else {

                    // Nohing to write here
                }

            }
        });


    }


    private void updateTime() {

        minutes = ((seekBar.getProgress() * 1000) / 1000) / 60;
        second = ((seekBar.getProgress() * 1000)/1000) % 60;

        format = String.format(Locale.ENGLISH,"%02d : %02d",minutes,second);

        time_count.setText(format);
    }

    protected void dialogBox()
    {
        new AlertDialog.Builder(WifiActivity.this)
                .setTitle("Important")
                .setMessage("\nPlease Turn On Your WiFi First\n")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }



    protected  void registeredNetworkBrodcast(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        //}
    }

    protected void unregisteredNetwork(){
        try {
            unregisterReceiver(broadcastReceiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        unregisteredNetwork();
    }




}