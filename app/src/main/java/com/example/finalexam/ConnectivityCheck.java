package com.example.finalexam;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import java.util.Locale;

public class ConnectivityCheck extends BroadcastReceiver {
    private CameraManager cameraManager;
    private String cameraID;
    int delay_seconds;
    CountDownTimer mCountDownTimer;



    public ConnectivityCheck(int delay_seconds) {
        this.delay_seconds = delay_seconds;

    }
    public ConnectivityCheck()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        new FlashSetup().setupflash(context);

        if(new WIFiState().isConnected(context))
        {
            Toast.makeText(context, "WiFi is Connected", Toast.LENGTH_LONG).show();
            new FlashSetup().FlashOff(context);
        }else
        {
            new FlashSetup().FlashOn(context);
            Toast.makeText(context, "WiFi is not Connected", Toast.LENGTH_SHORT).show();

            if(delay_seconds > 0)
            {
                mCountDownTimer = new CountDownTimer(delay_seconds*1000,1000) {
                    @Override
                    public void onTick(long l) {

                            // Nothing To code here

                    }

                    @Override
                    public void onFinish() {
                        //Toast.makeText(context,"Flash off",Toast.LENGTH_LONG).show();
                        new FlashSetup().FlashOff(context);


                    }
                }.start();
            }
        }

    }




}