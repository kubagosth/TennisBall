package com.example.tennisball;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * @author Jakub
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private ImageView imageView;
    private TextView sensorPos;
    private TextView ballPos;
    private TextView screenSize;
    private List list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide Actionbar
        getSupportActionBar().hide();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorPos = findViewById(R.id.text);
        imageView = findViewById(R.id.imageView);
        ballPos = findViewById(R.id.textCurrentPos);
        screenSize = findViewById(R.id.screenSize);


        list = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (list.size() > 0)
        {
            sensorManager.registerListener(this, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        } else
        {
            Toast.makeText(getBaseContext(), "Error: No Accelerometer.", Toast.LENGTH_LONG).show();
        }

    }
    /**
     * Updates every time sensor values has changed
     * @param sensorEvent sensor change
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Get the sensor values
        float[] values = sensorEvent.values;
        sensorPos.setText("Sensor Position\nx: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);
        //Calculate movement
        float x = imageView.getWidth() - values[0]*(imageView.getWidth()/6);
        float y = imageView.getHeight() + values[1]*(imageView.getHeight()/3);

        //Get the Screen size
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        screenSize.setText("Screen Size\nh : " + height + "\nw : " + width);
        ballPos.setText("Current ball position\nx : " + x + "\ny : " + y);

        //Updates the X Y position of the image
        imageView.setX(x);
        imageView.setY(y);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

/**
 sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
 List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

 TextView textView = findViewById(R.id.text);
 for (Sensor sensor:deviceSensors)
 {
 textView.setText( textView.getText() + sensor.getName() + "\n");
 }
 */