package com.example.finalexam;

import android.content.Context;
import android.hardware.camera2.CameraManager;

public class FlashSetup {
    private CameraManager cameraManager;
    private String cameraID;

    public void setupflash(Context context){
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraID = cameraManager.getCameraIdList()[0]; // 0 is for back camera and 1 is for front camera
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FlashOn(Context context)
    {
        setupflash(context);

        try {
            cameraManager.setTorchMode(cameraID, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void FlashOff(Context context)
    {
        setupflash(context);
        try {
            cameraManager.setTorchMode(cameraID, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
