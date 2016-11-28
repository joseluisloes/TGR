package com.example.joseluis.p2;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

/**
 * Created by joseluis on 5/11/16.
 */
//http://www.jtech.ua.es/cursos/apuntes/moviles/daa2013/sesion04-apuntes.html

public class DownDetector implements SensorEventListener {


    private static final float ROTATION_THRESHOLD = 80f;
    private static final float ROTATION_THRESHOLD1 = 100f;
    private static final int ROTATION_WAIT_TIME_MS = 2000;
    private long mRotationTime = 0;


    private com.example.joseluis.p2.GiroDetector.OnGiroListener mListener;
    private int mGiroCount;

    public void setOnGiroListener(com.example.joseluis.p2.GiroDetector.OnGiroListener listener) {
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

        if ((now - mRotationTime) > ROTATION_WAIT_TIME_MS) {
            mRotationTime = now;

            if ((event.values[1] > ROTATION_THRESHOLD/SensorManager.GRAVITY_EARTH)&&(event.values[1] < ROTATION_THRESHOLD1/SensorManager.GRAVITY_EARTH)) {
                mGiroCount++;
                mListener.onGiro(mGiroCount);
            }
            }
        }

    }


//0 hacia mi 1 rotandolo si mismo
