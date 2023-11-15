package edu.ncssm.anderson24a1.healthmonitoringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button userInfo = findViewById(R.id.info);
        userInfo.setOnClickListener(new InfoButtonHandler());

        Button recordHeartrate = findViewById(R.id.record);
        recordHeartrate.setOnClickListener(new RecordButtonHandler());

        Button activityLog = findViewById(R.id.log);
        activityLog.setOnClickListener(new LogButtonHandler());

    }

    private class InfoButtonHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Intent userInfo = new Intent(getBaseContext(), UserInfoGUI.class);
            startActivity(userInfo);

        }
    }

    private class RecordButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent recordHeartrate = new Intent(getBaseContext(), RecordHeartrateGUI.class);
            startActivity(recordHeartrate);

        }
    }

    private class LogButtonHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
}