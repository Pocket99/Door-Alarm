package com.example.coen390project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class ModeSwitch extends AppCompatActivity {
    Switch alertModeSwitch;
    Switch nightModeSwitch;
    TextView startTimeHour;
    TextView startTimeMinute;
    TextView endTimeHour;
    TextView endTimeMinute;
    TextView startTimeTextView;
    TextView endTimeTextView;
    TextView colon;
    TextView colon2;
    Button startButton;
    private Intent intent;
    private Bundle bundle;
    private String doorType;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    DatabaseReference alertModeRef;
    DatabaseReference nightModeRef;
    DatabaseReference doorRef;
    TimeCounter timeCounter;


    private IntentFilter intentFilter;

    private TimeChangeReceiver timeChangeReceiver;

    int startHour;
    int startMinute;
    int endHour;
    int endMinute;
    Calendar calendar ;
    protected SharedPreferenceHelper sharedPreferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceHelper = new SharedPreferenceHelper(ModeSwitch.this);
        getTheme().applyStyle(sharedPreferenceHelper.getTheme(),true);
        setContentView(R.layout.activity_mode_switch);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setupUI();
        setTitle(doorType);
        nightModeSwitch.setChecked(false);
        setInvisible();
        if (doorType.equals("FrontDoor")){

            startTimeHour.setText(String.valueOf(sharedPreferenceHelper.getStartHour()));
            startTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getStartMinute()));
            endTimeHour.setText(String.valueOf(sharedPreferenceHelper.getEndHour()));
            endTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getEndMinute()));
            intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间

            timeChangeReceiver = new TimeChangeReceiver();
            registerReceiver(timeChangeReceiver, intentFilter);
        }else if (doorType.equals("BackDoor")){

            startTimeHour.setText(String.valueOf(sharedPreferenceHelper.getBackStartHour()));
            startTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getBackStartMinute()));
            endTimeHour.setText(String.valueOf(sharedPreferenceHelper.getBackEndHour()));
            endTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getBackEndMinute()));
            intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间

            timeChangeReceiver = new TimeChangeReceiver();
            registerReceiver(timeChangeReceiver, intentFilter);
        }


        else if(doorType.equals("Window")){

            startTimeHour.setText(String.valueOf(sharedPreferenceHelper.getWinStartHour()));
            startTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getWinStartMinute()));
            endTimeHour.setText(String.valueOf(sharedPreferenceHelper.getWinEndHour()));
            endTimeMinute.setText(String.valueOf(sharedPreferenceHelper.getWinEndMinute()));
            intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_TIME_TICK);//每分钟变化
            intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);//设置了系统时区
            intentFilter.addAction(Intent.ACTION_TIME_CHANGED);//设置了系统时间

            timeChangeReceiver = new TimeChangeReceiver();
            registerReceiver(timeChangeReceiver, intentFilter);
        }

        /*get door type*/

        System.out.println("doorType"+doorType);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(timeChangeReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //calendar = Calendar.getInstance();
        /*initialize time database reference*/

        alertModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(ModeSwitch.this, "Alert Mode On", Toast.LENGTH_LONG).show();
                    alertModeRef.setValue("1");
                }else{
                    Toast.makeText(ModeSwitch.this, "Alert Mode Off", Toast.LENGTH_LONG).show();
                    alertModeRef.setValue("0");
                }
            }
        });

        alertModeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    alertModeSwitch.setChecked(true);
                }else{
                    alertModeSwitch.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*night mode*/

        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(ModeSwitch.this, "Night Mode On", Toast.LENGTH_LONG).show();
                    nightModeRef.setValue("1");
                    setVisible();

                }else{
                    Toast.makeText(ModeSwitch.this, "Night Mode Off", Toast.LENGTH_LONG).show();
                    nightModeRef.setValue("0");
                    setInvisible();
                }
            }
        });

        nightModeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.getValue(String.class);
                if(value.equals("1")){
                    nightModeSwitch.setChecked(true);
                }else{
                    nightModeSwitch.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*start button*/
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //local variable
                setColor();
		Toast.makeText(ModeSwitch.this, "Time set up", Toast.LENGTH_LONG).show();
                startHour = Integer.parseInt(String.valueOf(startTimeHour.getText()));
                startMinute = Integer.parseInt(String.valueOf(startTimeMinute.getText()));
                endHour = Integer.parseInt(String.valueOf(endTimeHour.getText()));
                endMinute = Integer.parseInt(String.valueOf(endTimeMinute.getText()));
                if (doorType.equals("FrontDoor")){

                    //sharedpreference
                    sharedPreferenceHelper.saveStartHour(startHour);
                    sharedPreferenceHelper.saveStartMinute(startMinute);
                    sharedPreferenceHelper.saveEndHour(endHour);
                    sharedPreferenceHelper.saveEndMinute(endMinute);
                }else if (doorType.equals("BackDoor")) {

                    //sharedpreference
                    sharedPreferenceHelper.saveBackStartHour(startHour);
                    sharedPreferenceHelper.saveBackStartMinute(startMinute);
                    sharedPreferenceHelper.saveBackEndHour(endHour);
                    sharedPreferenceHelper.saveBackEndMinute(endMinute);
                }else if(doorType.equals("Window")){

                        //sharedpreference
                        sharedPreferenceHelper.saveWinStartHour(startHour);
                        sharedPreferenceHelper.saveWinStartMinute(startMinute);
                        sharedPreferenceHelper.saveWinEndHour(endHour);
                        sharedPreferenceHelper.saveWinEndMinute(endMinute);
                }

            }
        });

        //run a new thread in back end to count time during activity running
       timeCounter = new TimeCounter();
        //Thread time_counter = new Thread(timeCounter, "1");
        timeCounter.start();

        doorRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (alertModeSwitch.isChecked()&& value.equals("opened")){
                    NotifyAlarm();
                }
                timeCounter.setDoor(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //listen when activity is pause
        doorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if (alertModeSwitch.isChecked()&& value.equals("opened")){
                    NotifyAlarm();
                }
                timeCounter.setDoor(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class TimeCounter extends Thread {
        //关键字volatile表示这个变量随时都有可能发生变化，主要是表示同步，也就是同一时刻只能由一个线程来修改该变量的值。
        public volatile String door = "";
        int counter = 0;
        @Override
        public void run() {
            super.run();
            while (true){
                // thread runing
                if (door.equals("opened")){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                    if (counter == 10){
                        Notify();
                        counter = 0;
                    }
                }else{
                    counter = 0;
                }

            }
        }
        public void setDoor(String door) {
            this.door= door;
        }
    }

    class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_TIME_TICK:
                    //每过一分钟 触发
                   if (nightModeSwitch.isChecked()){
                    calendar = Calendar.getInstance();
                    if((calendar.get(Calendar.HOUR_OF_DAY)*60+calendar.get(Calendar.MINUTE)== startHour*60+startMinute)){
                        alertModeRef.setValue("1");
                    }if (  (calendar.get(Calendar.HOUR_OF_DAY)*60 +calendar.get(Calendar.MINUTE)== endHour*60 + endMinute)){
                    alertModeRef.setValue("0");}
                }
                    //Toast.makeText(context, "1 min passed", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_TIME_CHANGED:
                    //设置了系统时间
                    //Toast.makeText(context, "system time changed", Toast.LENGTH_SHORT).show();
                    break;
                case Intent.ACTION_TIMEZONE_CHANGED:
                    //设置了系统时区的action
                    //Toast.makeText(context, "system time zone changed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    protected void setVisible(){
        endTimeHour.setVisibility(View.VISIBLE);
        endTimeMinute.setVisibility(View.VISIBLE);
        startTimeHour.setVisibility(View.VISIBLE);
        startTimeMinute.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
        startTimeTextView.setVisibility(View.VISIBLE);
        endTimeTextView.setVisibility(View.VISIBLE);
        colon.setVisibility(View.VISIBLE);
        colon2.setVisibility(View.VISIBLE);
    }
    protected void setInvisible(){
        endTimeHour.setVisibility(View.INVISIBLE);
        endTimeMinute.setVisibility(View.INVISIBLE);
        startTimeHour.setVisibility(View.INVISIBLE);
        startTimeMinute.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        startTimeTextView.setVisibility(View.INVISIBLE);
        endTimeTextView.setVisibility(View.INVISIBLE);
        colon.setVisibility(View.INVISIBLE);
        colon2.setVisibility(View.INVISIBLE);
    }
    public void Notify() {

        Intent resultIntent = new Intent(this, MainActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setContentIntent(resultPendingIntent);
        builder.setContentTitle("Notification");
        builder.setContentText(doorType+ " is still opening");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, builder.build());
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
    public void NotifyAlarm() {

        Intent resultIntent = new Intent(this, MainActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setContentIntent(resultPendingIntent);
        builder.setContentTitle("NOTIFICATION");
        builder.setContentText("WARNING: " + doorType +" POTENTIAL BREAK IN!");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(2, builder.build());
    }
    public void setupUI(){
        intent = getIntent();
        bundle = intent.getExtras();
        doorType = bundle.getString("DoorType");
        myRef = database.getReference().child(doorType);
        alertModeSwitch = this.findViewById(R.id.alertModeSwitch);
        nightModeSwitch = this.findViewById(R.id.nightModeSwitch);
        startTimeHour = this.findViewById(R.id.startTimeHour);
        startTimeMinute =this.findViewById(R.id.startTimeMinute);
        endTimeHour = this.findViewById(R.id.endTimeHour);
        endTimeMinute = this.findViewById(R.id.endTimeMinute);
        startButton = this.findViewById(R.id.startButton);
        startTimeTextView = this.findViewById(R.id.startTimeTextView);;
        endTimeTextView = this.findViewById(R.id.endTimeTextView);;
        colon = this.findViewById(R.id.colon);;
        colon2 = this.findViewById(R.id.colon2);;
        alertModeRef = myRef.getRef().child("AlertMode");
        nightModeRef = myRef.getRef().child("NightMode");
        doorRef = myRef.getRef().child("Door");

    }
    public void setColor(){
        getTheme().applyStyle(R.style.OverlayThemeLime, true);
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#ff2d00"));
        window.setNavigationBarColor(Color.parseColor("#ff2d00"));
        startButton.setTextColor(Color.parseColor("#ff2d00"));
        startTimeTextView.setTextColor(Color.parseColor("#ff2d00"));
        endTimeTextView.setTextColor(Color.parseColor("#ff2d00"));
    }
}