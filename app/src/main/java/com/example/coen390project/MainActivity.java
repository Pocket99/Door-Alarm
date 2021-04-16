package com.example.coen390project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    TextView frontDoorStatus;
    TextView backDoorStatus;
    TextView windowStatus;
    TextView frontDoorAlertTextView;
    TextView backDoorAlertTextView;
    TextView windowAlertTextView;
    Button frontDoorButton;
    Button backDoorButton;
    Button windowButton;
    Button redButton;
    Button blueButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference frontDoorStatusRef = database.getReference().child("FrontDoor").child("Door");
    DatabaseReference backDoorStatusRef = database.getReference().child("BackDoor").child("Door");
    DatabaseReference windowStatusRef = database.getReference().child("Window").child("Door");
    DatabaseReference frontDoorAlertRef = database.getReference().child("FrontDoor").child("AlertMode");
    DatabaseReference backDoorAlertRef = database.getReference().child("BackDoor").child("AlertMode");
    DatabaseReference windowAlertRef = database.getReference().child("Window").child("AlertMode");
    SharedPreferenceHelper sharedPreferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceHelper= new SharedPreferenceHelper(MainActivity.this);
        getTheme().applyStyle(sharedPreferenceHelper.getTheme(),true);
        setContentView(R.layout.activity_main);
        setupUI();
        setFrontDoorButton();
        setBackDoorButton();
        setWindowButton();
    }

    @Override
    protected void onStart() {
        super.onStart();


        Toast.makeText(MainActivity.this, "firebase connection success", Toast.LENGTH_LONG).show();
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.saveRedTheme(R.style.OverlayThemeRed);
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.saveBlueTheme(R.style.OverlayThemeBlue);
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
        System.out.println("database.getReference().child(\"FrontDoor\").getKey() ="+database.getReference().child("FrontDoor").getKey());
        //Door reference
        frontDoorStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                frontDoorStatus.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backDoorStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                backDoorStatus.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        windowStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                windowStatus.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        frontDoorAlertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    frontDoorAlertTextView.setText("Alert Mode : ON");
                }else{
                    frontDoorAlertTextView.setText("Alert Mode : OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backDoorAlertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    backDoorAlertTextView.setText("Alert Mode : ON");
                }else{
                    backDoorAlertTextView.setText("Alert Mode : OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        windowAlertRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    windowAlertTextView.setText("Alert Mode : ON");
                }else{
                    windowAlertTextView.setText("Alert Mode : OFF");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void setupUI() {
        frontDoorStatus = findViewById(R.id.frontDoorStatus);
        backDoorStatus = findViewById(R.id.backDoorStatus);
        windowStatus = findViewById(R.id.windowStatus);
        frontDoorButton = findViewById(R.id.frontDoorButton);
        backDoorButton = findViewById(R.id.backDoorButton);
        windowButton = findViewById(R.id.windowButton);
        frontDoorAlertTextView = findViewById(R.id.frontDoorAlertTextView);
        backDoorAlertTextView = findViewById(R.id.backDoorAlertTextView);
        windowAlertTextView = findViewById(R.id.windowAlertTextView);
        redButton = findViewById(R.id.redButton);
        blueButton = findViewById(R.id.blueButton);
    }

    private String createNotificationChannel(String channelID, String channelNAME, int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }



    public void setFrontDoorButton(){
        frontDoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModeSwitch.class);
                intent.putExtra("DoorType",database.getReference().child("FrontDoor").getKey());
                startActivity(intent);
            }
        });

    }
    public void setBackDoorButton(){
        backDoorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModeSwitch.class);
                intent.putExtra("DoorType",database.getReference().child("BackDoor").getKey());
                startActivity(intent);
            }
        });

    }
    public void setWindowButton(){
        windowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModeSwitch.class);
                intent.putExtra("DoorType",database.getReference().child("Window").getKey());
                startActivity(intent);
            }
        });

    }
}