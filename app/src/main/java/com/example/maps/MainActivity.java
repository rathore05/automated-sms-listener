package com.example.maps;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button msg, gmsg;
    Receiver connectionReceiver;
    IntentFilter intentFilter;
    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionReceiver = new Receiver();
        intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String address = extras.getString("MessageNumber");
            String message = extras.getString("Message");
            String[] coords = message.split("\\s*,\\s*");
            lat = coords[0];
            lng = coords[1];

            TextView addressField = (TextView) findViewById(R.id.address);
            TextView messageField = (TextView) findViewById(R.id.message);
            addressField.setText("Message From : " + address);
            messageField.setText("Messsage : "+ lat + " "+lng);
        }

        msg = findViewById(R.id.btn_msg);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_msg_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lng + "&travelmode=driving";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectionReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(connectionReceiver);
    }
}
