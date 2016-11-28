package com.example.joseluis.p2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

import java.text.BreakIterator;

/**
 * Created by joseluis on 5/11/16.
 */
//http://www.jtech.ua.es/cursos/apuntes/moviles/daa2013/sesion04-apuntes.html

public class GiroDetector implements SensorEventListener {

    private static final float ROTATION_THRESHOLD = 90.f;
    private static final int ROTATION_WAIT_TIME_MS = 1000;//1000;
    private long mRotationTime = 0;


    private GiroDetector.OnGiroListener mListener;
    private int mGiroCount;

    public void setOnGiroListener(GiroDetector.OnGiroListener listener) {
        this.mListener = listener;
    }

    public interface OnGiroListener {
        public void onGiro(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        long now = System.currentTimeMillis();
        mGiroCount=0;
        if ((now - mRotationTime) > ROTATION_WAIT_TIME_MS) {
            mRotationTime = now;

            if (Math.abs(event.values[2]/SensorManager.GRAVITY_EARTH) > ROTATION_THRESHOLD) {
                mGiroCount++;
            }
            if (mGiroCount==2){
                mListener.onGiro(mGiroCount);
                mGiroCount=0;

            }
        }
    }

}


//0 hacia mi 1 rotandolo si mismo