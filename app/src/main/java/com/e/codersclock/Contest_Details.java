package com.e.codersclock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class Contest_Details extends AppCompatActivity {

    String name,event,href;
    long start_sortingID,end_sortingID;
    View logo;

    RelativeLayout visiturl,setReminder,addtocalender;
    TextView textView_name,textView_event,textView_stat_time,textView_start_date,textView_end_time,textView_end_date;

    String startDate,startTime,endTime,endDate;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest__details);
        logo=findViewById(R.id.contest_detail_view);
        visiturl=findViewById(R.id.contest_detail_visit_url);
        setReminder=findViewById(R.id.contest_details_set_in_app);
        addtocalender=findViewById(R.id.contest_details_add_to_calender);
        textView_name=findViewById(R.id.contest_detail_textview_name);
        textView_event=findViewById(R.id.contest_detail_textview_event);

        textView_stat_time=findViewById(R.id.contest_detail_textview_start_time);
        textView_start_date=findViewById(R.id.contest_detail_textview_start_date);

        textView_end_time=findViewById(R.id.contest_detail_textview_end_time);
        textView_end_date=findViewById(R.id.contest_detail_textview_end_date);





        final Intent intent=getIntent();

        name=intent.getStringExtra("name");
        event=intent.getStringExtra("event");
        href=intent.getStringExtra("href");
        String startIdD=intent.getStringExtra("start_sortingID");
        assert startIdD != null;
        start_sortingID=Long.parseLong(startIdD);

        String endId=intent.getStringExtra("end_sortingID");
        assert endId != null;
        end_sortingID=Long.parseLong(endId);

        textView_name.setText(name);
        textView_event.setText(event);



        startTime=getDate(start_sortingID,"HH:mm");
        startDate=getDate(start_sortingID,"dd - MMM - yyyy");
        endTime=getDate(end_sortingID,"HH:mm");
        endDate=getDate(end_sortingID,"dd - MMM - yyyy");

        textView_stat_time.setText(startTime);
        textView_start_date.setText(startDate);
        textView_end_time.setText(endTime);
        textView_end_date.setText(endDate);





        if(name.equals("codechef"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.codechef));
        }else if(name.equals("codeforces"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.codeforces));
        }else if(name.equals("hackerearth"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.hackerearth));
        }else if(name.equals("topcoder"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.topcoder));
        }else if(name.equals("atcoder"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.atcoder));
        }else if(name.equals("leetcode"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.leetcode));
        }else if(name.equals("codingcompetitions"))
        {
            logo.setBackground(ContextCompat.getDrawable(Contest_Details.this, R.drawable.google));
        }


        visiturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
                startActivity(browserIntent);
            }
        });

        addtocalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", start_sortingID);
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", end_sortingID);
                intent.putExtra("title", "A Test Event from android app");



                if (intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(Contest_Details.this, "There is no app that can support this action", Toast.LENGTH_SHORT).show();
                }


            }
        });


        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=findViewById(R.id.textview_of_set_in_app_reminder);
                startAlarm(start_sortingID-600000);
                Toast.makeText(Contest_Details.this, "Reminder Set Successfully", Toast.LENGTH_SHORT).show();
                textView.setText("YOU'LL BE REMINDED 10 MINS BEFORE THE CONTEST");
            }
        });

    }

    private  void startAlarm(long time)
    {
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlertReceiver.class);
        intent.putExtra("tittle",name);
        intent.putExtra("message",event+" at "+startTime);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, new Random().nextInt(500000),intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}