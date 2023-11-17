package edu.ncssm.anderson24a1.healthmonitoringapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecordHeartrateGUI extends AppCompatActivity {
    private TextView displayHeartRate;
    private String heartRateValue;
    private SensorManager manager;
    private Sensor heartRateSensor;
    private static final int PERMISSION_REQUEST_CODE = 20;

    private void setHeartRateValue(String rate){
        displayHeartRate.setText(rate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(this.getBaseContext(), android.Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED){
            manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            heartRateSensor = manager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
            SensorEventListener listener = new HeartRateListener();
            manager.registerListener(listener, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.BODY_SENSORS)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("to record your heart rate")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RecordHeartrateGUI.this, new String[]{android.Manifest.permission.BODY_SENSORS}, PERMISSION_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
            else{
                ActivityCompat.requestPermissions(RecordHeartrateGUI.this, new String[]{Manifest.permission.BODY_SENSORS}, PERMISSION_REQUEST_CODE);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_heartrate_gui);

        displayHeartRate = findViewById(R.id.ShowHeartRate);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new BackButtonHandler());

        Button recordButton = findViewById(R.id.RecordButton);
        recordButton.setOnClickListener(new RecordButtonHandler());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

                manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
                heartRateSensor = manager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
                SensorEventListener listener = new HeartRateListener();
                manager.registerListener(listener, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);

            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class HeartRateListener implements SensorEventListener{

        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.d("HEART","YAY");
            heartRateValue = Integer.toString(event.values.length > 0 ? (int) event.values[0] : 0);
            setHeartRateValue(heartRateValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    private class BackButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private class RecordButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            setHeartRateValue(heartRateValue);
        }
    }


}