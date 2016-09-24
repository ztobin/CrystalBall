package com.tobincorporated.betterball;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;


public class MainActivity extends AppCompatActivity {
    private TextView answerText;
    private ImageView ballImage;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private double acceleration;
    private double currentAcceleration;
    private double previousAcceleration;
    private MediaPlayer mediaPlayer;
    private AnimationDrawable ballAnimation;
    private  Animation predictionAnimation;
    long lastShake= System.currentTimeMillis();

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

            previousAcceleration = currentAcceleration;
            currentAcceleration= Math.sqrt(x*x+y*y+z*z);
            double  delta = currentAcceleration-previousAcceleration;
            acceleration = acceleration*0.9 +delta;

            if(acceleration> 15 && System.currentTimeMillis()-lastShake >2500) {
//                Toast toast = Toast.makeText(getApplication(), "Device has shaken", Toast.LENGTH_SHORT);
//                toast.show();
                lastShake= System.currentTimeMillis();
                mediaPlayer.start();
                ballAnimation.stop();
                ballAnimation.selectDrawable(0);
                ballAnimation.start();
                answerText.setText(Predictions.get().getAnswer());
                answerText.startAnimation(predictionAnimation);


            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.0;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        previousAcceleration = SensorManager.GRAVITY_EARTH;

        mediaPlayer = MediaPlayer.create(this , R.raw.crystal_ball);

        predictionAnimation = AnimationUtils.loadAnimation(this, R.anim.prediction_animation);


//        ballImage = (ImageView)findViewById(R.id.ballImage);
//        ballImage.setImageResource(R.drawable.ball01);

        ballImage = (ImageView)findViewById(R.id.ballImage);
        ballImage.setImageResource(R.drawable.ball_animation);
        ballAnimation = (AnimationDrawable) ballImage.getDrawable();

        answerText = (TextView)findViewById(R.id.answerText);
        answerText.setText("Shake me. Go ahead.");
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }


}
