package telesketch.lmsierra.com.telesketch.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.lang.ref.WeakReference;

public class ShakeSensorManager{

    private static final String TAG = "ShakeSensorManager";
    private static final float DEFAULT_SHAKE_THRESHOLD = 3f;

    private WeakReference<Context> context;

    private float xAccel;
    private float yAccel;
    private float zAccel;

    private float xPreviousAccel;
    private float yPreviousAccel;
    private float zPreviousAccel;

    private boolean firstUpdate = true;
    private boolean shakeInitiated = false;

    private float shakeThreshold;

    private ShakeSensorListener shakeSensorListener;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            updateSensorParameters(event.values[0], event.values[1], event.values[2]);

            if(!shakeInitiated && isAccelerationChanged()){
                shakeInitiated = true;
            }else if(shakeInitiated && isAccelerationChanged()){

                if(shakeSensorListener != null){
                    shakeSensorListener.onShake();
                }

            }else if(shakeInitiated && !isAccelerationChanged()){
                shakeInitiated = false;
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public ShakeSensorManager(Context context){
        this(context, DEFAULT_SHAKE_THRESHOLD);
    }

    public ShakeSensorManager(Context context, float shakeThreshold){
        Log.d(TAG, "Init ShakeSensorManager with shake threshold: " + shakeThreshold);
        this.context = new WeakReference<>(context);
        this.shakeThreshold = shakeThreshold;

        configSensorManager();
    }

    private void configSensorManager() {
        Log.d(TAG, "Initing SensorManager");
        SensorManager sensorManager = (SensorManager) this.context.get().getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void updateSensorParameters(float xAccel, float yAccel, float zAccel){

        if(firstUpdate){
            xPreviousAccel = xAccel;
            yPreviousAccel = yAccel;
            zPreviousAccel = zAccel;
            firstUpdate = false;
        }else{
            xPreviousAccel = this.xAccel;
            yPreviousAccel = this.yAccel;
            zPreviousAccel = this.zAccel;
        }

        this.xAccel = xAccel;
        this.yAccel = yAccel;
        this.zAccel = zAccel;
    }

    private boolean isAccelerationChanged(){

        float deltaX = Math.abs(xPreviousAccel - xAccel);
        float deltaY = Math.abs(yPreviousAccel - yAccel);
        float deltaZ = Math.abs(zPreviousAccel - zAccel);

        return  (deltaX > shakeThreshold && deltaY > shakeThreshold) ||
                (deltaX > shakeThreshold && deltaZ > shakeThreshold) ||
                (deltaY > shakeThreshold && deltaZ > shakeThreshold);
    }

    public void setShakeSensorListener(ShakeSensorListener shakeSensorListener) {
        this.shakeSensorListener = shakeSensorListener;
    }

    public interface ShakeSensorListener{
        void onShake();
    }
}
