package com.e.codersclock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.e.codersclock.Model.Contest;
import com.e.codersclock.Model.SliderModel;
import com.e.codersclock.MyAdapter.ContestAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.e.codersclock.R.drawable.upcoming;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    FirebaseFirestore mydb;


    RecyclerView recyclerView,recyclerView_ongoing;
    RelativeLayout mainBackground;
    LinearLayout linearLayout_about;
    RecyclerView.LayoutManager layoutManager,layoutManager_ongoing;
    ArrayList<Contest> recycler_data,recycler_data_ongoing;



    View ongoing,upcoming,about;

    TextView heading_text;

    ViewPager bannerSliderViewPager;

    ImageView rotatingImageview;
    RelativeLayout splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue= Volley.newRequestQueue(this);
        mydb=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.upcoming_recycler);
        recycler_data=new ArrayList<>();
        bannerSliderViewPager=findViewById(R.id.banner_slider_view_pager);
        recyclerView_ongoing=findViewById(R.id.ongoing_recycler);
        recycler_data_ongoing=new ArrayList<>();
        splash=findViewById(R.id.relativeSplash);
        ongoing=findViewById(R.id.view_ongoing);
        upcoming=findViewById(R.id.view_upcoming);
        about=findViewById(R.id.view_about);
        heading_text=findViewById(R.id.heading_text);
        rotatingImageview=findViewById(R.id.rotatingimageview);
        linearLayout_about=findViewById(R.id.linear_layout_about);
        mainBackground=findViewById(R.id.RelativeLayout_main);
        FetchData fetchData=new FetchData();
        new Thread(fetchData).start();

        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotatingImageview.startAnimation(rotate);


        final long rightnow=Calendar.getInstance().getTimeInMillis();
        try {
                    mydb.collection("Upcoming").whereGreaterThanOrEqualTo("start_sortingID",rightnow).orderBy("start_sortingID", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (e==null) {


                    recycler_data.clear();
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(queryDocumentSnapshots)) {

                        final Contest data = new Contest();
                        data.setEvent(doc.getData().get("event").toString());
                        String name = doc.getData().get("name").toString();
                        int dot = name.indexOf('.');
                        String newName = name.substring(0, dot);
                        data.setName(newName);

                        String time = doc.getData().get("start_time").toString() + " - " + doc.getData().get("end_time").toString();
                        data.setTime(time);

                        String date = doc.getData().get("start_date").toString() + " - " + doc.getData().get("end_date").toString();
                        data.setDate(date);


                        data.setStart_sortingID(Objects.requireNonNull(doc.getData().get("start_sortingID")).toString());
                        data.setEnd_sortingID(Objects.requireNonNull(doc.getData().get("end_sortingID")).toString());
                        data.setHref(Objects.requireNonNull(doc.getData().get("href")).toString());


                        recycler_data.add(data);


                    }


                    recyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(MainActivity.this);

                    recyclerView.setLayoutManager(layoutManager);


                    ContestAdapter myAdapter = new ContestAdapter(MainActivity.this, recycler_data);
                    recyclerView.setAdapter(myAdapter);
                    splash.setVisibility(View.GONE);

                }

            }
        });
        }catch (Exception ignored)
        {

        }


        try {
                    mydb.collection("Ongoing").orderBy("start_sortingID", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if(e==null) {


                    recycler_data_ongoing.clear();
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(queryDocumentSnapshots)) {
                        if (Long.parseLong(Objects.requireNonNull(doc.getData().get("end_sortingID")).toString()) >= rightnow) {
                            final Contest data = new Contest();
                            data.setEvent(doc.getData().get("event").toString());
                            String name = doc.getData().get("name").toString();
                            int dot = name.indexOf('.');
                            String newName = name.substring(0, dot);
                            data.setName(newName);

                            String time = doc.getData().get("start_time").toString() + " - " + doc.getData().get("end_time").toString();
                            data.setTime(time);

                            String date = doc.getData().get("start_date").toString() + " - " + doc.getData().get("end_date").toString();
                            data.setDate(date);


                            data.setStart_sortingID(Objects.requireNonNull(doc.getData().get("start_sortingID")).toString());
                            data.setEnd_sortingID(Objects.requireNonNull(doc.getData().get("end_sortingID")).toString());
                            data.setHref(Objects.requireNonNull(doc.getData().get("href")).toString());


                            recycler_data_ongoing.add(data);
                        }


                    }


                    recyclerView_ongoing.setHasFixedSize(true);
                    layoutManager_ongoing = new LinearLayoutManager(MainActivity.this);

                    recyclerView_ongoing.setLayoutManager(layoutManager_ongoing);

                    ContestAdapter myAdapter = new ContestAdapter(MainActivity.this, recycler_data_ongoing);
                    recyclerView_ongoing.setAdapter(myAdapter);
                }

            }
        });
        }catch (Exception exception)
        {

        }



        try{
                   mydb.collection("Upcoming").whereGreaterThanOrEqualTo("start_sortingID",rightnow).orderBy("start_sortingID", Query.Direction.ASCENDING).limit(3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                int i=0;
                if (error==null) {


                    List<SliderModel> sliderModelList = new ArrayList<>();
                    Log.d("#alpha", "task2");
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(value)) {

                        SliderModel sliderModel = new SliderModel();

                        sliderModel.setEvent(doc.getData().get("event").toString());
                        String name = doc.getData().get("name").toString();
                        int dot = name.indexOf('.');
                        String newName = name.substring(0, dot);
                        sliderModel.setName(newName);

                        long start_sortingId = Long.parseLong(Objects.requireNonNull(doc.getData().get("start_sortingID")).toString());
                        long end_sortingId = Long.parseLong(Objects.requireNonNull(doc.getData().get("end_sortingID")).toString());

                        sliderModel.setStart_sortingID(start_sortingId);
                        sliderModel.setEnd_sortingID(end_sortingId);


                        sliderModelList.add(sliderModel);
                        Log.d("#alpha", "task3");
//                    i++;
//                    if (i==3)
//                    {
//                        Log.d("#alpha","task4");
//                        break;
//                    }
                    }

                    Harsh harsh = new Harsh();
                    harsh.oncreate(bannerSliderViewPager, sliderModelList, MainActivity.this);
                }

            }
        });
        }catch (Exception exception)
        {

        }



        Log.d("#alpha","task1");


        try {
            mydb.collection("Upcoming").whereLessThan("end_sortingID",rightnow).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot doc:task.getResult())
                    {
                        String id=doc.getData().get("id").toString();
                        mydb.collection("Upcoming").document(id).delete();
                    }
                }
            });


            mydb.collection("Ongoing").whereLessThan("end_sortingID",rightnow).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot doc:task.getResult())
                    {
                        String id=doc.getData().get("id").toString();
                        mydb.collection("Upcoming").document(id).delete();
                    }
                }
            });
        }catch (Exception e)
        {

        }



















        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcoming.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                about.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                heading_text.setText("o n g o i n g");
                mainBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                ongoing.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.tint));
                recyclerView.setVisibility(View.GONE);
                linearLayout_about.setVisibility(View.GONE);

                recyclerView_ongoing.setVisibility(View.VISIBLE);
            }
        });


        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ongoing.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                about.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                heading_text.setText("u p c o m i n g");

                upcoming.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.tint));
                mainBackground.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                recyclerView_ongoing.setVisibility(View.GONE);
                linearLayout_about.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                upcoming.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                ongoing.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.backtint));
                heading_text.setText("a b o u t  m e");
                recyclerView.setVisibility(View.GONE);
                recyclerView_ongoing.setVisibility(View.GONE);
                linearLayout_about.setVisibility(View.VISIBLE);
                mainBackground.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button));
                about.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.tint));




            }
        });




































    }


    class FetchData implements Runnable{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {

            try{
                long rightnow = Calendar.getInstance().getTimeInMillis();
                rightnow=rightnow-19800000;
                String time=getDate(rightnow,"yyyy-MM-dd'T'HH'%3A'mm'%3A'ss");
                Log.d("rightnow",Long.toString(rightnow));


                String codeforces_url = "https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codeforces.com&start__gte="+time+"&order_by=start";
                upload_Data(codeforces_url,"Upcoming");

                String codechef_url = "https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codechef.com&start__gte="+time+"&order_by=start";
                upload_Data(codechef_url,"Upcoming");

                String hackerearth_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=hackerearth.com&start__gte="+time+"&order_by=start";
                upload_Data(hackerearth_url,"Upcoming");

                String topercoder_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=topcoder.com&start__gte="+time+"&order_by=start";
                upload_Data(topercoder_url,"Upcoming");

                String atcoder_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=atcoder.jp&start__gte="+time+"&order_by=start";
                upload_Data(atcoder_url,"Upcoming");

                String leetcode_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=leetcode.com&start__gte="+time+"&order_by=start";
                upload_Data(leetcode_url,"Upcoming");

                String google_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codingcompetitions.withgoogle.com&start__gte="+time+"&order_by=start";
                upload_Data(google_url,"Upcoming");

                //Ongoing

                String ongoing_codeforces_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codeforces.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_codeforces_url,"Ongoing");

                String ongoing_codechef_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codechef.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_codechef_url,"Ongoing");

                String ongoing_hackerearth_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=hackerearth.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_hackerearth_url,"Ongoing");

                String ongoing_topcoder_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=topcoder.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_topcoder_url,"Ongoing");

                String ongoing_atcoder_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=atcoder.jp&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_atcoder_url,"Ongoing");

                String ongoing_leetcode_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=leetcode.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_leetcode_url,"Ongoing");


                String ongoing_google_url="https://clist.by/api/v1/contest/?username=harshsharma11&api_key=c52862f593105dee86c0d05958e213726316a937&limit=10&resource__name=codingcompetitions.withgoogle.com&start__lt="+time+"&end__gt="+time;
                upload_Data(ongoing_google_url,"Ongoing");
            }catch (Exception e)
            {

            }



        }
    }


    public void upload_Data(String url, final String collection)
    {
        final JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("123456","task2");
                        try {
                            JSONArray jsonArray =response.getJSONArray("objects");
                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject result=jsonArray.getJSONObject(i);
                                Log.d("123456",result.getString("event"));
                                String event=result.getString("event");
                                String id=result.getString("id");
                                String duration=result.getString("duration");
                                String end=result.getString("end");
                                String href=result.getString("href");
                                String start=result.getString("start");
                                long start_sortingID=sortingId(start);
                                long end_sortingID=sortingId(end);

                                String start_date=getDate(start_sortingID,"dd/MM/yyyy");
                                String start_time=getDate(start_sortingID,"HH:mm");

                                String end_date=getDate(end_sortingID,"dd/MM/yyyy");
                                String end_time=getDate(end_sortingID,"HH:mm");


                                JSONObject resource=result.getJSONObject("resource");
                                String name=resource.getString("name");
                                String icon=resource.getString("icon");
                                String R_id=resource.getString("id");





                                final HashMap<String, Object> data = new HashMap<>();
                                data.put("event",event);
                                data.put("id",id);
                                data.put("duration",duration);
                                //data.put("end",end);
                                data.put("href",href);
                                //data.put("start",start);
                                data.put("name",name);
                                data.put("icon",icon);
                                data.put("R_id",R_id);
                                data.put("start_sortingID",start_sortingID);
                                data.put("end_sortingID",end_sortingID);
                                data.put("start_date",start_date);
                                data.put("end_date",end_date);
                                data.put("start_time",start_time);
                                data.put("end_time",end_time);




                                mydb.collection(collection).document(id).set(data);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("123456","task3");
                Log.d("123456",error.toString());
            }
        });

        mQueue.add(request);
    }


    public long sortingId(String myDate)
    {
        //String myDate = "2020-11-10T04:30:00";

        SimpleDateFormat sdf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }
        Date date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            try {
                date = sdf.parse(myDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        long millis = date.getTime();
        long ans=millis+19800000;

        return  ans;
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