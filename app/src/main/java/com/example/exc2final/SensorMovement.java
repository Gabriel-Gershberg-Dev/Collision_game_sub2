package com.example.exc2final;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.lang.Math;

public class SensorMovement {
    public interface CallBack_steps {



        void moveLeft();
        void moveRight();
    }
    private CallBack_steps callBack_steps;
    private SensorManager mSensorManager;
    private Sensor sensor;
    private  float previousX=0;
    private  float previousY=0;

    public SensorMovement(Context context, CallBack_steps callBack_steps) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBack_steps = callBack_steps;

    }
    /**
     * register to the sensors
     */
    public void start() {
        mSensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * unregister to the sensors
     */
    public void stop() {
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            registerMovement(x,y);




        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void registerMovement (float x, float y){
        Log.d( "movement", "x is" +(x));
        if( (x-previousX)>=1.5) {
            if (callBack_steps != null) {
                callBack_steps.moveLeft();
                previousX=x;
            }
        }
        else if ((x-previousX)<=-1.5){
            if (callBack_steps != null) {
                callBack_steps.moveRight();
                previousX=x;
            }
        }
    }


}
